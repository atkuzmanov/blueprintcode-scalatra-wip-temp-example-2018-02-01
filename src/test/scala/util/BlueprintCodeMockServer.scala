package util

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.{MappingBuilder, UrlMatchingStrategy, WireMock}
import com.github.tomakehurst.wiremock.client.WireMock._

import example.blueprintcode.util.ExamplePortFinder

object BlueprintCodeMockServer {
  val port = ExamplePortFinder.blueprintCodeMockServerPort
  val stubbedServerUrl = s"http://localhost:$port"
  println(s">>> Running Example Mock Server for test on [$stubbedServerUrl]")
  val mockServer = WireMockServer(port)
}
