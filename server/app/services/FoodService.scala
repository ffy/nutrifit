package services

import com.google.inject.{Inject, Singleton}
import models.{Food, Foods}
import utils.SlickAPI._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by matthieu.villard on 23.05.2017.
  */
@Singleton
class FoodService @Inject()(implicit ec: ExecutionContext) {

  def list: Future[Seq[Food]] = {
    Foods.run
  }

  def create(f: Food): Future[Food] = {
    Foods.insert(f).transactionally.run
  }

  def delete(id: Int): Future[Boolean] = {
    Foods.findById(id).delete.run.map {
      case 0 => false
      case 1 => true
    }
  }

}
