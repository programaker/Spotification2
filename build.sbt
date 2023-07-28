// 0.0.0
// | | |_ bugfixes, small improvements
// | |___ non-api changes
// | ____ api changes
def Spotification2 = "0.1.0"

def Scala = "3.3.0"

// Try Alpaquita: https://bell-sw.com/blog/bellsoft-introduces-alpaquita-linux/
def DockerImage = "eclipse-temurin:19.0.1_10-jre-focal"

lazy val root = project
  .in(file("."))
  .settings(
    organization := "com.github.programaker",
    name := "spotification2",
    version := Spotification2,

    scalaVersion := Scala,
    libraryDependencies ++= Dependencies.libraries,
    Compile / mainClass := Some("spotification2.app.Spotification2HttpApp"),

    dockerBaseImage := DockerImage,
    dockerExposedPorts += 8080,

    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-language:strictEquality",
      "-Wunused:all",
      "-Wvalue-discard",
      "-Ykind-projector:underscores",
      "-Yimports:java.lang,scala,scala.Predef,scala.util.chaining"
    )
  )
  .enablePlugins(
    JavaAppPackaging,
    DockerPlugin,
    AshScriptPlugin
  )
