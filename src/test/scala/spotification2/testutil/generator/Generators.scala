package spotification2.testutil.generator

import org.scalacheck.Gen

import spotification2.auth.ClientId
import spotification2.common.HexString32
import spotification2.common.HexString32P
import spotification2.common.syntax.refined.*

object Generators:
  def genHex32String: Gen[HexString32] =
    Gen
      .listOfN(32, Gen.hexChar)
      .map(_.mkString.toLowerCase().refineU[HexString32P])

  def genClientId: Gen[ClientId] = 
    genHex32String.map(ClientId(_))
