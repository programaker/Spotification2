import sbt._

object Dependencies {
  val libraries = Seq(
    "eu.timepit" %% "refined" % Versions.Refined,
    "eu.timepit" %% "refined-cats" % Versions.Refined,
    "eu.timepit" %% "refined-pureconfig" % Versions.Refined,
    "eu.timepit" %% "refined-scalacheck" % Versions.Refined % Test,

    "org.typelevel" %% "cats-core" % Versions.CatsCore,
    "org.typelevel" %% "cats-effect" % Versions.CatsEffect,
    "org.typelevel" %% "cats-collections-core" % Versions.CatsCollectionsCore,
    "org.typelevel" %% "mouse" % Versions.Mouse,

    "com.softwaremill.sttp.tapir" %% "tapir-core" % Versions.TapirCore,
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % Versions.TapirCore,
    "com.softwaremill.sttp.tapir" %% "tapir-netty-server-cats" % Versions.TapirCore,
    "com.softwaremill.sttp.tapir" %% "tapir-sttp-client4" % Versions.TapirCore,
    "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub4-server" % Versions.TapirCore % Test,
    "com.softwaremill.sttp.client4" %% "circe" % Versions.SttpCirce % Test,

    "io.circe" %% "circe-core" % Versions.CirceCore,
    "io.circe" %% "circe-generic" % Versions.CirceCore,
    "io.circe" %% "circe-parser" % Versions.CirceCore,
    "io.circe" %% "circe-refined" % Versions.CirceRefined,

    "org.scalameta" %% "munit" % Versions.MUnit % Test,
    "org.scalameta" %% "munit-scalacheck" % Versions.MUnitScalacheck % Test,
    "org.typelevel" %% "munit-cats-effect" % Versions.MUnitCatsEffect % Test,

    "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore,

    "co.fs2" %% "fs2-core" % Versions.Fs2Core,

    "org.typelevel" %% "log4cats-slf4j" % Versions.Log4Cats,
    "ch.qos.logback" % "logback-classic" % Versions.Logback,
  )

  private object Versions {
    val Refined = "0.11.3"
    val CatsCore = "2.13.0"
    val CatsEffect = "3.6.1"
    val CatsCollectionsCore = "0.9.9"
    val Fs2Core = "3.12.0"
    val CirceCore = "0.14.13"
    val CirceRefined = "0.14.8"
    val PureConfigCore = "0.17.9"
    val Log4Cats = "2.7.0"
    val Logback = "1.5.18"
    val Mouse = "1.3.2"
    val TapirCore = "1.11.25"
    val MUnit = "1.1.1"
    val MUnitCatsEffect = "2.1.0"
    val MUnitScalacheck = "1.1.0"
    val SttpCirce = "4.0.3"
  }
}