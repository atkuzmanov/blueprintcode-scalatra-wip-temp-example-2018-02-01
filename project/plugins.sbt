resolvers += "Templemore Repository" at "http://templemore.co.uk/repo"

resolvers += "Local Ivy repository" at "file:///" + Path.userHome + "/.ivy2/local"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "EXAMPLE Artifactory" at "https://example.maven.repo.com/maven2/releases/"

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.4")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")

addSbtPlugin("org.scalatra.sbt" % "scalatra-sbt" % "0.3.5")

//addSbtPlugin("example.some" % "sbt-cucumber-plugin" % "1.0.1")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.9")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")
