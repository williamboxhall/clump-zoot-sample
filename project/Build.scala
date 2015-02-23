import sbt.Keys._
import sbt._
import com.typesafe.sbt.SbtStartScript

object Everything extends Build {

  lazy val root = Project(id = "root", base = file("."), aggregate = Seq(api, presentation, tracks, users))

  lazy val api = Project(
    id = "api",
    base = file("api"),
    settings = Seq(
      libraryDependencies ++= Seq(
        "net.fwbrasil" %% "zoot-finagle" % "0.14")
    )
  )

  lazy val presentation = Project(
    id = "presentation",
    base = file("presentation"),
    settings = Seq(
      mainClass in Compile := Some("org.example.clumpzootsample.PresentationService"),
      libraryDependencies ++= Seq("io.getclump" %% "clump" % "0.0.9")
    )++SbtStartScript.startScriptForClassesSettings
  ).dependsOn(api)

  lazy val tracks = Project(
    id = "tracks",
    base = file("tracks"),
    settings = Seq(
      mainClass in Compile := Some("org.example.clumpzootsample.TracksService")
    )++SbtStartScript.startScriptForClassesSettings
  ).dependsOn(api)

  lazy val users = Project(
    id = "users",
    base = file("users"),
    settings = Seq(
      mainClass in Compile := Some("org.example.clumpzootsample.UsersService")
    )++SbtStartScript.startScriptForClassesSettings
  ).dependsOn(api)

}