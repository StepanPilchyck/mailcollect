package models

import slick.dbio.DBIOAction
import slick.jdbc.MySQLProfile.api._

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.scene.text.{Text, TextFlow}

object DBTools {
  private var _connected = false
  private var _path = ""
  private var _logOutput = new TextFlow()

  def schemaCreate(path: String): Unit = {
    val db = this.setPath(path).getConnect
    try {
      db.run(this.getTablesQuery).onComplete {
        case Success(data) => this._connected = true
        case Failure(data) =>
          this._connected = true
          println(data.getMessage)
        case _ => db.close()
      }
    } catch {
      case e: Exception =>
        this._connected = false
        println(e.getMessage)
        db.close
    }
  }

  def getConnect: Database = Database.forConfig(this.getPath)

  def setPath(path : String): DBTools.type = {
    this._path = path
    this
  }

  def getPath: String = this._path

  def getTablesQuery: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(
    this.usersData.schema.create
  )

  def isConnected: Boolean = this._connected

  def usersData: TableQuery[UsersData] = TableQuery[UsersData]

  def actionLog(message: String): Unit = {
    this.getLogOut.children.add(new Text(" >> "))
    this.getLogOut.children.add(new Text(message + "\n"))
  }

  def setLogOut(textFlow: TextFlow): DBTools.type = {
    this._logOutput = textFlow
    this
  }

  def getLogOut: TextFlow = this._logOutput

}
