package org.ergoplatform.appkit

import org.ergoplatform.ErgoBox
import org.ergoplatform.appkit.testing.AppkitTesting
import org.scalatest.{PropSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import sigmastate.Values.{ByteArrayConstant, EvaluatedValue, IntConstant}
import sigmastate.{SType, TrivialProp}
import sigmastate.helpers.TestingHelpers._
import org.ergoplatform.wallet.mnemonic.{Mnemonic => WMnemonic}

class JavaHelpersSpec extends PropSpec with Matchers
    with ScalaCheckDrivenPropertyChecks
    with AppkitTesting {
  import ErgoBox._
  import Iso._
  import JavaHelpers.UniversalConverter

  type Registers = Map[NonMandatoryRegisterId, _ <: EvaluatedValue[_ <: SType]]

  def boxWithRegs(regs: Registers) = {
    testBox(10, TrivialProp.TrueProp, 100, Nil, regs)
  }

  def check(regs: Registers, expRegs: IndexedSeq[ErgoValue[_]]) = {
    val box = boxWithRegs(regs)
    val res = JavaHelpers.getBoxRegisters(box).convertTo[IndexedSeq[ErgoValue[_]]]
    res shouldBe expRegs
  }

  property("InputBox.getRegisters") {
    check(Map.empty, IndexedSeq.empty)
    check(Map(R4 -> IntConstant(10)), IndexedSeq(ErgoValue.of(10)))
    check(
      Map(
        R4 -> IntConstant(10),
        R5 -> ByteArrayConstant(Array[Byte](10, 20, 30))),
      IndexedSeq(
        ErgoValue.of(10), ErgoValue.of(Array[Byte](10, 20, 30))))

    an[IllegalArgumentException] shouldBe thrownBy {
      check(Map(R5 -> IntConstant(10)), IndexedSeq(ErgoValue.of(10)))
    }

    an[IllegalArgumentException] shouldBe thrownBy {
      check(
        Map(
          R4 -> IntConstant(10),
          R6 -> ByteArrayConstant(Array[Byte](10, 20, 30))),
        IndexedSeq(
          ErgoValue.of(10), ErgoValue.of(Array[Byte](10, 20, 30))))
    }
  }

  property("mnemonicToSeed") {
    // check that bouncycastle-based implementation is equivalent to the
    // original Java8-based implementation
    forAll(MinSuccessful(50)) { (mnemonic: String, passOpt: Option[String]) =>
      val seed = JavaHelpers.mnemonicToSeed(mnemonic, passOpt)
      val expSeed = WMnemonic.toSeed(mnemonic, passOpt)
      seed shouldBe expSeed
      println(s"Mnemonic: $mnemonic, Password: $passOpt")
    }
  }

}
