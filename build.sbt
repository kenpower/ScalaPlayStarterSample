name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.194"

libraryDependencies += "org.mockito" % "mockito-core" % "2.8.47" % Test
libraryDependencies += "com.github.tomakehurst" % "wiremock" % "2.7.1" % Test
libraryDependencies ++= Seq(
  // these overrides are needed until wiremock updates jetty to v9.4
  ("org.eclipse.jetty" % "jetty-http" % "9.2.22.v20170606" % Test).force(),
  ("org.eclipse.jetty" % "jetty-io" % "9.2.22.v20170606" % Test).force(),
  ("org.eclipse.jetty" % "jetty-util" % "9.2.22.v20170606" % Test).force())

javaOptions in Test += "-Dconfig.resource=test.conf"