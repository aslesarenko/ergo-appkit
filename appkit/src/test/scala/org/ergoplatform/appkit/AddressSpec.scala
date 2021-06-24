package org.ergoplatform.appkit

import org.ergoplatform.appkit.testing.AppkitTesting
import org.ergoplatform.{ErgoAddressEncoder, Pay2SAddress}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.scalatest.{PropSpec, Matchers}
import scorex.util.encode.Base16
import sigmastate.serialization.ErgoTreeSerializer



class AddressSpec extends PropSpec with Matchers with ScalaCheckDrivenPropertyChecks
  with AppkitTesting {

  def checkIsTestnetP2PKAddress(addr: Address) = {
    addr.isMainnet shouldBe false
    addr.isP2PK shouldBe true
    addr.getNetworkType shouldBe NetworkType.TESTNET
    addr.getErgoAddress.networkPrefix shouldBe ErgoAddressEncoder.TestnetNetworkPrefix
  }

  property("encoding vector") {
    val addr = Address.create(addrStr)
    addr.toString shouldBe addrStr
    checkIsTestnetP2PKAddress(addr)
  }

  property("Address fromMnemonic") {
    val addr = Address.fromMnemonic(NetworkType.TESTNET, mnemonic, SecretString.empty())
    addr.toString shouldBe addrStr
    checkIsTestnetP2PKAddress(addr)

    val addr2 = Address.fromMnemonic(NetworkType.MAINNET, mnemonic, SecretString.empty())
    addr2.toString shouldNot be (addrStr)
  }

  property("Address from ErgoAddress") {
    implicit val encoder: ErgoAddressEncoder = ErgoAddressEncoder(ErgoAddressEncoder.TestnetNetworkPrefix);
    val ergoAddr = encoder.fromString(addrStr).get
    val addr = new Address(ergoAddr)
    checkIsTestnetP2PKAddress(addr)
    addr.toString shouldBe addrStr
  }

  property("Address createEip3Address") {
    val addr = Address.fromMnemonic(NetworkType.MAINNET, mnemonic, SecretString.empty())
    val firstEip3Addr = Address.createEip3Address(0, NetworkType.MAINNET, mnemonic, SecretString.empty())
    firstEip3Addr.toString shouldBe firstEip3AddrStr
    addr.toString shouldNot be (firstEip3AddrStr)

    val secondEip3Addr = Address.createEip3Address(1, NetworkType.MAINNET, mnemonic, SecretString.empty())
    secondEip3Addr.toString shouldBe secondEip3AddrStr
  }

  property("create Address from ErgoTree with DHT") {
    val tree = "100207036ba5cfbc03ea2471fdf02737f64dbcd58c34461a7ec1e586dcd713dacbf89a120400d805d601db6a01ddd6027300d603b2a5730100d604e4c672030407d605e4c672030507eb02ce7201720272047205ce7201720472027205"
    implicit val encoder: ErgoAddressEncoder = ErgoAddressEncoder.apply(NetworkType.MAINNET.networkPrefix);
    val ergoTree = ErgoTreeSerializer.DefaultSerializer.deserializeErgoTree(Base16.decode(tree).get)
    val addr = Pay2SAddress.apply(ergoTree)
    val addr2 = encoder.fromProposition(ergoTree).get
    val addr3 = encoder.fromProposition(ergoTree.proposition).get
    addr shouldBe addr2
    addr shouldBe addr3
  }
}
