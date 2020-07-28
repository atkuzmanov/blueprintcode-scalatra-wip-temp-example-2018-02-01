import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.server.Server

import org.scalatra.servlet.ScalatraListener

object ExampleLauncher extends App {

  val examplePort = sys.props.get("jetty.port") map (_.toInt) getOrElse 8080

  val exampleServer = new Server(examplePort)
  val exampleContext = new WebAppContext

  exampleContext.setContextPath("/")
  exampleContext.setResourceBase("src/main/webapp")
  exampleContext.addServlet(classOf[DefaultServlet], "/")
  exampleContext.addEventListener(new ScalatraListener)

  exampleServer.setHandler(exampleContext)
  exampleServer.start()
  exampleServer.join()
}
