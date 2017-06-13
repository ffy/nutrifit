package nutrifit

import nutrifit.dto.{Food, User}
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by matthieu.villard on 12.06.2017.
  */
object ApiClient {
  val HOST: String = "http://localhost:9000"

  def postUser(email: String, password: String, gender: String, weight: String, height: String): Future[Unit] = {
    Ajax.post(
      HOST + "/register",
      s"""{"email":"$email", "password":"$password", "weight":"$weight","height":"$height", "gender": "$gender"}""",
      1000,
      Map("content-type" -> "application/json")
    ).map(
      xhr => xhr.status match {
        case 201 => Future.successful()
        case _ => Future.failed(new RuntimeException(xhr.responseText))
      }
    )
  }

  def postLogin(email: String, password: String): Future[Unit] = {
    Ajax.post(
      HOST + "/login",
      s"""{"email":"$email", "password":"$password"}""",
      1000,
      Map("content-type" -> "application/json"),
      true
    ).map(
      xhr => xhr.status match {
        case 200 => {
          Future.successful()
        }
        case _ => Future.failed(new RuntimeException(xhr.responseText))
      }
    )
  }

  def getUser(): Future[Unit] = {
    Ajax.get(
      HOST + "/users",
      null,
      0,
      Map.empty,
      true
    ).map(
      xhr => xhr.status match {
        case 200 => Future.successful()
        case _ => Future.failed(new RuntimeException(xhr.responseText))
      }
    )
  }

  def getFoods(): Future[List[Food]] = {
    Ajax.get(
      HOST + "/foods/list",
      null,
      0,
      Map.empty,
      true
    ).flatMap(
      xhr => xhr.status match {
        case 200 => Future.successful(Food.parseAll(xhr.responseText))
        case _ => Future.failed(new RuntimeException(xhr.responseText))
      }
    )
  }

  def deleteFood(id: Int): Future[Unit] = {
    Ajax.delete(
      HOST + s"/foods/delete/$id",
      null,
      0,
      Map.empty,
      true
    ).map(
      xhr => xhr.status match {
        case 200 => Future.successful()
        case _ => Future.failed(new RuntimeException(xhr.responseText))
      }
    )
  }

  def postFood(name: String, calories: String, proteins: String, carbohydrates: String, lipids: String): Future[Unit]
  = {
    Ajax.post(
      HOST + "/foods/add",
      s"""{"name":"$name", "calories":"$calories", "proteins":"$proteins","carbohydrates":"$carbohydrates", "lipids":"$lipids"}""",
      1000,
      Map("content-type" -> "application/json"),
      true
    ).map(
      xhr => xhr.status match {
        case 201 => {
          Future.successful()
        }
        case _ => Future.failed(new RuntimeException(xhr.responseText))
      }
    )
  }
}
