ThisBuild / scalaVersion := "2.12.8"
ThisBuild / crossScalaVersions := Seq("2.12.8")
ThisBuild / scalacOptions ++= compilerOptions ++ warnCompilerOptions
ThisBuild / libraryDependencies ++= Seq(
  "com.lihaoyi" %% "fastparse" % "2.1.2",
  "org.scalatest" %% "scalatest" % "3.0.7" % Test
).map(_.withSources)

lazy val root = project
  .in(file("."))
  .settings(name := "uri-parser")
  .settings(
    Compile / console / scalacOptions --= warnCompilerOptions,
    Compile / console / scalacOptions += "-Yrepl-class-based"
  )
  .aggregate(core, benchmark)
  .dependsOn(core, benchmark)

lazy val core = project
  .in(file("core"))
  .settings(
    moduleName := "uri-parser-core",
    description := "uri-parser core"
  )

lazy val benchmark = project
  .in(file("benchmark"))
  .settings(
    moduleName := "uri-parser-benchmark",
    description := "uri-parser benchmark"
  )
  .dependsOn(core)
  .enablePlugins(JmhPlugin)

lazy val compilerOptions = Seq(
  "-deprecation",
  "-encoding",
  "utf-8",
  "-explaintypes",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Xcheckinit",
)

lazy val warnCompilerOptions = Seq(
  "-Xlint",
  "-Xfatal-warnings",
  "-Ywarn-unused:_",
  "-Ywarn-value-discard",
  "-Ypartial-unification",
  "-Yno-adapted-args",
  "-Xfuture",
  "-Ywarn-inaccessible",
  "-Ywarn-extra-implicit",
  "-Ywarn-dead-code",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
)

