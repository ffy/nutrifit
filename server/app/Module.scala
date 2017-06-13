import com.google.inject.AbstractModule
import utils.SlickAPI

/**
  * Created by matthieu.villard on 23.05.2017.
  */
class Module extends AbstractModule {
  override def configure() = {
    requestInjection(SlickAPI)
  }
}