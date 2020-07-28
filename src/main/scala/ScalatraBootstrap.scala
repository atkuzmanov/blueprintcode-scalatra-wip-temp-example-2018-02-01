
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import com.amazonaws.regions.{Region, Regions}

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.blocking
import scala.concurrent.Future

import org.scalatra._

import javax.servlet.ServletContext

import example.Servlets
import example.blueprintcode.ExampleConfig._

class ScalatraBootstrap extends LifeCycle {
  implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global

  override def init(context: ServletContext) {
    reportMetrics()

    Servlets().foreach(servletMapping => {
      val servlet = servletMapping._1
      val endpoint = servletMapping._2

      context.mount(servlet, endpoint)
    })
  }

  override def destroy(context: ServletContext) {
  }

  def reportMetrics() = {
    if (isStatisticsAndMonitoringEnabled) {
      val client = new AmazonCloudWatchClient {
        setRegion(Region.getRegion(Regions.EU_WEST_1))
      }
    }
  }
}
