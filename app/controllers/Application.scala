package controllers

import models._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def addFood() = Action { implicit request =>
    if (request.method == "GET") {
      Ok(views.html.addFood("Add new food."))
    }
    else {
      val data = FoodForm.form.bindFromRequest.get
      val food = Food(0, data.name, data.calories, data.proteins, data.carbohydrates, data.lipids)
      Foods.add(food)
      Ok(views.html.addFood("Food added."))
    }
  }

}