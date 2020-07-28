package example.blueprintcode.docs

object ExampleApiSpecification {

  val ExampleDateType = Map("type" -> "string", "example" -> "2014-08-11T15:59:37.123Z")

  val ExampleLocatorType = Map("type" -> "string", "example" -> "urn:example:qwertyui-1q2w-3e4r-5t6y-7u8i9o0p1a2s3d4f")

  val ExampleSwaggerStatus = Seq("1. Status")

  val exampleInfo = ExampleApiInfo("Blueprintcode Example API", "Describe API here.", "1.0.0")

  val exampleDefinitions = Map[String, String]()

  def arrayOf(fieldType: Any) = Map("type" -> "array", "items" -> fieldType)

  val exampleDefaultResponses = Map(
    "404" -> ExampleResponse("Not found"),
    "200" -> ExampleResponse("OK"),
    "500" -> ExampleResponse("Internal Server Error"))

  val examplePaths = Map(
    "/status" -> Map(
      GET -> ExampleOperation("Status", "Indicates whether the application is healthy",
        ExampleSwaggerStatus,
        responses = exampleDefaultResponses))
  )
}
