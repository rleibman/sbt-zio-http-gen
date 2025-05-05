package zio.http.gen

import sbt.*
import zio.http.endpoint.openapi.OpenAPI
import zio.http.gen.openapi.EndpointGen
import zio.http.gen.scala.CodeGen

import java.nio.file.{Files, Paths}

case class ZioHttpGenTask(
  config:       ZioHttpGenConfiguration,
  outDir:       File,
  scalaVersion: String // If you want to change the way things get generated depending on scala version
) {

  def files: Task[Seq[File]] = {
    val res = config.openApiFiles.flatMap { file =>
      processFile(file)
    }
    task(res)
  }

  private def processFile(file: File): Seq[File] =
    OpenAPI
      .fromJson(Files.readString(file.toPath))
      .fold(
        err => {
          println(s"Error parsing OpenAPI file: $err")
          Seq.empty
        },
        (api: OpenAPI) => {
          val endpointFiles = CodeGen.writeFiles(
            EndpointGen.fromOpenAPI(api),
            basePath = Paths.get(outDir.getAbsolutePath),
            basePackage = config.packageName,
            scalafmtPath = config.scalafmtPath.map(_.toPath)
          )

          if (config.generateClient) {
            Seq.empty
          } else {
            Seq.empty
          }
          if (config.generateServer) {
            Seq.empty
          } else {
            Seq.empty
          }

          endpointFiles.map(_.toFile)
        }
      ).toSeq

}
