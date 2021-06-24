package org.ergoplatform.appkit

import org.ergoplatform.wallet.secrets.{ExtendedSecretKey, DerivationPath}
import scalan.RType
import special.collection.Coll
import com.google.common.base.{Preconditions, Strings}

import scala.collection.{mutable, JavaConversions}
import org.ergoplatform._
import org.ergoplatform.ErgoBox.TokenId
import sigmastate.SType
import sigmastate.Values.{ErgoTree, Constant, SValue, EvaluatedValue}
import sigmastate.serialization.{ValueSerializer, ErgoTreeSerializer, SigmaSerializer, GroupElementSerializer}
import scorex.crypto.authds.ADKey
import scorex.crypto.hash.Digest32
import org.ergoplatform.wallet.mnemonic.{Mnemonic => WMnemonic}
import org.ergoplatform.settings.ErgoAlgos
import sigmastate.lang.Terms.ValueOps
import sigmastate.eval.{CompiletimeIRContext, Evaluation, Colls, CostingSigmaDslBuilder, CPreHeader}
import sigmastate.eval.Extensions._
import special.sigma.{AnyValue, AvlTree, Header, GroupElement}
import java.util
import java.lang.{Short => JShort, Integer => JInt, Long => JLong, Byte => JByte, String => JString}
import java.math.BigInteger
import java.text.Normalizer.Form.NFKD
import java.text.Normalizer.normalize
import java.util.{Map => JMap, List => JList}

import sigmastate.utils.Helpers._  // don't remove, required for Scala 2.11
import org.ergoplatform.ErgoAddressEncoder.NetworkPrefix
import org.ergoplatform.appkit.Iso.{isoErgoTokenToPair, JListToColl}
import org.ergoplatform.wallet.{Constants, TokensMap}
import org.ergoplatform.wallet.mnemonic.Mnemonic.{Pbkdf2KeyLength, Pbkdf2Iterations}
import scorex.util.encode.Base16
import sigmastate.basics.DLogProtocol.ProveDlog
import sigmastate.basics.{ProveDHTuple, DiffieHellmanTupleProverInput}
import sigmastate.interpreter.CryptoConstants.EcPointType
import scorex.util.{idToBytes, bytesToId, ModifierId}
import sigmastate.interpreter.ContextExtension
import org.bouncycastle.crypto.digests.SHA512Digest
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator
import org.bouncycastle.crypto.params.KeyParameter

/** Type-class of isomorphisms between types.
  * Isomorphism between two types `A` and `B` essentially say that both types
  * represents the same information (entity) but in a different way.
  * <p>
  * The information is not lost so that both are true:
  * 1) a == from(to(a))
  * 2) b == to(from(b))
  * <p>
  * It is used to define type-full conversions:
  * - different conversions between Java and Scala data types.
  * - conversion between Ergo representations and generated API representations
  */
abstract class Iso[A, B] {
  def to(a: A): B
  def from(b: B): A
  def andThen[C](iso: Iso[B,C]): Iso[A,C] = ComposeIso(iso, this)
}
final case class InverseIso[A,B](iso: Iso[A,B]) extends Iso[B,A] {
  override def to(a: B): A = iso.from(a)
  override def from(b: A): B = iso.to(b)
}
final case class ComposeIso[A, B, C](iso2: Iso[B, C], iso1: Iso[A, B]) extends Iso[A, C] {
  def from(c: C): A = iso1.from(iso2.from(c))
  def to(a: A): C = iso2.to(iso1.to(a))
}

trait LowPriorityIsos {
}

object Iso extends LowPriorityIsos {
  implicit def identityIso[A]: Iso[A, A] = new Iso[A, A] {
    override def to(a: A): A = a

    override def from(b: A): A = b
  }

  implicit def inverseIso[A,B](implicit iso: Iso[A,B]): Iso[B,A] = InverseIso[A,B](iso)

