// 0.0.0
// | | |_ bugfixes, small improvements
// | |___ non-api changes
// | ____ api changes
val Spotification2 = "0.0.1"

val Scala = "3.7.0" 
val Java = "21"

// Try Alpaquita, maybe: https://bell-sw.com/blog/bellsoft-introduces-alpaquita-linux/
val DockerImage = "eclipse-temurin:21.0.1_12-jre-alpine"

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
    // scalafixOnCompile := true,

    // To run scalafix from sbt console
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,

    scalacOptions ++= Seq(
      "-release", Java,
      "-encoding", "utf8",
      "-deprecation",
      "-language:strictEquality",
      "-Wunused:all",
      "-Wvalue-discard",
      "-Xkind-projector:underscores",
      "-Yimports:java.lang,scala,scala.Predef,scala.util.chaining"
    ),

    javacOptions ++= Seq(
      "-source", Java, 
      "-target", Java
    ),
  )
  .enablePlugins(
    JavaServerAppPackaging,
    DockerPlugin,
    AshScriptPlugin // required to build an Alpine image
  )
