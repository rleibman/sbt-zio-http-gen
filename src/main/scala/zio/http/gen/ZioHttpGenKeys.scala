package zio.http.gen

import sbt.*

case class ZioHttpGenConfiguration(
  openApiFiles:      Seq[File],
  packageName:       String,
  scalafmtPath:      Option[File],
  objectPrefix:      String,
  additionalImports: Seq[String],
  generateClient:    Boolean,
  generateServer:    Boolean
  // TODO add all options required for client generation
  // client package
  // TODO add all options required for server generation
  // server package
)

trait ZioHttpGenKeys {

  lazy val zioHttpGenOpenApiFiles = settingKey[Seq[File]]("A list of files that contain OpenAPI specs")
  lazy val zioHttpGenPackageName = settingKey[String]("The name for the generated package")
  lazy val zioHttpGenScalafmtPath = settingKey[Option[File]]("The path to the scalafmt config file")
  lazy val zioHttpGenObjectPrefix = settingKey[String]("The prefix for the generated objects")
  lazy val zioHttpGenAdditionalImports = settingKey[Seq[String]]("List of additional imports to add to the generated code")
  lazy val zioHttpGenGenerateClient = settingKey[Boolean]("Whether to generate a client or not")
  lazy val zioHttpGenGenerateServer = settingKey[Boolean]("Whether to generate a server or not")
  lazy val zioHttpGenConfiguration = settingKey[ZioHttpGenConfiguration]("Aggregation of all other settings, manually set value will be disregarded")

  lazy val generateDefinitions = taskKey[Unit]("The task that generates zio definitions based on the input openapi files")

}

object OpenapiCodegenKeys extends ZioHttpGenKeys
