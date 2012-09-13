import atmotest._
import org.scalatra._
import org.scalatra.atmosphere._
import javax.servlet.ServletContext

class Bootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.enableAtmosphere()
//    context.mount(new MyScalatraServlet, "/*")
  }
}