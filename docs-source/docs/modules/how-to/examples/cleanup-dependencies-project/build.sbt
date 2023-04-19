name := "cleanup-dependencies-project"

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

val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.0"
val AkkaManagementVersion = "1.3.0"

// tag::remove-akka-persistence-cassandra-version[]
val AkkaPersistenceCassandraVersion = "1.1.0"
// end::remove-akka-persistence-cassandra-version[]
// tag::add-akka-persistence-jdbc-version[]
val AkkaPersistenceJdbcVersion = "5.2.1"
// end::add-akka-persistence-jdbc-version[]
// tag::remove-alpakka-kafka-version[]
val AlpakkaKafkaVersion = "4.0.1"
// end::remove-alpakka-kafka-version[]
// tag::remove-akka-projection-version[]
val AkkaProjectionVersion = "1.3.1"
// end::remove-akka-projection-version[]
val AkkaDiagnosticsVersion = "2.0.0"

// tag::remove-grpc-plugin[]
enablePlugins(AkkaGrpcPlugin)
// end::remove-grpc-plugin[]

enablePlugins(JavaAppPackaging, DockerPlugin)
dockerBaseImage := "docker.io/library/adoptopenjdk:11-jre-hotspot"
dockerUsername := sys.props.get("docker.username")
dockerRepository := sys.props.get("docker.registry")
ThisBuild / dynverSeparator := "-"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster-sharding-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  // tag::remove-akka-persistence-cassandra[]
  "com.typesafe.akka" %% "akka-persistence-cassandra" % AkkaPersistenceCassandraVersion,
  // end::remove-akka-persistence-cassandra[]

  // tag::add-akka-persistence-jdbc[]
  "com.lightbend.akka" %% "akka-persistence-jdbc" % AkkaPersistenceJdbcVersion,
  // end::add-akka-persistence-jdbc[]

  // tag::remove-akka-projection[]
  "com.lightbend.akka" %% "akka-projection-eventsourced" % AkkaProjectionVersion,
  "com.lightbend.akka" %% "akka-projection-cassandra" % AkkaProjectionVersion,
  "com.lightbend.akka" %% "akka-projection-jdbc" % AkkaProjectionVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
  "com.lightbend.akka" %% "akka-projection-testkit" % AkkaProjectionVersion % Test,
  // end::remove-akka-projection[]

  /*
  // tag::replace-offset-store-for-projections-jdbc[]
  -  "com.lightbend.akka" %% "akka-projection-cassandra" % AkkaProjectionVersion,
  +  "com.lightbend.akka" %% "akka-projection-jdbc" % AkkaProjectionVersion,
  // end::replace-offset-store-for-projections-jdbc[]
   */
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  // tag::remove-grpc-optional[]
  "com.typesafe.akka" %% "akka-http2-support" % AkkaHttpVersion,
  // end::remove-grpc-optional[]
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.lightbend.akka.management" %% "akka-management" % AkkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % AkkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % AkkaManagementVersion,
  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % AkkaManagementVersion,
  "com.lightbend.akka" %% "akka-diagnostics" % AkkaDiagnosticsVersion,
  "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  // tag::remove-alpakka-kafka[]
  "com.typesafe.akka" %% "akka-stream-kafka" % AlpakkaKafkaVersion,
  // end::remove-alpakka-kafka[]

  // Logging
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  // Test dependencies
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-persistence-testkit" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.2" % Test)
