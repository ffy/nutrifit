package services

import com.google.inject.{Inject, Singleton}
import models.{User, Users}
import utils.SlickAPI._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by matthieu.villard on 23.05.2017.
  */
@Singleton
class UserService @Inject() (ec: ExecutionContext) {
  def get(id: Int): Future[User] = {
    Users.findById(id).head
  }

  def list: Future[Seq[User]] = {
    Users.run
  }

  def create(u: User): Future[User] = {
    Users.insert(u).run
  }
}
