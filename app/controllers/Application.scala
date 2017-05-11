package controllers

import models.{Food, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.data.format.Formats._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  val userForm: Form[User] = Form {
    mapping(
      "email" -> email,
      "password" -> text,
      "weight" -> of(doubleFormat),
      "height" -> of(doubleFormat),
      "gender" -> text
    )(User.apply)(User.unapply)
  }

  def addUser = Action { implicit request =>
    val user = userForm.bindFromRequest.get
    // TODO: Save user to database.
    Redirect(routes.Application.index())
  }

  val foodForm: Form[Food] = Form {
    mapping(
      "name" -> text,
      "calories" -> of(doubleFormat),
      "proteins" -> of(doubleFormat),
      "carbohydrates" -> of(doubleFormat),
      "lipids" -> of(doubleFormat)
    )(Food.apply)(Food.unapply)
  }

  def addFood = Action { implicit request =>
    val food = foodForm.bindFromRequest.get
    // TODO: Save food to database.
    Redirect(routes.Application.index())
  }

}