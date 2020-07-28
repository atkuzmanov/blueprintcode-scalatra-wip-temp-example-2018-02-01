package example.blueprintcode

package object docs {

  sealed trait ExampleResponse

  object ExampleResponse {
    def apply(description: String): ExampleResponse = ExampleResponseEmpty(description)

    def apply(description: String, schema: Ref): ExampleResponse = ExampleResponseRef(description, Some(schema))

    def apply(description: String, schema: Map[String, Any]): ExampleResponse = ExampleResponseMap(description, Some(schema))
  }

  private case class ExampleResponseMap(description: String, schema: Option[Map[String, Any]]) extends ExampleResponse

  private case class ExampleResponseRef(description: String, schema: Option[Ref]) extends ExampleResponse

  private case class ExampleResponseEmpty(description: String) extends ExampleResponse

  case class Ref(`$ref`: String)

  object Ref {
    def to(something: String) = Ref(s"#/definitions/$something")
  }

  case class ExampleApiInfo(title: String, description: String, version: String)

  case class ExampleParameter(name: String, description: String = "", in: String = "query", required: Boolean = false, `type`: String = "string", schema: Option[Ref] = None)

  case class Swagger(
                      info: ExampleApiInfo,
                      host: String,
                      paths: Map[String, Map[String, ExampleOperation]],
                      schemes: Seq[String],
                      definitions: Map[String, Any],
                      swagger: String = "2.0")

  case class ExampleOperation(
                               summary: String,
                               description: String,
                               tags: Seq[String],
                               parameters: Seq[ExampleParameter] = Nil,
                               responses: Map[String, ExampleResponse] = Map.empty)

  val Ok = "200"
  val Accepted = "202"
  val NoContent = "204"
  val BadRequest = "400"
  val NotFound = "404"

  val GET = "get"
  val PUT = "put"
  val POST = "post"
  val DELETE = "delete"
}
