package models

import play.api.libs.json.Json

case class Food(name: String, calories: Double, proteins: Double, carbohydrates: Double, lipids: Double)

object Food {
  implicit val foodFormat = Json.format[Food]
}
