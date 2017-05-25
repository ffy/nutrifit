package controllers

import java.lang.ProcessBuilder.Redirect

import com.google.inject.Singleton
import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._
import services.UserService
import play.api.libs.concurrent.Execution.Implicits._
import javax.inject.Inject

import models.{User, Users}
import play.api.i18n._

/**
  * Created by matthieu.villard on 22.05.2017.
  */
@Singleton
class UserController @Inject() (users: UserService, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def list = Action.async{implicit request =>
    users.list.map(u => Ok(Json.toJson(u)))
  }

  def user(id: Int) = Action.async{implicit request =>
    users.get(id).map(u => Ok(Json.toJson(u))).recover {

      case _: NoSuchElementException => NotFound(Messages("USER_NOT_FOUND"))
    }
  }

  def create = Action{ implicit request =>
    Ok(views.html.user.form(Users.form))
  }

  def postForm = Action { implicit request =>
      Users.form.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.user.form(formWithErrors))
        },
        user => {users.create(user)
          Redirect(routes.UserController.create).flashing("success" -> "User Saved!")
        }
      )
  }
}
