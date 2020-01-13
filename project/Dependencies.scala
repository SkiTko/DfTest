import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.6.1"
  lazy val casbah = "org.mongodb" %% "casbah" % "3.1.1"  pomOnly()
}
