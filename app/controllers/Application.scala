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
    if (request.method == "GET") {
      Ok(views.html.addUser("Add new user."))
    }
    else {
      val user = userForm.bindFromRequest.get
      // TODO: Save user to database.
      Ok(views.html.addUser("User added."))
    }
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
    if (request.method == "GET") {
      Ok(views.html.addFood("Add new food."))
    }
    else {
      val food = foodForm.bindFromRequest.get
      // TODO: Save food to database.
      Ok(views.html.addFood("Food added."))
    }
  }

}