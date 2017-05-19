package models

import play.api.data.Form
import play.api.libs.json.{Json, OFormat}
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Food(id: Long, name: String, calories: Double, proteins: Double, carbohydrates: Double, lipids: Double)

case class FoodFormData(name: String, calories: Double, proteins: Double, carbohydrates: Double, lipids: Double)

object FoodForm {
  val form: Form[FoodFormData] = Form(
    mapping(
      "name" -> text,
      "calories" -> of(doubleFormat),
      "proteins" -> of(doubleFormat),
      "carbohydrates" -> of(doubleFormat),
      "lipids" -> of(doubleFormat)
    )(FoodFormData.apply)(FoodFormData.unapply)
  )
}

object Food {
  implicit val foodFormat: OFormat[Food] = Json.format[Food]
}

object Foods {
  var foods: Seq[Food] = Seq()

  def add(food: Food): String = {
    foods = foods :+ food.copy(id = foods.length)
    "Food successfully added."
  }

  def delete(id: Long): Option[Int] = {
    val originalSize = foods.length
    foods = foods.filterNot(_.id == id)
    Some(originalSize - foods.length)
  }

  def get(id: Long): Option[Food] = foods.find(_.id == id)

  def listAll: Seq[Food] = foods
}
