addCommandAlias("namaste", "~testOnly scalafunctionalkoans.Koans")

lazy val root = (project in file("."))
  .settings(
    name := "scala-functional-koans",
    version := "0.1",

    scalaVersion := "2.13.7",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature"),

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.9" % Test
    ),

    test / traceLevel := -1,
    test / parallelExecution := false,
    logLevel := Level.Info,
    showTiming := false,
    showSuccess := false
  )
