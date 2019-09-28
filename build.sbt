organization := "com.muhuk"
name := "akka-http-boopickle"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.10",
  "com.typesafe.akka" %% "akka-stream" % "2.5.25",
  "io.suzaku" %% "boopickle" % "1.3.1"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.25" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.25" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.10" % Test,
  "org.scalactic" %% "scalactic" % "3.0.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.0" % Test
)

licenses := Seq("Apache License v2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("https://github.com/muhuk/akka-http-boopickle"))

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

pomExtra := (
  <scm>
    <url>git@github.com:muhuk/akka-http-boopickle.git</url>
    <connection>scm:git:git@github.com:muhuk/akka-http-boopickle.git</connection>
  </scm>
  <developers>
    <developer>
      <id>muhuk</id>
      <name>Atamert Ölçgen</name>
      <url>http://www.muhuk.com</url>
    </developer>
  </developers>)
