
lazy val root = (project in file(".")).
  settings(
    name := "gavel",
    version := "1.0",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "fastparse" % "0.4.1",
      "com.github.scopt" %% "scopt" % "3.4.0",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )
