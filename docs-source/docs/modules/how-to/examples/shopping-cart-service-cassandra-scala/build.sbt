name := "shopping-cart-service"

organization := "com.lightbend.akka.samples"
organizationHomepage := Some(url("https://akka.io"))
licenses := Seq(("CC0", url("https://creativecommons.org/publicdomain/zero/1.0")))

scalaVersion := "2.13.5"

Compile / scalacOptions ++= Seq(
  "-target:11",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint")
Compile / javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

Test / parallelExecution := false
Test / testOptions += Tests.Argument("-oDF")
Test / logBuffered := false

run / fork := true
// pass along config selection to forked jvm
run / javaOptions ++= sys.props
  .get("config.resource")
  .fold(Seq.empty[String])(res => Seq(s"-Dconfig.resource=$res"))
Global / cancelable := false // ctrl-c

val AkkaVersion = "2.8.2"
val AkkaHttpVersion = "10.5.2"
val AkkaManagementVersion = "1.4.0"
// tag::akka-persistence-cassandra[]
val AkkaPersistenceCassandraVersion = "1.1.0"

// end::akka-persistence-cassandra[]
val AlpakkaKafkaVersion = "4.0.2"
val AkkaProjectionVersion = "1.4.0"
val AkkaDiagnosticsVersion = "2.0.0"

enablePlugins(AkkaGrpcPlugin)

enablePlugins(JavaAppPackaging, DockerPlugin)
dockerBaseImage := "docker.io/library/eclipse-temurin:17.0.3_7-jre-jammy"
dockerUsername := sys.props.get("docker.username")
dockerRepository := sys.props.get("docker.registry")
ThisBuild / dynverSeparator := "-"

// tag::akka-persistence-cassandra[]
libraryDependencies ++= Seq(
  // end::akka-persistence-cassandra[]
  // 1. Basic dependencies for a clustered application
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-cluster-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  // Akka Management powers Health Checks, Akka Cluster Bootstrapping, and Akka Diagnostics
  "com.lightbend.akka.management" %% "akka-management" % AkkaManagementVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % AkkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % AkkaManagementVersion,
  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % AkkaManagementVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  "com.lightbend.akka" %% "akka-diagnostics" % AkkaDiagnosticsVersion,
  // Common dependencies for logging and testing
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  "org.scalatest" %% "scalatest" % "3.1.2" % Test,
  // 2. Using gRPC and/or protobuf
  "com.typesafe.akka" %% "akka-http2-support" % AkkaHttpVersion,
  // 3. Using Akka Persistence
  "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,
  // tag::akka-persistence-cassandra[]
  "com.typesafe.akka" %% "akka-persistence-cassandra" % AkkaPersistenceCassandraVersion,
  // end::akka-persistence-cassandra[]
  "com.typesafe.akka" %% "akka-persistence-testkit" % AkkaVersion % Test,
  // 4. Querying or projecting data from Akka Persistence
  "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
  "com.lightbend.akka" %% "akka-projection-eventsourced" % AkkaProjectionVersion,
  // tag::akka-projection-cassandra[]
  "com.lightbend.akka" %% "akka-projection-cassandra" % AkkaProjectionVersion,
  // end::akka-projection-cassandra[]
  "com.typesafe.akka" %% "akka-stream-kafka" % AlpakkaKafkaVersion,
  "com.lightbend.akka" %% "akka-projection-testkit" % AkkaProjectionVersion % Test
  // tag::akka-persistence-cassandra[]
)
// end::akka-persistence-cassandra[]
