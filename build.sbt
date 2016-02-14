import sbtassembly.AssemblyPlugin._

lazy val root = (project in file(".")).
  settings(
    name := "finatraSample",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.7"/*,
    mainClass in Compile := Some("com.axtstar.finatraSample.ExampleServerMain")*/
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "BUILD" => MergeStrategy.discard
  case other => MergeStrategy.defaultMergeStrategy(other)
}

lazy val versions = new {
  val finatra = "2.1.2"
  val guice = "4.0"
  val logback = "1.0.13"
  val mockito = "1.9.5"
  val scalatest = "2.2.3"
  val specs2 = "2.3.12"
}


libraryDependencies ++= Seq(
  "com.twitter.finatra" % "finatra-http_2.11" % versions.finatra,
  "com.twitter.finatra" % "finatra-slf4j_2.11" % versions.finatra,
  "com.twitter.finatra" % "finatra-jackson_2.11" % versions.finatra,
  "com.twitter.finatra" % "finatra-http_2.11" % versions.finatra % "test",
  "com.twitter.inject" % "inject-server_2.11" % versions.finatra % "test",
  "com.twitter.inject" % "inject-app_2.11" % versions.finatra % "test",
  "com.twitter.inject" % "inject-core_2.11" % versions.finatra % "test",
  "com.twitter.inject" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
  "com.twitter.finatra" % "finatra-jackson_2.11" % versions.finatra % "test",
  "com.twitter.finatra" % "finatra-http_2.11" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" % "inject-server_2.11" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" % "inject-app_2.11" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" % "inject-core_2.11" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" % "inject-modules_2.11" % versions.finatra % "test" classifier "tests",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test" classifier "tests",
  "com.twitter.finatra" % "finatra-jackson_2.11" % versions.finatra % "test" classifier "tests",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test",
"org.mockito" % "mockito-core" % versions.mockito % "test"
)

resolvers ++= Seq(
  "Twitter" at "http://maven.twttr.com",
  "Finatra Repo" at "http://twitter.github.com/finatra"
)