package controllers

import com.google.inject.{Provider, Singleton}
import play.api.mvc._
import play.api.libs.json._
import services.UserService
import play.api.libs.concurrent.Execution.Implicits._
import javax.inject.Inject

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import models.{User, Users}
import play.api.Application
import play.api.i18n._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by matthieu.villard on 22.05.2017.
  */
@Singleton
class UserController @Inject() (users: UserService, val messagesApi: MessagesApi, ec: ExecutionContext)
  extends Controller with AuthActionBuilder {

  def user = AuthAction.async{implicit request =>
    users.get(request.session.get("email").get).map(u => Ok(Json.toJson(u))).recover {
      case _: NoSuchElementException => NotFound(Messages("USER_NOT_FOUND"))
    }
  }

  def register = Action.async{ implicit request =>
    Users.form.bindFromRequest.fold(
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
      user => {
        users.create(user).map(
          u => Created(
            Json.obj(
              "status" -> "success",
              "user" -> Json.toJson(u)
            )
          )
        ).recover {
          case e: MySQLIntegrityConstraintViolationException => Conflict(
            Json.obj(
              "status" -> "error",
              "message" -> Messages("DUPLICATE_EMAIL")
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

  def login = Action.async(BodyParsers.parse.json) { implicit request =>
    try{
      val email = (request.body \ "email").as[String]
      val password = (request.body \ "password").as[String]
      users.check(email, password).map { case u =>
        Ok.withSession("email" -> email)
      }.recover {
        case _: NoSuchElementException => Unauthorized(
          Json.obj(
            "status" -> "error",
            "message" -> Messages("BAD_CREDENTIALS")
          )
        )
      }
    } catch {
      case e: Exception => Future.successful(
        Unauthorized(
          Json.obj(
            "status" -> "error",
            "message" -> Messages("BAD_CREDENTIALS")
          )
        )
      )
    }

  }
}
