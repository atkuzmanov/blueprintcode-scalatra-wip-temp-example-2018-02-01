package util

import scala.concurrent.{Await, Awaitable}
import scala.concurrent.duration._

import org.json4s.native.JsonMethods._
import org.json4s._

trait ExampleTestUtil {

  def complete[T](awaitable: => Awaitable[T]): T = Await.result(awaitable, 5.seconds)

  def fixtureAsJson(path: String): JValue = {
    parse {
      io.Source.fromFile(s"src/test/resources/fixtures/$path").mkString
    }
  }
}
