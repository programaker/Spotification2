import sbt._

object Dependencies {
  val libraries = Seq(
    "eu.timepit" %% "refined" % Versions.Refined,
    "eu.timepit" %% "refined-cats" % Versions.Refined,
    "eu.timepit" %% "refined-scalacheck" % Versions.Refined % Test,

    "org.typelevel" %% "cats-core" % Versions.CatsCore,
    "org.typelevel" %% "cats-effect" % Versions.CatsEffect,
    "org.typelevel" %% "mouse" % Versions.Mouse,

    "com.softwaremill.sttp.tapir" %% "tapir-core" % Versions.TapirCore,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % Versions.TapirCore,

    "io.circe" %% "circe-core" % Versions.CirceCore,
    "io.circe" %% "circe-generic" % Versions.CirceCore,
    "io.circe" %% "circe-parser" % Versions.CirceCore,
    "io.circe" %% "circe-refined" % Versions.CirceCore,

    "com.disneystreaming" %% "weaver-cats" % Versions.Weaver % Test,
    "com.disneystreaming" %% "weaver-scalacheck" % Versions.Weaver % Test,

    "com.github.valskalla" %% "odin-core" % Versions.OdinCore,

    "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore,

    "co.fs2" %% "fs2-core" % Versions.Fs2Core,

    "ch.qos.logback" % "logback-classic" % Versions.Logback,
  )

  private object Versions {
    val Refined = "0.11.0"
    val CatsCore = "2.9.0"
    val CatsEffect = "3.5.1"
    val Fs2Core = "3.7.0"
    val CirceCore = "0.14.5"
    val OdinCore = "0.13.0"
    val PureConfigCore = "0.17.4"
    val Logback = "1.4.8"
    val Mouse = "1.2.1"
    val TapirCore = "1.6.3"
    val Weaver = "0.8.3"
  }
}