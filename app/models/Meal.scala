package models

import play.api.data.Form
import play.api.libs.json.{Json, OFormat}
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Meal(id: Long, amount: Double, userId: Long, foodId: Long)

case class MealFormData(amount: Double, userId: Long, foodId: Long)

object MealForm {
  val form: Form[MealFormData] = Form(
    mapping(
      "amount" -> of(doubleFormat),
      "userId" -> of(longFormat),
      "foodId" -> of(longFormat)
    )(MealFormData.apply)(MealFormData.unapply)
  )
}

object Meal {
  implicit val mealFormat: OFormat[Meal] = Json.format[Meal]
}

object Meals {
  var meals: Seq[Meal] = Seq()

  def add(meal: Meal): String = {
    meals = meals :+ meal.copy(id = meals.length)
    "Meal successfully added."
  }

  def delete(id: Long): Option[Int] = {
    val originalSize = meals.length
    meals = meals.filterNot(_.id == id)
    Some(originalSize - meals.length)
  }

  def get(id: Long): Option[Meal] = meals.find(_.id == id)

  def listAll: Seq[Meal] = meals
}
