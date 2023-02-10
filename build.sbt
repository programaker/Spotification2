// 0.0.0
// | | |_ bugfixes, small improvements
// | |___ non-api changes
// | ____ api changes
def Spotification2 = "0.1.0"

def Scala = "3.2.1"

// TODO
// Update this to Java 19 
// Try Alpaquita: https://bell-sw.com/blog/bellsoft-introduces-alpaquita-linux/
def DockerImage = "bellsoft/liberica-openjre-alpine:17.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    organization := "com.github.programaker",
    name := "spotification2",
    version := Spotification2,

    scalaVersion := Scala,
    libraryDependencies ++= Dependencies.libraries,

    wartremoverErrors ++= Seq(
      Wart.FinalCaseClass,
      Wart.Throw,
      Wart.Return
    ),
    wartremoverWarnings ++= Warts.allBut(
      Wart.Recursion,
      Wart.ImplicitParameter,
      Wart.Any,
      Wart.Nothing,
      Wart.ImplicitConversion,
      Wart.Overloading,
      Wart.JavaSerializable,
      Wart.Serializable,
      Wart.Product,
      Wart.Equals
    ),
    
    // disable Wartremover in console. Not only it's unnecessary but also cause error in Scala 2.13.2+
    Compile / console / scalacOptions := (console / scalacOptions).value.filterNot(_.contains("wartremover")),
    // Compile / mainClass := Some("<TODO>"),

    dockerBaseImage := DockerImage,
    dockerExposedPorts += 8080,

    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-Ykind-projector:underscores"
    )
  )
  .enablePlugins(
    JavaAppPackaging,
    DockerPlugin,
    AshScriptPlugin
  )
