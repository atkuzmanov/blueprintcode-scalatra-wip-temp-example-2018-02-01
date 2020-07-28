package example

import example.blueprintcode.api._

object Servlets {
  def apply(): Map[ExampleBaseApi, String] = {
    Map(
      new ExampleIndexApi -> "/*",
      ExampleSwaggerUi -> "/docs",
      ExampleDocsApi -> "/api"
    )
  }
}
