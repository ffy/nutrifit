package models

import play.api.data.Form
import play.api.libs.json.{Json, OFormat}
import play.api.data.Forms._
import play.api.data.format.Formats._

case class User(id: Long, email: String, password: String, weight: Double, height: Double, gender: String)

case class UserFormData(email: String, password: String, weight: Double, height: Double, gender: String)

object UserForm {
  val form: Form[UserFormData] = Form {
    mapping(
      "email" -> email,
      "password" -> text,
      "weight" -> of(doubleFormat),
      "height" -> of(doubleFormat),
      "gender" -> text
    )(UserFormData.apply)(UserFormData.unapply)
  }
}

object User {
  implicit val userFormat: OFormat[User] = Json.format[User]
}

object Users {
  var users: Seq[User] = Seq()

  def add(user: User): String = {
    users = users :+ user.copy(id = users.length)
    "User successfully added."
  }

  def delete(id: Long): Option[Int] = {
    val originalSize = users.length
    users = users.filterNot(_.id == id)
    Some(originalSize - users.length)
  }

  def get(id: Long): Option[User] = users.find(_.id == id)

  def listAll: Seq[User] = users
}
