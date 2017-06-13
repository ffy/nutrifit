package controllers

import javax.inject.Inject

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import models._
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc._
import services.FoodService
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.{ExecutionContext, Future}

class FoodController @Inject() (foods: FoodService, val messagesApi: MessagesApi, ec: ExecutionContext)
  extends Controller with AuthActionBuilder {

  def list = AuthAction.async{ implicit request =>
    foods.list.map(u => Ok(Json.toJson(u)))
  }

  def delete(id: Int) = AuthAction.async{ implicit request =>
    foods.delete(id).map(u => Ok).recover{
      case e: Exception => InternalServerError(
        Json.obj(
          "status" -> "error",
          "message" -> e.getMessage
        )
      )
    }
  }

  def add = AuthAction.async(BodyParsers.parse.json) { implicit request =>
    Foods.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          BadRequest(
            Json.obj(
              "status" -> "error",
              "message" -> formWithErrors.errors.map(e => Messages(e.key + "." + e.message, e.args: _*)).mkString("\n")
            )
          )
        )
      },
      food => {
        foods.create(food).map(
          f => Created(
            Json.obj(
              "status" -> "success",
              "user" -> Json.toJson(f)
            )
          )
        ).recover {
          case e: MySQLIntegrityConstraintViolationException => Conflict(
            Json.obj(
              "status" -> "error",
              "message" -> Messages("DUPLICATE_NAME")
            )
          )
          case e: Exception => InternalServerError(
            Json.obj(
              "status" -> "error",
              "message" -> e.getMessage
            )
          )
        }
      }
    )

  }

}