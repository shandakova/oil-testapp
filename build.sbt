

name := """oil-testapp"""
organization := "com.shandakova"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

resolvers ++= Resolver.sonatypeOssRepos("releases")

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "com.iterable" %% "swagger-play" % "2.0.1",
  "org.webjars" % "swagger-ui" % "3.25.3",
  "org.webjars" %% "webjars-play" % "2.8.0",
  "org.postgresql" % "postgresql" % "42.5.1",
  evolutions,
  jdbc,
  "org.playframework.anorm" %% "anorm" % "2.7.0",
  "com.github.tototoshi" %% "scala-csv" % "1.3.10"
)