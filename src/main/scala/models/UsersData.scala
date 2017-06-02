package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.MySQLProfile.api._

class UsersData(tag: Tag) extends Table[UserData](tag, "users_data") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def owner: Rep[String] = column[String]("owner")
  def repo: Rep[String] = column[String]("repo")
  override def * : ProvenShape[UserData] = (id.?, owner, repo) <> (UserData.tupled, UserData.unapply)
}
