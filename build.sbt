import sbtassembly.AssemblyPlugin._

lazy val root = (project in file(".")).
  settings(
    name := "finatraSample",
    version := "0.0.1",
    scalaVersion := "2.11.7"
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "BUILD" => MergeStrategy.discard
  case other => MergeStrategy.defaultMergeStrategy(other)
}

lazy val versions = new {
  val finatra = "2.4.0"
}


libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra
)

resolvers ++= Seq(
  "Twitter" at "http://maven.twttr.com",
  "Finatra Repo" at "http://twitter.github.com/finatra"
)