  implicit val jbyteToByte: Iso[JByte, Byte] = new Iso[JByte, Byte] {
    override def to(b: JByte): Byte = b
    override def from(a: Byte): JByte = a
  }

  implicit val jshortToShort: Iso[JShort, Short] = new Iso[JShort, Short] {
    override def to(b: JShort): Short = b
    override def from(a: Short): JShort = a
  }

  implicit val jintToInt: Iso[JInt, Int] = new Iso[JInt, Int] {
    override def to(b: JInt): Int = b
    override def from(a: Int): JInt = a
  }

  implicit val jlongToLong: Iso[JLong, Long] = new Iso[JLong, Long] {
    override def to(b: JLong): Long = b
    override def from(a: Long): JLong = a
  }

  implicit val isoErgoTokenToPair: Iso[ErgoToken, (TokenId, Long)] = new Iso[ErgoToken, (TokenId, Long)] {
    override def to(a: ErgoToken) = (Digest32 @@ a.getId.getBytes, a.getValue)
    override def from(t: (TokenId, Long)): ErgoToken = new ErgoToken(t._1, t._2)
  }

  implicit val isoJListErgoTokenToMapPair: Iso[JList[ErgoToken], mutable.LinkedHashMap[ModifierId, Long]] =
    new Iso[JList[ErgoToken], mutable.LinkedHashMap[ModifierId, Long]] {
      override def to(a: JList[ErgoToken]): mutable.LinkedHashMap[ModifierId, Long] = {
        import JavaHelpers._
        val lhm = new mutable.LinkedHashMap[ModifierId, Long]()
        a.convertTo[IndexedSeq[(TokenId, Long)]]
          .map(t => bytesToId(t._1) -> t._2)
          .foldLeft(lhm)(_ += _)
      }

      override def from(t: mutable.LinkedHashMap[ModifierId, Long]): JList[ErgoToken] = {
        import JavaHelpers._
        val pairs: IndexedSeq[(TokenId, Long)] = t.toIndexedSeq
          .map(t => (Digest32 @@ idToBytes(t._1)) -> t._2)
        pairs.convertTo[JList[ErgoToken]]
      }
    }

  implicit val isoErgoTypeToSType: Iso[ErgoType[_], SType] = new Iso[ErgoType[_], SType] {
    override def to(et: ErgoType[_]): SType = Evaluation.rtypeToSType(et.getRType)
    override def from(st: SType): ErgoType[_] = new ErgoType(Evaluation.stypeToRType(st))
  }

