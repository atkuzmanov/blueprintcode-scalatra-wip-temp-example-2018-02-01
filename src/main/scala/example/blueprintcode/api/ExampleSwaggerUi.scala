package example.blueprintcode.api

trait ExampleSwaggerUi extends ExampleBaseApi {
  before() {
    contentType = "text/html"
  }

  get("/") {
    getClass.getResourceAsStream("/index.html")
  }
}

object ExampleSwaggerUi extends ExampleSwaggerUi {}
