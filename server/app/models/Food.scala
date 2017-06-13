package models

import play.api.data.Forms._
import play.api.data._
import play.api.libs.json._
import slick.lifted.{TableQuery, Tag}
import play.api.data.format.Formats._
import utils.BCrypt
import utils.SlickAPI._
import validation.Constraints._

case class Food(id: Option[Int], name: String, calories: Double, proteins: Double, carbohydrates: Double, lipids: Double)

class Foods(tag: Tag) extends Table[Food](tag, "foods") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def calories = column[Double]("calories")
  def proteins = column[Double]("proteins")
  def carbohydrates = column[Double]("carbohydrates")
  def lipids = column[Double]("lipids")

  override def * = (id.?, name, calories, proteins, carbohydrates, lipids) <> ((Food.apply _).tupled, Food.unapply _)
}

object Food {
  implicit val foodFormat: OFormat[Food] = Json.format[Food]
}

object Foods extends TableQuery(new Foods(_)) {

  def list = Foods.sortBy(m => m.name)

  def insert(f: Food) = Foods returning Foods.map(_.id) into ((e, i) => e.copy(id = Some(i))) += f

  def findById(id: Int) = Foods.filter(e => e.id === id)

  val form: Form[Food] = Form(
    mapping(
      "id" -> ignored[Option[Int]](None),
      "name" -> text,
      "calories" -> of(doubleFormat),
      "proteins" -> of(doubleFormat),
      "carbohydrates" -> of(doubleFormat),
      "lipids" -> of(doubleFormat)
    )(Food.apply)(Food.unapply)
  )
}
