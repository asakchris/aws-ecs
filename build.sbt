
name := "aws-ecs"

version := "0.1"

scalaVersion := "2.12.6"

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)
enablePlugins(DockerPlugin)

dockerEntrypoint := Seq("bin/aws-ecs")
dockerRepository := Some("asakchris")
dockerUpdateLatest := true
maintainer := "christopher.kamaraj@gmail.com"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "software.amazon.awssdk" % "ecs" % "2.3.9",
  "org.scala-lang.modules" % "scala-java8-compat_2.11" % "0.9.0"
)
