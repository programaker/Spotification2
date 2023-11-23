// 0.0.0
// | | |_ bugfixes, small improvements
// | |___ non-api changes
// | ____ api changes
val Spotification2 = "0.0.1"

val Scala = "3.3.1"

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

    Compile / mainClass := Some("spotification2.app.Spotification2HttpApp"),

    // To fix a warning while running from sbt console
    Compile / run / fork := true,

    dockerBaseImage := DockerImage,
    dockerExposedPorts += 8080,

    // There are some gotchas when you run scalafix on compile. 
    // Better run manually or when building the image.
    // https://scalacenter.github.io/scalafix/docs/users/installation.html#run-scalafix-automatically-on-compile
    //
    // scalafixOnCompile := true,
    // semanticdbEnabled := true,
    // semanticdbVersion := scalafixSemanticdb.revision,

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
    JavaServerAppPackaging,
    DockerPlugin
  )
