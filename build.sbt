// 0.0.0
// | | |_ bugfixes, small improvements
// | |___ non-api changes
// | ____ api changes
val Spotification2 = "0.1.0"

val Scala = "3.2.2"

// TODO
// Update this to Java 19 
// Try Alpaquita: https://bell-sw.com/blog/bellsoft-introduces-alpaquita-linux/
val DockerImage = "eclipse-temurin:19.0.1_10-jre-focal"

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
    Compile / mainClass := Some("spotification2.app.Spotification2HttpApp"),

    dockerBaseImage := DockerImage,
    dockerExposedPorts += 8080,

    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-Ykind-projector:underscores",
      "-language:strictEquality",
    )
  )
  .enablePlugins(
    JavaAppPackaging,
    DockerPlugin
  )
