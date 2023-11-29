package spotification2.testutil.syntax.gen

import cats.Applicative
import cats.syntax.all.*
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

extension [A](gen: Gen[A])
  def run(): A =
    gen.pureApply(Gen.Parameters.default, Seed.random())

  def runF[F[_]: Applicative]: F[A] =
    gen.run().pure
