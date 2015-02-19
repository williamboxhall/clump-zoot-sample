import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "clump-zoot-sample"

libraryDependencies += "com.twitter" %% "finagle-http" % "6.22.0"

libraryDependencies += "net.fwbrasil" %% "zoot-finagle" % "1.0-RC2"

mainClass in Compile := Some("net.fwbrasil.zoot.sample.counter.Api")

