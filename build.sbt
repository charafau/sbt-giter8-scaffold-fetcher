import Dependencies._


scalacOptions ++= Vector(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.6",
  "-encoding", "UTF-8"
)

organization := "com.nullpointerbay"
sbtPlugin := true


lazy val root = (project in file(".")).
  settings(
    name := "sbt-giter8-scaffold-fetcher",
    description := "Plugin for downloading g8 scaffold templates from github",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := false,
    libraryDependencies ++= Seq(
      "org.eclipse.jgit" % "org.eclipse.jgit" % "4.6.1.201703071140-r",
      "com.github.pathikrit" %% "better-files" % "2.17.0",
      scalaTest % Test)
  )
