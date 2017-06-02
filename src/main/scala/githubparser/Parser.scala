package githubparser

import java.io.IOException
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import models.DBTools

object Parser extends JFXApp {
  val resource = getClass.getResource("Parser.fxml")

  if (resource == null) {
    throw new IOException("Cannot load resource: Parser.fxml")
  }

  val root = FXMLView(resource, NoDependencyResolver)
  stage = new PrimaryStage() {
    title = "Email collect from GitHub.com"
    scene = new Scene(root)
  }

  DBTools.schemaCreate("mysql")

}