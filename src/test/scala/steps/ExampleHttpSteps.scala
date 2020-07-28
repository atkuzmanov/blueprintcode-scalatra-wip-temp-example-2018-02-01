package steps

import dispatch.Http

import org.scalatest.MustMatchers._

import util.BlueprintCodeMockServer._

import steps.ExampleResponseHandler._

import scala.concurrent.duration._
import scala.concurrent.Await

class ExampleHttpSteps extends ExampleApiSteps {
  Before { _ =>
    beforeAll()
    mockServer.reset()
  }

  When("""^I request the application status$""") { () =>
    val request = (ExampleApiTestServer.blueprintCodeHost / "status").GET
    val response = Await.result(Http(request), 10.seconds)
    statusCode = response.getStatusCode
  }

  Then("""^an OK status is returned$""") { () =>
    statusCode mustBe 200
  }

  When("""^I request a static file$"""){ () =>
    val request = (ExampleApiTestServer.blueprintCodeHost / "css" / "reset.css").GET
    val response = Await.result(Http(request), 10.seconds)
    statusCode = response.getStatusCode
    bodyAsString = response.getResponseBody
  }

  Then("""^the static file is returned$"""){ () =>
    statusCode mustBe 200
    bodyAsString must include("HTML5 display-role reset")
 }
 When("""^I request a static file that doesn't exist$"""){ () =>
    val request = (ExampleApiTestServer.blueprintCodeHost / "css" / "unknown.txt").GET
    val response = Await.result(Http(request), 10.seconds)
    statusCode = response.getStatusCode
 }
  Then("""^I receive a not found response$"""){ () =>
    statusCode mustBe 404
   }

  After { _ =>
    afterAll()
  }
}
