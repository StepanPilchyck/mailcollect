package githubparser

import javafx.fxml.FXML

import models.{DBTools, UserData}
import slick.jdbc.MySQLProfile.api._

import scala.util.{Failure, Success}
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableColumn, TableView, TextField}
import scalafx.scene.text.TextFlow
import scalafx.scene.layout._
import scalafxml.core.macros.sfxml
import scalafx.collections.ObservableBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

@sfxml
class ParserController(
    private var usersTable: TableView[UserData],
    private var ownerField: TextField,
    private var repoField: TextField,
    private val resultPanel: AnchorPane,
    private val actionsLog: TextFlow
  ) {

  DBTools.setLogOut(actionsLog)
  val usersData: ObservableBuffer[UserData] = ObservableBuffer[UserData]()
  loadUsersTable(usersTable)

  usersTable.columns ++= List(
    new TableColumn[UserData, String]("Owner") {
      cellValueFactory = {_.value.owner_}
    },
    new TableColumn[UserData, String]("Repo") {
      cellValueFactory = {_.value.repo_}
    }
  )

  @FXML
  def handleInsertUserDataAction(): Unit = {
    if (!ownerField.getText.isEmpty && !repoField.getText.isEmpty) {
      this.usersData += UserData(
        None,
        ownerField.getText,
        repoField.getText
      )
      ownerField.clear()
      repoField.clear()
    }
  }

  @FXML
  def handlePareAllAction(): Unit = {
    val url = List(
      "https://api.github.com/repos/",
      usersTable.getItems.get(0).owner + "/",
      usersTable.getItems.get(0).repo + "/stargazers"
    ).mkString
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

  private def getUrl(item : UserData): String = {
    List(
      "https://api.github.com/repos/",
      item.owner.value, "/",
      item.repo.value, "/stargazers"
    ).mkString
  }

  def loadUsersTable(table: TableView[UserData]): Unit = {
    val fData: Future[Seq[(String, String)]] = DBTools.getConnect.run(
      (
        for {
          ud <- DBTools.usersData
        } yield (ud.owner, ud.repo)
      ).result
    )

    fData.onComplete {
      case Success(data) =>
        for (item <- data) {
          this.usersData += UserData(
            None,
            item._1,
            item._2
          )
        }
        table.setItems(this.usersData)
    }
  }

}
