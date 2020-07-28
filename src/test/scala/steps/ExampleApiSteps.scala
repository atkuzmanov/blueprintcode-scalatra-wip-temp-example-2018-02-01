package steps

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}

import com.ning.http.client.Response

import cucumber.api.scala.{EN, ScalaDsl}

import scala.concurrent.ExecutionContext
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest.FunSuiteLike

import org.scalatra.test.scalatest.ScalatraSuite

import dispatch.{Http, Req, host}

import java.util.concurrent.Executors

import org.json4s.JsonAST.JValue

import example.Servlets

trait ExampleApiSteps extends ScalatraSuite with FunSuiteLike with ScalaDsl with EN {
  implicit val executionContext = ExecutionContext fromExecutorService Executors.newSingleThreadExecutor

  ExampleApiTestServer
}

object ExampleApiTestServer {
  private val port = 8080
  private val server = new Server(port)
  private val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)

  val blueprintCodeHost = host("localhost", port)

  implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global

  Servlets().foreach(servletMapping => {
    val servlet = servletMapping._1
    val endpoint = servletMapping._2
    val servletHolder = new ServletHolder(servlet)
    context.addServlet(servletHolder, endpoint)
  })
  context.setResourceBase("src/main/webapp")
  server.start()
}

object ExampleResponseHandler {
  var statusCode: Int = _
  var bodyAsJson: JValue = _
  var bodyAsString: String = _
}
