package nutrifit

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.concurrent.ExecutionContext.Implicits.global
import ApiClient._
import nutrifit.dto.{Error, User}
import mhtml._
import scala.xml.Node
import org.scalajs.dom

object NutrifitApp extends JSApp {

  lazy val loginBtn =
    dom.document.getElementById("login").asInstanceOf[html.Button]
  lazy val email =
    dom.document.getElementById("email").asInstanceOf[html.Input]
  lazy val password =
    dom.document.getElementById("password").asInstanceOf[html.Input]
  lazy val gender =
    dom.document.getElementById("gender").asInstanceOf[html.Input]
  lazy val weight =
    dom.document.getElementById("weight").asInstanceOf[html.Input]
  lazy val height =
    dom.document.getElementById("height").asInstanceOf[html.Input]
  lazy val emailLogin =
    dom.document.getElementById("emailLogin").asInstanceOf[html.Input]
  lazy val passwordLogin =
    dom.document.getElementById("passwordLogin").asInstanceOf[html.Input]

  lazy val name =
    dom.document.getElementById("name").asInstanceOf[html.Input]
  lazy val calories =
    dom.document.getElementById("calories").asInstanceOf[html.Input]
  lazy val proteins =
    dom.document.getElementById("proteins").asInstanceOf[html.Input]
  lazy val carbohydrates =
    dom.document.getElementById("carbohydrates").asInstanceOf[html.Input]
  lazy val lipids =
    dom.document.getElementById("lipids").asInstanceOf[html.Input]


  def main(): Unit = {
  }

  @JSExportTopLevel("register")
  def register(): Unit = {
    postUser(email.value, password.value, gender.value, weight.value, height.value).map(
      _ => {
        loginBtn.click()
      })
    .onFailure {
      case dom.ext.AjaxException(req) => {
        dom.window.alert(Error.parse(req.responseText).message)
      }
    }
  }

  @JSExportTopLevel("login")
  def login(): Unit = {
    postLogin(emailLogin.value, passwordLogin.value).map(
      _ => {
        dom.window.location.assign("main.html")
      })
      .onFailure {
        case dom.ext.AjaxException(req) => {
          dom.window.alert(Error.parse(req.responseText).message)
        }
      }
  }

  @JSExportTopLevel("init")
  def init(): Unit = {
    getUser().onFailure {
        case dom.ext.AjaxException(req) => {
          dom.window.location.assign("index.html")
        }
      }
  }

  @JSExportTopLevel("loadFoods")
  def loadFoods(containerId: String): Unit = {
    val headers =
      <tr>
        <th>Name</th>
        <th>Calories</th>
        <th>proteines</th>
        <th>carbohydrates</th>
        <th>lipids</th>
      </tr>
    dom.document.getElementById(containerId).innerHTML = ""
    mount(dom.document.getElementById(containerId), headers)
    getFoods().map(foods => {
      foods.foreach(f => {
        val component =
          <tr>
            <td>{f.name}</td>
            <td>{f.calories}</td>
            <td>{f.proteins}</td>
            <td>{f.carbohydrates}</td>
            <td>{f.lipids}</td>
            <td><a onclick={() => removeFood(f.id, containerId)}>Delete</a></td>
          </tr>
        mount(dom.document.getElementById(containerId), component)
      })
    }).onFailure {
      case dom.ext.AjaxException(req) => {
        dom.window.alert(Error.parse(req.responseText).message)
      }
    }
  }

  @JSExportTopLevel("removeFood")
  def removeFood(id: Int, containerId: String): Unit = {
    deleteFood(id).map(f => {
      loadFoods(containerId)
    }).onFailure {
      case dom.ext.AjaxException(req) => {
        dom.window.alert(Error.parse(req.responseText).message)
      }
    }
  }

  @JSExportTopLevel("createFood")
  def createFood(containerId: String): Unit = {
    postFood(name.value, calories.value, proteins.value, carbohydrates.value, lipids.value).map(f => {
      loadFoods(containerId)
    }).onFailure {
      case dom.ext.AjaxException(req) => {
        dom.window.alert(Error.parse(req.responseText).message)
      }
    }
  }
}