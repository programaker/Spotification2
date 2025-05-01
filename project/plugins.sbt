Seq(
  "org.scalameta" % "sbt-scalafmt" % "2.5.4",
  "io.spray" % "sbt-revolver" % "0.10.0",
  "com.github.sbt" % "sbt-native-packager" % "1.9.16",
  "org.jmotor.sbt" % "sbt-dependency-updates" % "1.2.9",
  
  // https://github.com/philippus/sbt-dotenv/#illegal-reflective-access-warnings-and-exceptions
  "nl.gn0s1s" % "sbt-dotenv" % "3.1.1",
  
  // https://scalacenter.github.io/scalafix/docs/users/installation.html
  "ch.epfl.scala" % "sbt-scalafix" % "0.14.2"
).map(addSbtPlugin)