  implicit val isoErgoValueToSValue: Iso[ErgoValue[_], EvaluatedValue[SType]] = new Iso[ErgoValue[_], EvaluatedValue[SType]] {
    override def to(x: ErgoValue[_]): EvaluatedValue[SType] =
      Constant(x.getValue.asInstanceOf[SType#WrappedType], Evaluation.rtypeToSType(x.getType.getRType))
      
    override def from(x: EvaluatedValue[SType]): ErgoValue[_] = {
      new ErgoValue(x.value, new ErgoType(Evaluation.stypeToRType(x.tpe)))
    }
  }

  implicit val isoContextVarsToContextExtension: Iso[JList[ContextVar], ContextExtension] = new Iso[JList[ContextVar], ContextExtension] {
    import JavaHelpers._
    override def to(vars: JList[ContextVar]): ContextExtension = {
      var values: Map[Byte, EvaluatedValue[SType]] = Map.empty
      vars.convertTo[IndexedSeq[ContextVar]].foreach { v =>
        val id = v.getId
        val value = v.getValue
        if (values.contains(id)) sys.error(s"Duplicate variable id: ($id -> $value")
        values += (v.getId() -> isoErgoValueToSValue.to(v.getValue))
      }
      ContextExtension(values)
    }
    override def from(b: ContextExtension): JList[ContextVar] = {
      val iso = JListToIndexedSeq[ContextVar, ContextVar]
      val vars = iso.from(b.values
        .map { case (id, v) => new ContextVar(id, isoErgoValueToSValue.from(v)) }
        .toIndexedSeq)
      vars
    }
  }

  implicit def isoJMapToMap[K,V1,V2](iso: Iso[V1, V2]): Iso[JMap[K, V1], scala.collection.Map[K,V2]] = new Iso[JMap[K, V1], scala.collection.Map[K,V2]] {
    override def to(a: JMap[K, V1]): scala.collection.Map[K, V2] = {
      JavaConversions.mapAsScalaMap(a).mapValues(iso.to)
    }
    override def from(b: scala.collection.Map[K, V2]): JMap[K, V1] = {
      JavaConversions.mapAsJavaMap(b.mapValues(iso.from))
    }
  }

  val isoEvaluatedValueToSConstant: Iso[EvaluatedValue[SType], Constant[SType]] = new Iso[EvaluatedValue[SType], Constant[SType]] {
    override def to(x: EvaluatedValue[SType]): Constant[SType] = x.asInstanceOf[Constant[SType]]
    override def from(x: Constant[SType]): EvaluatedValue[SType] = x
  }

  val isoTokensListToPairsColl: Iso[JList[ErgoToken], Coll[(TokenId, Long)]] = {
    implicit val TokenIdRType: RType[TokenId] = RType.arrayRType[Byte].asInstanceOf[RType[TokenId]]
    JListToColl(isoErgoTokenToPair, RType[(TokenId, Long)])
  }

  implicit val jstringToOptionString: Iso[JString, Option[String]] = new Iso[JString, Option[String]] {
    override def to(a: JString): Option[String] = if (Strings.isNullOrEmpty(a)) None else Some(a)
    override def from(b: Option[String]): JString = if (b.isEmpty) "" else b.get
  }

  implicit val arrayCharToOptionString: Iso[SecretString, Option[String]] = new Iso[SecretString, Option[String]] {
    override def to(ss: SecretString): Option[String] = {
      if (ss == null || ss.isEmpty) None else Some(ss.toStringUnsecure)
    }
    override def from(b: Option[String]): SecretString =
      if (b.isEmpty) SecretString.empty() else SecretString.create(b.get)
  }

  implicit def JListToIndexedSeq[A, B](implicit itemIso: Iso[A, B]): Iso[JList[A], IndexedSeq[B]] =
    new Iso[JList[A], IndexedSeq[B]] {
      override def to(as: JList[A]): IndexedSeq[B] = {
        JavaConversions.asScalaIterator(as.iterator()).map(itemIso.to).toIndexedSeq
      }

      override def from(bs: IndexedSeq[B]): JList[A] = {
        val res = new util.ArrayList[A](bs.length)
        for ( a <- bs.map(itemIso.from) ) res.add(a)
        res
      }
    }

  implicit def JListToColl[A, B](implicit itemIso: Iso[A, B], tB: RType[B]): Iso[JList[A], Coll[B]] =
    new Iso[JList[A], Coll[B]] {
      override def to(as: JList[A]): Coll[B] = {
        val bsIter = JavaConversions.asScalaIterator(as.iterator).map { a =>
          itemIso.to(a)
        }
        Colls.fromArray(bsIter.toArray(tB.classTag))
      }

      override def from(bs: Coll[B]): JList[A] = {
        val res = new util.ArrayList[A](bs.length)
        bs.toArray.foreach { b =>
          res.add(itemIso.from(b))
        }
        res
      }
    }

}

object JavaHelpers {
  implicit class UniversalConverter[A](val x: A) extends AnyVal {
    def convertTo[B](implicit iso: Iso[A,B]): B = iso.to(x)
  }

  implicit class StringExtensions(val base16: String) extends AnyVal {
    /** Decodes this base16 string to byte array. */
    def toBytes: Array[Byte] = decodeStringToBytes(base16)

    /** Decodes this base16 string to byte array and wrap it as Coll[Byte]. */
    def toColl: Coll[Byte] = decodeStringToColl(base16)

    /** Decodes this base16 string to byte array and parse it using [[GroupElementSerializer]]. */
    def toGroupElement: GroupElement = decodeStringToGE(base16)

    /** Decodes this base16 string to byte array and parse it using
      * [[ErgoTreeSerializer.deserializeErgoTree()]].
      */
    def toErgoTree: ErgoTree = decodeStringToErgoTree(base16)
  }

  implicit class ListOps[A](val xs: JList[A]) extends AnyVal {
    def map[B](f: A => B): JList[B] = {
      xs.convertTo[IndexedSeq[A]].map(f).convertTo[JList[B]]
    }
  }

  implicit val TokenIdRType: RType[TokenId] = RType.arrayRType[Byte].asInstanceOf[RType[TokenId]]

  val HeaderRType: RType[Header] = special.sigma.HeaderRType
  val PreHeaderRType: RType[special.sigma.PreHeader] = special.sigma.PreHeaderRType

  def Algos: ErgoAlgos = org.ergoplatform.settings.ErgoAlgos

  def deserializeValue[T <: SValue](bytes: Array[Byte]): T = {
    ValueSerializer.deserialize(bytes).asInstanceOf[T]
  }

  /** Decodes this base16 string to byte array. */
  def decodeStringToBytes(base16: String): Array[Byte] = {
    val bytes = ErgoAlgos.decode(base16).fold(t => throw t, identity)
    bytes
  }

  /** Decodes this base16 string to byte array and wrap it as Coll[Byte]. */
  def decodeStringToColl(base16: String): Coll[Byte] = {
    val bytes = ErgoAlgos.decode(base16).fold(t => throw t, identity)
    Colls.fromArray(bytes)
  }

  /** Decodes this base16 string to byte array and parse it using [[GroupElementSerializer]]. */
  def decodeStringToGE(base16: String): GroupElement = {
    val bytes = ErgoAlgos.decode(base16).fold(t => throw t, identity)
    val pe = GroupElementSerializer.parse(SigmaSerializer.startReader(bytes))
    SigmaDsl.GroupElement(pe)
  }

  /** Decodes this base16 string to byte array and parse it using
   * [[ErgoTreeSerializer.deserializeErgoTree()]].
   */
  def decodeStringToErgoTree(base16: String): ErgoTree = {
    ErgoTreeSerializer.DefaultSerializer.deserializeErgoTree(Base16.decode(base16).get)
  }

  def createP2PKAddress(pk: ProveDlog, networkPrefix: NetworkPrefix): P2PKAddress = {
    implicit val ergoAddressEncoder: ErgoAddressEncoder = ErgoAddressEncoder(networkPrefix)
    P2PKAddress(pk)
  }

  def createP2SAddress(ergoTree: ErgoTree, networkPrefix: NetworkPrefix): Pay2SAddress = {
    implicit val ergoAddressEncoder: ErgoAddressEncoder = ErgoAddressEncoder(networkPrefix)
    Pay2SAddress(ergoTree)
  }

  def hash(s: String): String = {
    ErgoAlgos.encode(ErgoAlgos.hash(s))
  }

  def toPreHeader(h: Header): special.sigma.PreHeader = {
    CPreHeader(h.version, h.parentId, h.timestamp, h.nBits, h.height, h.minerPk, h.votes)
  }

  def getStateDigest(tree: AvlTree): Array[Byte] = {
    tree.digest.toArray
  }

  def toIndexedSeq[T](xs: util.List[T]): IndexedSeq[T] = {
    JavaConversions.asScalaIterator(xs.iterator()).toIndexedSeq
  }

  def compile(constants: util.Map[String, Object], contractText: String, networkPrefix: NetworkPrefix): ErgoTree = {
    val env = JavaConversions.mapAsScalaMap(constants).toMap
    implicit val IR = new CompiletimeIRContext
    val prop = ErgoScriptPredef.compileWithCosting(env, contractText, networkPrefix).asSigmaProp
    ErgoTree.fromProposition(prop)
  }

  private def anyValueToConstant(v: AnyValue): Constant[_ <: SType] = {
    val tpe = Evaluation.rtypeToSType(v.tVal)
    Constant(v.value.asInstanceOf[SType#WrappedType], tpe)
  }

  /** Extracts registers as a list of ErgoValue instances (containing type descriptors). */
  def getBoxRegisters(ergoBox: ErgoBox): JList[ErgoValue[_]] = {
    val size = ergoBox.additionalRegisters.size
    val res = new util.ArrayList[ErgoValue[_]](size)
    for ((r, v: EvaluatedValue[_]) <- ergoBox.additionalRegisters.toIndexedSeq.sortBy(_._1.number)) {
      val i = r.number - ErgoBox.mandatoryRegistersCount
      require(i == res.size(), s"registers are not densely packed in a box: ${ergoBox.additionalRegisters}")
      res.add(Iso.isoErgoValueToSValue.from(v))
    }
    res
  }

  def createBoxCandidate(
        value: Long, tree: ErgoTree,
        tokens: Seq[ErgoToken],
        registers: Seq[ErgoValue[_]], creationHeight: Int): ErgoBoxCandidate = {
    import ErgoBox.nonMandatoryRegisters
    val nRegs = registers.length
    Preconditions.checkArgument(nRegs <= nonMandatoryRegisters.length,
       "Too many additional registers %d. Max allowed %d", nRegs, nonMandatoryRegisters.length)
    implicit val TokenIdRType: RType[TokenId] = RType.arrayRType[Byte].asInstanceOf[RType[TokenId]]
    val ts = Colls.fromItems(tokens.map(isoErgoTokenToPair.to(_)):_*)
    val rs = registers.zipWithIndex.map { case (ergoValue, i) =>
      val id = ErgoBox.nonMandatoryRegisters(i)
      val value = Iso.isoErgoValueToSValue.to(ergoValue)
      id -> value
    }.toMap

    new ErgoBoxCandidate(value, tree, creationHeight, ts, rs)
  }

  /** Converts mnemonic phrase to seed it was derived from.
    * This method should be equivalent to [[org.ergoplatform.wallet.mnemonic.Mnemonic.toSeed()]].
    */
  def mnemonicToSeed(mnemonic: String, passOpt: Option[String] = None): Array[Byte] = {
    val normalizedMnemonic = normalize(mnemonic.toCharArray, NFKD)
    val normalizedPass = normalize(s"mnemonic${passOpt.getOrElse("")}", NFKD)

    val gen = new PKCS5S2ParametersGenerator(new SHA512Digest)
    gen.init(
      normalizedMnemonic.getBytes(org.ergoplatform.wallet.Constants.Encoding),
      normalizedPass.getBytes(org.ergoplatform.wallet.Constants.Encoding),
      Pbkdf2Iterations)
    val dk = gen.generateDerivedParameters(Pbkdf2KeyLength).asInstanceOf[KeyParameter].getKey
    dk
  }

  def seedToMasterKey(seedPhrase: SecretString, pass: SecretString = null): ExtendedSecretKey = {
    val passOpt = if (pass == null || pass.isEmpty()) None else Some(pass.toStringUnsecure)
    val seed = mnemonicToSeed(seedPhrase.toStringUnsecure, passOpt)
    val masterKey = ExtendedSecretKey.deriveMasterKey(seed)
    masterKey
  }

  def createUnsignedInput(boxId: String): UnsignedInput = {
    val idBytes = decodeStringToBytes(boxId)
    createUnsignedInput(idBytes)
  }

  def createUnsignedInput(boxIdBytes: Array[Byte]): UnsignedInput = {
    new UnsignedInput(ADKey @@ boxIdBytes)
  }

  def createDataInput(boxIdBytes: Array[Byte]): DataInput = {
    DataInput(ADKey @@ boxIdBytes)
  }

  def collRType[T](tItem: RType[T]): RType[Coll[T]] = special.collection.collRType(tItem)

  def BigIntRType: RType[special.sigma.BigInt] = special.sigma.BigIntRType

  def GroupElementRType: RType[special.sigma.GroupElement] = special.sigma.GroupElementRType

  def SigmaPropRType: RType[special.sigma.SigmaProp] = special.sigma.SigmaPropRType

  def AvlTreeRType: RType[special.sigma.AvlTree] = special.sigma.AvlTreeRType

  def SigmaDsl: CostingSigmaDslBuilder = sigmastate.eval.SigmaDsl

  def collFrom(arr: Array[Byte]): Coll[Byte] = {
    Colls.fromArray(arr)
  }

  def ergoTreeTemplateBytes(ergoTree: ErgoTree): Array[Byte] = {
    val r = SigmaSerializer.startReader(ergoTree.bytes)
    ErgoTreeSerializer.DefaultSerializer.deserializeHeaderWithTreeBytes(r)._4
  }

  def createDiffieHellmanTupleProverInput(g: GroupElement,
                                          h: GroupElement,
                                          u: GroupElement,
                                          v: GroupElement,
                                          x: BigInteger): DiffieHellmanTupleProverInput = {
    createDiffieHellmanTupleProverInput(
      g = SigmaDsl.toECPoint(g).asInstanceOf[EcPointType],
      h = SigmaDsl.toECPoint(h).asInstanceOf[EcPointType],
      u = SigmaDsl.toECPoint(u).asInstanceOf[EcPointType],
      v = SigmaDsl.toECPoint(v).asInstanceOf[EcPointType], x
    )
  }

  def createDiffieHellmanTupleProverInput(g: EcPointType,
                                          h: EcPointType,
                                          u: EcPointType,
                                          v: EcPointType,
                                          x: BigInteger): DiffieHellmanTupleProverInput = {
    val dht = ProveDHTuple(g, h, u, v)
    DiffieHellmanTupleProverInput(x, dht)
  }

  def createTokensMap(linkedMap: mutable.LinkedHashMap[ModifierId, Long]): TokensMap = {
    linkedMap.toMap
  }

  // TODO the method below is copied from ErgoTransaction. Both of them should be moved to ergo-wallet.

  /**
   * Extracts a mapping (assets -> total amount) from a set of boxes passed as a parameter.
   * That is, the method is checking amounts of assets in the boxes(i.e. that a box contains positive
   * amount for an asset) and then summarize and group their corresponding amounts.
   *
   * @param boxes - boxes to check and extract assets from
   * @return a mapping from asset id to to balance and total assets number
   */
  def extractAssets(boxes: IndexedSeq[ErgoBoxCandidate]): (Map[Seq[Byte], Long], Int) = {
    val map: mutable.Map[Seq[Byte], Long] = mutable.Map[Seq[Byte], Long]()
    val assetsNum = boxes.foldLeft(0) { case (acc, box) =>
      require(box.additionalTokens.length <= SigmaConstants.MaxTokens.value, "too many assets in one box")
      box.additionalTokens.foreach { case (assetId, amount) =>
        val aiWrapped = assetId: Seq[Byte]
        val total = map.getOrElse(aiWrapped, 0L)
        map.put(aiWrapped, Math.addExact(total, amount))
      }
      acc + box.additionalTokens.size
    }
    map.toMap -> assetsNum
  }

  /** Creates a new EIP-3 derivation path with the given last index.
    * The resulting path corresponds to `m/44'/429'/0'/0/index` path.
    */
  def eip3DerivationWithLastIndex(index: Int) = {
    val firstPath = org.ergoplatform.wallet.Constants.eip3DerivationPath
    DerivationPath(firstPath.decodedPath.dropRight(1) :+ index, firstPath.publicBranch)
  }
}



