package nutrifit.dto

import derive.key
import upickle.default.read

import scala.concurrent.Future

/**
  * Created by matthieu.villard on 12.06.2017.
  */
case class User(@key("id") id: Int,
                @key("email") email: String,
                @key("gender") gender: String,
                @key("weight") weight: Int,
                @key("height") height: Int)

object User {
  def parse(json: String): User = {
    read[User](json)
  }
}

