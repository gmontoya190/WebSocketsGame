name := "OnAir"

version := "0.1"

scalaVersion := "2.13.5"

val Versions = new {
  val Circe = "0.14.1"
  val Scala = "2.13.5"
  val ScalaTest = "3.2.7"
  val ScalaLogging = "3.9.3"
  val http4sVersion = "0.23.6"
  val pureConfig = "0.17.0"
  val scalaLogging = "3.9.4"
  val mockitoScalaTest = "1.16.46"
}
lazy val `domain` = project
  .settings(
    name := "domain",
    organization := "lucklygames.domain",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % Versions.Circe,
      "io.circe" %% "circe-generic" % Versions.Circe,
      "io.circe" %% "circe-generic-extras" % Versions.Circe,
      "io.circe" %% "circe-parser" % Versions.Circe,
    ),
  )

lazy val `game-api` = project
  .dependsOn(`domain` % "compile->compile;test->test")
  .settings(
    name := "game-api",
    organization := "lucklygames.game.api",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % Versions.ScalaTest % Test,
      "io.circe" %% "circe-core" % Versions.Circe,
      "io.circe" %% "circe-generic" % Versions.Circe,
      "io.circe" %% "circe-generic-extras" % Versions.Circe,
      "io.circe" %% "circe-parser" % Versions.Circe,
      "org.http4s" %% "http4s-dsl" % Versions.http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % Versions.http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Versions.http4sVersion,
      "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig,
      "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging,
      "org.mockito" % "mockito-scala-scalatest_2.12" % Versions.mockitoScalaTest % Test,

    )
  )