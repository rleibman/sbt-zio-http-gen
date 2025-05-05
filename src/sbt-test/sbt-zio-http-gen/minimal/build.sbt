val zioHttpVersion = "3.2.0"

lazy val root = (project in file("."))
  .enablePlugins(ZioHttpGenPlugin)
  .settings(
    scalaVersion := "3.6.0",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-http" % zioHttpVersion withSources ()
    )
  )
