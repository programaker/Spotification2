Seq(
  "org.jmotor.sbt" % "sbt-dependency-updates" % "1.2.2",
  "org.scalameta" % "sbt-scalafmt" % "2.5.0",
  "com.typesafe.sbt" % "sbt-native-packager" % "1.8.1",
  "org.wartremover" % "sbt-wartremover" % "3.0.14"
).map(addSbtPlugin)
