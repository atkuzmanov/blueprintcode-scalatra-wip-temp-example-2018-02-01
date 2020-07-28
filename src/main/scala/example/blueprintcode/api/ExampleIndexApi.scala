package example.blueprintcode.api

import org.apache.commons.io.IOUtils

class ExampleIndexApi extends ExampleBaseApi {

  private def serveStaticContentFromJar(uri: String) = {
    contentType = ExampleResourceMapper.exampleGetContentType(uri)
    Option(getClass.getResourceAsStream(uri)) match {
      case Some(inputStream) => IOUtils.toByteArray(inputStream)
      case None => resourceNotFound()
    }
  }

  get("/") {
    contentType = "application/json"
    // Monitoring statistics go here.
    """{"application":"blueprintcode"}"""
  }

  get("/status") {
    contentType = "application/json"
    // Monitoring statistics go here.
    """{"status":"OK"}"""
  }

  notFound {
    serveStaticContentFromJar(request.getRequestURI)
  }
}
