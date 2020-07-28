package example.blueprintcode.api

import org.slf4j.LoggerFactory
import org.scalatra.ScalatraServlet

import scala.util.control.NonFatal

trait ExampleBaseApi extends ScalatraServlet {

  private val logger = LoggerFactory getLogger getClass

  error {
    case NonFatal(e) => {
      logger error e.getMessage
      logger error e.getCause.toString
      logger error e.getStackTrace.toString
      response.setStatus(500)
      s"Exception: ${e.getMessage}"
    }
  }
}
