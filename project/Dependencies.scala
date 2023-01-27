import sbt._

object Dependencies {
  def libraries = Seq(
    "eu.timepit" %% "refined" % Versions.Refined,
    "eu.timepit" %% "refined-cats" % Versions.Refined,

    "org.typelevel" %% "cats-core" % Versions.CatsCore,

    "dev.zio" %% "zio" % Versions.Zio,
    "dev.zio" %% "zio-streams" % Versions.Zio,
    "dev.zio" %% "zio-logging" % Versions.ZioLogging,
    "dev.zio" %% "zio-http" % Versions.ZioHttp,
    
    "dev.zio" %% "zio-json" % Versions.ZioJson,
    // "dev.zio" % "zio-json-interop-refined" % Versions.ZioJson,
    
    "dev.zio" %% "zio-config" % Versions.ZioConfig,
    // "dev.zio" %% "zio-config-refined" % Versions.ZioConfig,
  )

  private object Versions {
    def Refined = "0.10.1"
    def CatsCore = "2.9.0"
    def Zio = "2.0.6"
    def ZioJson = "0.4.2"
    def ZioLogging = "2.1.8"
    def ZioHttp = "0.0.4"
    def ZioConfig = "3.0.6"
  }
}