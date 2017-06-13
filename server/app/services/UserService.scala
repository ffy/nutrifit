package services

import com.google.inject.{Inject, Singleton}
import models.{User, Users}
import play.api.i18n.Messages
import utils.BCrypt
import utils.SlickAPI._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by matthieu.villard on 23.05.2017.
  */
@Singleton
class UserService @Inject() (implicit ec: ExecutionContext) {
  def get(email: String): Future[User] = {
    Users.filter(_.email === email).head
  }

  def list: Future[Seq[User]] = {
    Users.run
  }

  def create(u: User): Future[User] = {
    Users.insert(u).transactionally.run
  }

  def check(email: String, password: String): Future[User] = {
    get(email).filter(u => BCrypt.checkpw(password, u.password))
  }
}
