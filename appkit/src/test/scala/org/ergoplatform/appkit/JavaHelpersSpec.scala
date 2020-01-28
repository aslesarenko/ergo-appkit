package org.ergoplatform.appkit

import org.ergoplatform.ErgoBox
import org.ergoplatform.ErgoBox.NonMandatoryRegisterId
import org.scalatest.{PropSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import sigmastate.Values.{EvaluatedValue, IntConstant, ByteArrayConstant}
import sigmastate.{SType, TrivialProp}

class JavaHelpersSpec extends PropSpec with Matchers
    with ScalaCheckDrivenPropertyChecks
    with AppkitTesting {
  import ErgoBox._
  import Iso._
  import JavaHelpers.UniversalConverter

  type Registers = Map[NonMandatoryRegisterId, _ <: EvaluatedValue[_ <: SType]]

  def boxWithRegs(regs: Registers) = {
    ErgoBox(10, TrivialProp.TrueProp, 100, Nil, regs)
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
}
