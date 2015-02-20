import sbt.Keys._
import sbt._

object EverythingBuild extends Build {

  lazy val root = Project(id = "root", base = file("."), aggregate = Seq(api, tracks))

  lazy val api = Project(
    id = "api",
    base = file("api"),
    settings = Seq(libraryDependencies ++= Seq("com.twitter" %% "finagle-http" % "6.22.0", "net.fwbrasil" %% "zoot-finagle" % "1.0-RC2"))
  )

  lazy val tracks = Project(
    id = "tracks",
    base = file("tracks"),
    settings = Seq(libraryDependencies ++= Seq("com.twitter" %% "finagle-http" % "6.22.0", "net.fwbrasil" %% "zoot-finagle" % "1.0-RC2"))
  ).dependsOn(api)

}