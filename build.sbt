import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "clump-zoot-sample"

libraryDependencies += "com.twitter" %% "finagle-http" % "6.22.0"

mainClass in Compile := Some("org.example.clumpzootsample.Api")

