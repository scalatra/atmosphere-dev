import sbt._
import Keys._

object AtmoTestBuild extends Build {

  val scalatraLocation = file("/Users/ivan/projects/scalatra/scalatra")
  def scalatra(name: String = null) =
    ProjectRef(scalatraLocation, if (name == null || name.trim.isEmpty) "scalatra" else "scalatra-" + name)
  val root = (Project(id = "atmotest", base = file("."))
                dependsOn(scalatra(""))
                dependsOn(scalatra("scalate"))
                dependsOn(scalatra("json"))
                dependsOn(scalatra("specs2") % "test->compile"))
}
