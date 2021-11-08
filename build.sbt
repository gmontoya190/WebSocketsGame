name := "OnAir"

version := "0.1"

scalaVersion := "2.13.5"

val Versions = new {
  val Circe = "0.14.1"
  val AkkaHttp = "10.2.4"
  val Akka = "2.6.14"
  val Scala = "2.13.5"
  val Zio = "1.0.12"
  val Slf4Zio = "1.0.0"
  val ZioInteropLog = "1.0.0"
  val AkkaHttpCirce = "1.31.0"
  val ScalaTest = "3.2.7"
  val ScalaLogging = "3.9.3"
  val http4sVersion = "0.23.6"
}
lazy val IntegrationTests = config("test-int") extend Test

val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-http-caching" % Versions.AkkaHttp,
  "com.typesafe.akka" %% "akka-http" % Versions.AkkaHttp,
  "com.typesafe.akka" %% "akka-http-testkit" % Versions.AkkaHttp % Test,
  "com.typesafe.akka" %% "akka-stream" % Versions.Akka,
  "com.typesafe.akka" %% "akka-slf4j" % Versions.Akka,
  "com.typesafe.akka" %% "akka-testkit" % Versions.Akka % Test,
  // circe support for akka http
  "de.heikoseeberger" %% "akka-http-circe" % Versions.AkkaHttpCirce
)

lazy val `domain` = project
  .settings(
    name := "domain",
    organization := "lucklygames.domain",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= akkaDependencies ++ Seq(
      //ZIO logging
      "dev.zio" %% "zio" % Versions.Zio,
      "dev.zio" %% "zio-test" % Versions.Zio % Test,
      "com.github.mlangc" %% "slf4zio" % Versions.Slf4Zio,
      "com.github.mlangc" %% "zio-interop-log4j2" % Versions.ZioInteropLog,
      "org.scalatest" %% "scalatest" % Versions.ScalaTest,
      "io.circe" %% "circe-core" % Versions.Circe,
      "io.circe" %% "circe-generic" % Versions.Circe,
      "io.circe" %% "circe-generic-extras" % Versions.Circe,
      "io.circe" %% "circe-parser" % Versions.Circe,
    ),
//    inConfig(IntegrationTests)(
//      Defaults.testTasks ++
//        Seq(forkOptions := Defaults.forkOptionsTask.value)
//    )
  )

lazy val `game-api` = project
  .dependsOn(`domain` % "compile->compile;test->test")
  .settings(
    name := "game-api",
    organization := "lucklygames.game.api",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= akkaDependencies ++ Seq(
      //ZIO logging
      "dev.zio" %% "zio" % Versions.Zio,
      "dev.zio" %% "zio-test" % Versions.Zio % Test,
      "dev.zio"  %% "zio-interop-cats"  % "2.0.0.0-RC2",
      "com.github.mlangc" %% "slf4zio" % Versions.Slf4Zio,
      "com.github.mlangc" %% "zio-interop-log4j2" % Versions.ZioInteropLog,
      "org.scalatest" %% "scalatest" % Versions.ScalaTest,
      "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging,
      "io.circe" %% "circe-core" % Versions.Circe,
      "io.circe" %% "circe-generic" % Versions.Circe,
      "io.circe" %% "circe-generic-extras" % Versions.Circe,
      "io.circe" %% "circe-parser" % Versions.Circe,
      "de.heikoseeberger" %% "akka-http-json4s" % "1.38.2",
      "org.json4s" %% "json4s-jackson" % "4.0.3",
      "com.typesafe.akka" %% "akka-http-caching" % "10.2.6",

      "org.http4s" %% "http4s-dsl" % Versions.http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % Versions.http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Versions.http4sVersion,
//      "com.softwaremill.sttp.client3" %% "async-http-client-backend-monix" % "3.3.16",
//      "com.softwaremill.sttp.client3" %% "async-http-client-backend-fs2" % "3.3.16"
//       "com.softwaremill.sttp.client3" %% "akka-http-backend" % "3.3.16"
    ),
//    inConfig(IntegrationTests)(
//      Defaults.testTasks ++
//        Seq(forkOptions := Defaults.forkOptionsTask.value)
//    )
  )