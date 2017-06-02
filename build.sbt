name := "email collect"

version := "0.1"

scalaVersion := "2.12.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.102-R11",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.3",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "mysql" % "mysql-connector-java" % "5.1.41"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

fork := true

shellPrompt := { state => System.getProperty("user.name") + s":${name.value}> " }