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

libraryDependencies ++= Seq(
  ("com.twitter.finatra" % "finatra-http_2.11" % "2.1.2"),
  ("com.github.scala-incubator.io" % "scala-io-file_2.11" % "0.4.3-1")
)

resolvers +=
  "Twitter" at "http://maven.twttr.com"
