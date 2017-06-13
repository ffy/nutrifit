package nutrifit.dto

import derive.key
import upickle.default.read

/**
  * Created by matthieu.villard on 12.06.2017.
  */
case class Food(@key("id") id: Int,
                @key("name") name: String,
                @key("calories") calories: Double,
                @key("proteins") proteins: Double,
                @key("carbohydrates") carbohydrates: Double,
                @key("lipids") lipids: Double)

object Food {
  def parse(json: String): Food = {
    read[Food](json)
  }

  def parseAll(json: String): List[Food] = {
    read[List[Food]](json).map(f => Food(f.id, f.name, f.calories, f.proteins, f.carbohydrates, f.lipids))
  }
}

