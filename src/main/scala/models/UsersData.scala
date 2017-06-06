package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.MySQLProfile.api._

import scalafx.beans.property.StringProperty

case class UserData(id: Option[Int], owner: String, repo: String) {
  val owner_ = new StringProperty(this, "owner_", owner)
  val repo_ = new StringProperty(this, "repo_", repo)
}

class UsersData(tag: Tag) extends Table[UserData](tag, "users_data") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def owner: Rep[String] = column[String]("owner")
  def repo: Rep[String] = column[String]("repo")
  def * : ProvenShape[UserData] = (id.?, owner, repo) <> (UserData.tupled, UserData.unapply)
}
