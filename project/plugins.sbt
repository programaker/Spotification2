Seq(
  "org.scalameta" % "sbt-scalafmt" % "2.5.2",
  "io.spray" % "sbt-revolver" % "0.10.0",
  "com.github.sbt" % "sbt-native-packager" % "1.9.16",
  
  // https://github.com/philippus/sbt-dotenv/#illegal-reflective-access-warnings-and-exceptions
  "nl.gn0s1s" % "sbt-dotenv" % "3.0.0",
  
  // https://scalacenter.github.io/scalafix/docs/users/installation.html
  "ch.epfl.scala" % "sbt-scalafix" % "0.11.1"
).map(addSbtPlugin)
