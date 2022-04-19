lazy val akkaHttpVersion = "10.2.4"
lazy val akkaVersion    = "2.6.14"
lazy val openTelemetryVersion = "1.10.0"
lazy val mesmerVersion = "0.6.0"
// Run in a separate JVM, to make sure sbt waits until all threads have
// finished before returning.
// If you want to keep the application running while executing other
// sbt tasks, consider https://github.com/spray/sbt-revolver/
fork := true

lazy val root = (project in file("."))
  .enablePlugins(JavaAgent)
  .settings(
    inThisBuild(List(
      organization    := "dino.papa",
      scalaVersion    := "2.13.4"
    )),
    name := "sample-akka-http-with-opentelemetry",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",

      "io.opentelemetry" % "opentelemetry-exporter-otlp" % openTelemetryVersion,

      "io.scalac" %% "mesmer-akka-extension" % mesmerVersion,
      "io.opentelemetry" % "opentelemetry-exporter-otlp-metrics" % s"${openTelemetryVersion}-alpha",
      "io.opentelemetry" % "opentelemetry-sdk" % openTelemetryVersion,
      "io.grpc" % "grpc-netty-shaded" % "1.43.2",


      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test
    ),
    javaAgents += JavaAgent("io.opentelemetry.javaagent" % "opentelemetry-javaagent" % openTelemetryVersion % "runtime"),
    run / javaOptions ++= Seq(
      "-Dotel.service.name=sample-akka-http",
      "-Dotel.metrics.exporter=otlp",
      "-Dotel.traces.sampler=always_on")
)