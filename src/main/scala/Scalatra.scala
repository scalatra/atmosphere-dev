import atmotest._
import org.scalatra._
import org.scalatra.atmosphere._
import javax.servlet.ServletContext

class Scalatra extends LifeCycle {
  override def init(context: ServletContext) {
    context.enableAtmosphere(useNative = false)
    context.mount(new MyScalatraServlet, "/*")
  }
}
