import sbt.Keys._
import sbt._
import com.typesafe.sbt.SbtStartScript

object Everything extends Build {

  lazy val root = Project(id = "root", base = file("."), aggregate = Seq(api, tracks, users))

  lazy val api = Project(
    id = "api",
    base = file("api"),
    settings = Seq(
      libraryDependencies ++= Seq(
        "com.twitter" %% "finagle-http" % "6.22.0",
        "net.fwbrasil" %% "zoot-finagle" % "1.0-RC2")
    )
  )

  lazy val tracks = Project(
    id = "tracks",
    base = file("tracks"),
    settings = Seq(
      libraryDependencies ++= Seq(
        "com.twitter" %% "finagle-http" % "6.22.0",
        "net.fwbrasil" %% "zoot-finagle" % "1.0-RC2"),
      mainClass in Compile := Some("org.example.clumpzootsample.Tracks")
    )++SbtStartScript.startScriptForClassesSettings
  ).dependsOn(api)

  lazy val users = Project(
    id = "users",
    base = file("users"),
    settings = Seq(
      libraryDependencies ++= Seq(
        "com.twitter" %% "finagle-http" % "6.22.0",
        "net.fwbrasil" %% "zoot-finagle" % "1.0-RC2"),
      mainClass in Compile := Some("org.example.clumpzootsample.Users")
    )++SbtStartScript.startScriptForClassesSettings
  ).dependsOn(api)

}