package controllers

import javax.inject.Inject

import com.google.inject.Provider
import play.api.Application
import play.api.i18n._
import play.api.libs.json.{JsObject, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag


/**
  * Created by matthieu.villard on 11.06.2017.
  */
trait AuthActionBuilder extends Controller with I18nSupport{
  object AuthAction extends ActionBuilder[Request] {
    override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
      try {
        if (request.session.get("email").nonEmpty) {
          block(request)
        }
        else {
          unauthorized
        }
      } catch {
        case e: Exception => Future.successful(Results.InternalServerError(e.getMessage))
      }
    }

    def unauthorized() = Future.successful(
      Results.Unauthorized(
        Json.obj(
          "status" -> "error",
          "message" -> Messages("NOT_AUTHENTICATED")
        )
      )
    )
  }
}