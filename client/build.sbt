name := "client"

version := "1.0"

scalaVersion := "2.11.7"

enablePlugins(ScalaJSPlugin)
scalaJSUseMainModuleInitializer := true

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2"
libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.4.3"
libraryDependencies += "in.nvilla" %%% "monadic-html" % "0.3.2"
