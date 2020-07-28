package example.blueprintcode.api

import example.blueprintcode.docs.{ExampleApiSpecification, Swagger}
import example.blueprintcode.ExampleConfig

import org.json4s.{DefaultFormats, Formats}

import org.scalatra.FutureSupport
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext

trait ExampleDocsApi extends ExampleBaseApi with JacksonJsonSupport with FutureSupport {

  import ExampleApiSpecification._

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  protected implicit def executor: ExecutionContext = ExecutionContext.global

  before() {
    contentType = formats("json")
  }

  get("/") {
    Swagger(exampleInfo, ExampleConfig.exampleHostName, examplePaths, Seq(ExampleConfig.exampleHttpProtocol), exampleDefinitions)
  }
}

object ExampleDocsApi extends ExampleDocsApi
