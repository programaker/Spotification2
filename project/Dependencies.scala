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
    "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % Versions.TapirCore,
    "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % Versions.TapirCore % Test,
    "com.softwaremill.sttp.client3" %% "circe" % Versions.SttpCirce % Test,

    "io.circe" %% "circe-core" % Versions.CirceCore,
    "io.circe" %% "circe-generic" % Versions.CirceCore,
    "io.circe" %% "circe-parser" % Versions.CirceCore,
    "io.circe" %% "circe-refined" % Versions.CirceCore,

    "org.scalameta" %% "munit" % Versions.MUnit % Test,
    "org.scalameta" %% "munit-scalacheck" % Versions.MUnit % Test,
    "org.typelevel" %% "munit-cats-effect-3" % Versions.MUnitCatsEffect % Test,

    "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore,

    "co.fs2" %% "fs2-core" % Versions.Fs2Core,

    "org.typelevel" %% "log4cats-slf4j" % Versions.Log4Cats,
    "ch.qos.logback" % "logback-classic" % Versions.Logback,
  )

  private object Versions {
    val Refined = "0.11.0"
    val CatsCore = "2.10.0"
    val CatsEffect = "3.5.2"
    val CatsCollectionsCore = "0.9.8"
    val Fs2Core = "3.9.3"
    val CirceCore = "0.14.6"
    val PureConfigCore = "0.17.4"
    val Log4Cats = "2.6.0"
    val Logback = "1.4.11"
    val Mouse = "1.2.2"
    val TapirCore = "1.9.2"
    val MUnit = "0.7.29"
    val MUnitCatsEffect = "1.0.7"
    val SttpCirce = "3.9.1"
  }
}