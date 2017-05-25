package models

import play.api.data.Forms._
import play.api.data._
import play.api.libs.json._
import slick.lifted.{TableQuery, Tag}
import play.api.data.format.Formats._
import utils.SlickAPI._
import validation.Constraints._

case class User(id: Option[Int], email: String, password: String, weight: Double, height: Double, gender: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def password = column[String]("password")
  def weight = column[Double]("weight")
  def height = column[Double]("height")
  def gender = column[String]("gender")

  override def * = (id.?, email, password, weight, height, gender) <> ((User.apply _).tupled, User.unapply _)
}

object User {
  implicit val UserFormat: Format[User] = new Format[User] {
    def writes(u: User): JsValue = Json.obj(
      "id" -> u.id,
      "email" -> u.email,
      "weight" -> u.weight,
      "height" -> u.height,
      "gender" -> u.gender
    )
    def reads(json: JsValue): JsResult[User] = for {
      id <- (json \ "id").validate[Int]
      email <- (json \ "email").validate[String]
      weight <- (json \ "weight").validate[Double]
      height <- (json \ "height").validate[Double]
      gender <- (json \ "gender").validate[String]
    } yield User(Some(id), email, null, weight, height, gender)
  }
}

object Users extends TableQuery(new Users(_)){

  def list = Users.sortBy(m => m.email.asc)

  def insert(u: User) = Users returning Users.map(_.id) into ((e, i) => e.copy(id = Some(i))) += u

  def findById(id: Rep[Int]): Query[Users, User, Seq] = Users.filter(_.id === id)

  val form: Form[User] = Form {
    mapping(
      "id" -> ignored[Option[Int]](None),
      "email" -> email.verifying(nonEmpty),
      "password" -> text.verifying(minLength(6)),
      "weight" -> of(doubleFormat),
      "height" -> of(doubleFormat),
      "gender" -> text
    )(User.apply)(User.unapply)
  }
}

case class UserFormData(email: String, password: String, weight: Double, height: Double, gender: String)

