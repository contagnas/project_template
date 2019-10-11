name := "greetings"

version := "0.1"

scalaVersion := "2.13.1"

val Http4sVersion = "0.21.0-M4"
val CirceVersion = "0.12.0-M4"
val DoobieVersion = "0.8.4"
val TapirVersion = "0.11.0"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % DoobieVersion,
  "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
  "org.typelevel" %% "cats-effect" % "2.0.0",
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "com.softwaremill.tapir" %% "tapir-http4s-server" % TapirVersion,
  "com.softwaremill.tapir" %% "tapir-swagger-ui-http4s" % TapirVersion,
  "com.softwaremill.tapir" %% "tapir-json-circe" % TapirVersion,
  "com.softwaremill.tapir" %% "tapir-openapi-docs" % TapirVersion,
  "com.softwaremill.tapir" %% "tapir-openapi-circe-yaml" % TapirVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings",
)
