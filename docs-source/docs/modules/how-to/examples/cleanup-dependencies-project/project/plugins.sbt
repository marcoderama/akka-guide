// tag::remove-grpc-plugin[]
addSbtPlugin("com.lightbend.akka.grpc" % "sbt-akka-grpc" % "2.3.2")
// end::remove-grpc-plugin[]

addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.13")
addSbtPlugin("com.dwijnand" % "sbt-dynver" % "4.1.1")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2")
