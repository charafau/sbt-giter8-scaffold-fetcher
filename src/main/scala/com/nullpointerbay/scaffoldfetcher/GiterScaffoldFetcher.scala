package com.nullpointerbay.scaffoldfetcher

import java.io.{File => JFile}

import better.files._
import org.eclipse.jgit.api.Git
import sbt.Keys._
import sbt._

import scala.util.{Failure, Success, Try}


object GiterScaffoldFetcher extends AutoPlugin {

  lazy val COMMAND = "g8ScaffoldFetch"

  override def projectSettings = Seq(commands += helloCommand)

  lazy val helloCommand = Command.args(COMMAND, "<url>") { (state, args) =>

    if (args.size < 1) {
      println(s"Need to specify repository $COMMAND <user/repository>")
    } else {

      val url = s"https://github.com/${args(0)}"

      val tempDownloadPath = File.newTemporaryDirectory()

      println(s"Cloning repository from $url")

      val clone = Try(Git.cloneRepository()
        .setURI(url)
        .setDirectory(new JFile(tempDownloadPath.pathAsString))
        .call())

      val outputPath = s"${Project.extract(state).currentRef.build}.g8".substring(5)
      val outputDirectory = File(outputPath).createIfNotExists(true)

      clone match {
        case Success(value) =>
          val scaffoldDir = new JFile(s"${tempDownloadPath.pathAsString}/src/main/scaffolds")
          println(scaffoldDir.getAbsolutePath)
          if (scaffoldDir.exists()) {
            val betterFile = File(scaffoldDir.getAbsolutePath)
            betterFile.copyTo(outputDirectory, true)
            println("Copied scaffolds to project")
            tempDownloadPath.delete()

          } else {
            println("Downloaded repository doesn't contain any scaffolds")
          }
        case Failure(error) => println(s"Error while cloning repository: ${error.getMessage}")
      }
    }

    state

  }

}
