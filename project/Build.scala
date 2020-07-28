import sbt._
import sbt.{Build => SbtBuild}
import sbt.Keys._
import org.scalatra.sbt._
import com.earldouglas.xsbtwebplugin.PluginKeys._
import com.earldouglas.xsbtwebplugin.WebPlugin._
import templemore.sbt.cucumber.CucumberPlugin
import sbtassembly.Plugin._
import AssemblyKeys._

object Build extends SbtBuild {

  val Organization = "[ORGANISATION-NAME]"
  val Name = "blueprintcode"
  val Version = Option(System.getenv("BUILD_VERSION")) getOrElse "DEV"
  val ScalaVersion = "2.11.8"
  val ScalatraVersion = "2.4.1"
  val JettyVersion = "9.4.2.v20170220"
  val Json4sVersion = "3.4.0"

  val JettyPort = sys.props.get("jetty.port") map (_.toInt) getOrElse 8080

  val dependencies = Seq(

    // core
    "org.scalatra" %% "scalatra" % ScalatraVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.7" % "runtime",
    "org.scalatra" %% "scalatra-json" % ScalatraVersion,
    "org.json4s" %% "json4s-jackson" % Json4sVersion,
    "org.json4s" %% "json4s-native" % Json4sVersion,
    "org.json4s" %% "json4s-ext" % Json4sVersion,
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
    "commons-io" % "commons-io" % "2.5",

    // testing
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "info.cukes" %% "cucumber-scala" % "1.2.4" % "compile,test",
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.3" % "test",
    "com.github.tomakehurst" % "wiremock" % "1.57" ,

    // jetty
    "org.eclipse.jetty" % "jetty-webapp" % JettyVersion % "container;compile"
  )

  lazy val project = Project(
    Name,
    file("."),
    settings =
      Defaults.coreDefaultSettings ++
      ScalatraPlugin.scalatraWithJRebel ++
      assemblySettings ++
      Seq(CucumberPlugin.cucumberSettings: _*) ++
      Seq(
        CucumberPlugin.cucumberFeaturesLocation := "cucumber",
        CucumberPlugin.cucumberJsonReport := true,
        CucumberPlugin.cucumberStepsBasePackage := "steps",
        unmanagedResourceDirectories in Compile <+= baseDirectory(_ / "src/main/webapp")
      ) ++
      Seq(scalacOptions ++= Seq("-feature", "-target:jvm-1.7", "-language:implicitConversions")) ++
      Seq(
        organization := Organization,
        name := Name,
        version := Version,
        scalaVersion := ScalaVersion,
        resolvers += Classpaths.typesafeReleases,
        jarName in assembly := s"$Name.jar",
        mainClass in assembly := Some("Launcher"),
        mergeStrategy in assembly := {
          case "mime.types" => MergeStrategy.filterDistinctLines
          case x =>
            val oldStrategy = (mergeStrategy in assembly).value
            oldStrategy(x)
        },
        libraryDependencies ++= dependencies,
        port in container.Configuration := JettyPort
      ) ++
      Seq(
        resolvers += "Local Ivy repository" at "file://" + Path.userHome + "/.ivy2/local",
        resolvers += "EXAMPLE Maven Releases" at "https://example.maven.repo.com/maven2/releases/",
        resolvers += "EXAMPLE Maven Snapshots" at "https://example.maven.repo.com/maven2/snapshots",
        resolvers += "EXAMPLE Artifactory" at "https://example.maven.repo.com/artifactory/repo",
        resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
      )
  )
}
