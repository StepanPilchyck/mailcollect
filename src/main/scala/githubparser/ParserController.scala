package githubparser

import javafx.fxml.FXML

import models.UsersData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.util.{Failure, Success}
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableColumn, TableView, TextField}
import scalafx.scene.layout._
import scalafxml.core.macros.sfxml
import scalafx.collections.ObservableBuffer
import scala.concurrent.ExecutionContext.Implicits.global

@sfxml
class ParserController(
    private var usersTable: TableView[User],
    private var ownerField: TextField,
    private var repoField: TextField,
    private val resultPanel: AnchorPane
  ) {

  val usersData: ObservableBuffer[User] = ObservableBuffer[User]()
  val logger: Logger = LoggerFactory.getLogger("ParserController")
  usersTable.setItems(usersData)

  usersTable.columns ++= List(
    new TableColumn[User, String]("Owner") {
      cellValueFactory = {_.value.owner}
    },
    new TableColumn[User, String]("Repo") {
      cellValueFactory = {_.value.repo}
    }
  )

  @FXML
  def handleInsertUserDataAction(): Unit = {
    if (!ownerField.getText.isEmpty && !repoField.getText.isEmpty) {
      usersData += new User(
        ownerField.getText,
        repoField.getText
      )
      ownerField.clear()
      repoField.clear()
    }
  }

  @FXML
  def handlePareAllAction(): Unit = {
    val url = "https://api.github.com/repos/" + usersTable.getItems.get(0).owner + "/" + usersTable.getItems.get(0).repo + "/stargazers"
    println(
      url
    )
  }

  @FXML
  def handlePareSelectedAction(): Unit = {
    if (usersTable.getSelectionModel.getSelectedItem != null) {
      val item = usersTable.getSelectionModel.getSelectedItem

      //  val url = "https://api.github.com/repos/rundeck/rundeck/stargazers"
      //  val url = "https://api.github.com/users/CharlieSu"
      println(getUrl(item))

      val result = scala.io.Source.fromURL(getUrl(item)).mkString
      println(result)

    }
  }

  private def getUrl(item : User): String = {
    "https://api.github.com/repos/" + item.owner.value + "/" + item.repo.value + "/stargazers"
  }

}
