package models

import play.api.libs.json.Json

case class User(email: String, password: String, weight: Double, height: Double, gender: Char)

object User {
  implicit val userFormat = Json.format[User]
}
