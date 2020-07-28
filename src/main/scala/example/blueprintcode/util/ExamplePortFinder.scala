package example.blueprintcode.util

import java.net.ServerSocket

object ExamplePortFinder {
  private def exampleFindFreePort = {
    val serverSocket = new ServerSocket(0)
    val port = serverSocket.getLocalPort
    serverSocket.close()
    port
  }
  val blueprintCodeMockServerPort = exampleFindFreePort
  val blueprintCodePort = exampleFindFreePort
}
