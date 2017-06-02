package githubparser

import scalafx.beans.property.StringProperty

class User(owner_ : String, repo_ : String) {
  val owner = new StringProperty(owner_)
  val repo = new StringProperty(repo_)
}