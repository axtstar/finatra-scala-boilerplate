name := "finatra_example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.twitter.finatra" % "finatra-http_2.11" % "2.1.2",
  "com.github.scala-incubator.io" % "scala-io-file_2.11" % "0.4.3-1"
)

resolvers +=
  "Twitter" at "http://maven.twttr.com"
