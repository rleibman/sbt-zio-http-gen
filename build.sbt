import scala.collection.JavaConverters._

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "dev.zio"
ThisBuild / organizationName := "zio"

val zioHttpVersion = "3.2.0"

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    scalaVersion     := "2.12.20",
    name             := "sbt-zio-http-gen",
    sbtPlugin        := true,
    sbtTestDirectory := sourceDirectory.value / "sbt-test",
    scriptedLaunchOpts += ("-Dplugin.version=" + version.value),
    scriptedLaunchOpts ++= java.lang.management.ManagementFactory.getRuntimeMXBean.getInputArguments.asScala
      .filter(a => Seq("-Xmx", "-Xms", "-XX", "-Dfile").exists(a.startsWith)),
    scriptedBufferLog := false,
    libraryDependencies ++= Seq(
      "dev.zio"       %% "zio-http"       % zioHttpVersion withSources (),
      "dev.zio"       %% "zio-http-gen"   % zioHttpVersion withSources (),
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % Test
    )
  )
