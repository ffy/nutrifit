package nutrifit.dto

import derive.key
import upickle.default.read

/**
  * Created by matthieu.villard on 12.06.2017.
  */
case class Error(@key("status") status: String,
                @key("message") message: String)

object Error {
  def parse(json: String): Error = read[Error](json)
}

