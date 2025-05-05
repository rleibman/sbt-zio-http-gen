package zio.http.gen

import sbt.{Def, *}
import sbt.Keys.*

object ZioHttpGenPlugin extends AutoPlugin {

  object autoImport extends ZioHttpGenKeys
  import TupleSyntax.*
  import autoImport.*

  override def requires = plugins.JvmPlugin

  lazy override val projectSettings: Seq[Setting[_]] =
    zioHttpGenScopedSettings(Compile) ++ zioHttpGenDefaultSettings

  def zioHttpGenScopedSettings(conf: Configuration): Seq[Setting[_]] =
    inConfig(conf)(
      Seq(
        generateDefinitions := codegen.value,
        sourceGenerators += codegen.taskValue.map(_.map(_.toPath.toFile))
      )
    )

  def standardParamSetting: Setting[ZioHttpGenConfiguration] =
    zioHttpGenConfiguration := ZioHttpGenConfiguration(
      zioHttpGenOpenApiFiles.value,
      zioHttpGenPackageName.value,
      zioHttpGenScalafmtPath.value,
      zioHttpGenObjectPrefix.value,
      zioHttpGenAdditionalImports.value,
      zioHttpGenGenerateClient.value,
      zioHttpGenGenerateServer.value
    )

  def zioHttpGenDefaultSettings: Seq[Setting[_]] =
    Seq(
      zioHttpGenOpenApiFiles      := Seq(baseDirectory.value / "openApi.json"),
      zioHttpGenPackageName       := "zio.http.generated",
      zioHttpGenScalafmtPath      := None,
      zioHttpGenObjectPrefix      := "",
      zioHttpGenAdditionalImports := Nil,
      zioHttpGenGenerateClient    := false,
      zioHttpGenGenerateServer    := false,
      standardParamSetting
    )

  private def codegen: Def.Initialize[Task[Seq[File]]] =
    Def.task {
      val log = sLog.value
      log.info("Generating endpoint code from openapi specs...")
      (
        zioHttpGenConfiguration,
        sourceManaged,
        streams,
        scalaVersion
      ).flatMap {
        (
          config:        ZioHttpGenConfiguration,
          sourceManaged: File,
          streams:       TaskStreams,
          scalaVersion:  String
        ) =>
          ZioHttpGenTask(config, sourceManaged, scalaVersion).files
      }.value
    }

}
