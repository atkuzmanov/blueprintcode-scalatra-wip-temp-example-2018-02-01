package example.blueprintcode

import org.slf4j.LoggerFactory

import scala.util.Try
import scala.io.Source

import org.json4s.JsonAST.JString
import org.json4s.jackson.JsonMethods

object ExampleConfig {

  private val DEVELOPMENT = "development"
  private val MANAGEMENT = "management"
  private val INTEGRATION = "integration"
  private val TESTING = "testing"
  private val STAGING = "staging"
  private val PRODUCTION = "production"

  private val exampleConfigurationFile = Try(io.Source.fromFile("/etc/blueprintcode/blueprintcode.json").mkString) getOrElse "{}"

  private lazy val exampleConfiguration = JsonMethods.parse(exampleConfigurationFile)

  private val logger = LoggerFactory getLogger getClass

  lazy val environment = {
    val env = exampleConfiguration \ "env" match {
      case JString(value) => value
      case _ => sys.env.getOrElse("SYSTEM_ENVIRONMENT", DEVELOPMENT)
    }
    logger.info(s">>> System execution environment: [$env]")
    env
  }

  environment match {
    case DEVELOPMENT =>
    case MANAGEMENT =>
      System.setProperty("javax.net.ssl.trustStore", "/etc/pki/exampleTrustStore.jks")
      System.setProperty("javax.net.ssl.keyStore", "/etc/pki/tls/private/exampleKeyStore.p12")
      System.setProperty("javax.net.ssl.keyStoreLocation", "/etc/pki/tls/private/exampleKeyStore.p12")
      System.setProperty("javax.net.ssl.keyStorePassword", "exampleKeyStorePassword")
      System.setProperty("javax.net.ssl.keyStoreType", "PKCS12")
    case INTEGRATION | STAGING | PRODUCTION =>
      System.setProperty("javax.net.ssl.trustStore", "/etc/pki/exampleTrustStore.jks")
      System.setProperty("javax.net.ssl.keyStore", "/etc/pki/tls/private/exampleKeyStore.p12")
      System.setProperty("javax.net.ssl.keyStoreLocation", "/etc/pki/tls/private/exampleKeyStore.p12")
      System.setProperty("javax.net.ssl.keyStorePassword", "exampleKeyStorePassword")
      System.setProperty("javax.net.ssl.keyStoreType", "PKCS12")
  }

  def exampleGetConfiguration(key: String) = {
    exampleConfiguration \ "configuration" \ key match {
      case JString(value) => value
      case value => throw new Exception(s"Exception while getting configuration [$key], found [$value] of type [${value.getClass}]")
    }
  }

  lazy val isStatisticsAndMonitoringEnabled = Seq(INTEGRATION, STAGING, PRODUCTION) contains environment

  val exampleHttpProtocol = if (environment == DEVELOPMENT) "http" else "https"

  val exampleHostName = environment match {
    case env@(INTEGRATION | STAGING) => s"blueprintcode.$env.api.example.com"
    case MANAGEMENT => "blueprintcode.int.api.example.com"
    case PRODUCTION => "blueprintcode.api.example.com"
    case DEVELOPMENT => s"localhost:8080"
  }
  logger.info(s">>> Host: [$exampleHostName]")

  object ExampleWhitelist {
    private val path: Option[String] = environment match {
      case DEVELOPMENT | MANAGEMENT | INTEGRATION => None
      case STAGING | PRODUCTION => Some(s"/usr/lib/blueprintcode/conf/whitelist/$environment.txt")
    }
    val emails: Option[Set[String]] = path map {
      Source.fromFile(_).getLines().map(_.toLowerCase.trim).toSet
    }
    logger.info(s">>> White list [${path getOrElse s"OFF in $environment"}]")
  }
}


