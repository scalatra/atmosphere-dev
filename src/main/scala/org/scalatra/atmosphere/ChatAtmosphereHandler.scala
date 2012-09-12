package org.scalatra
package atmosphere

import org.atmosphere.cpr.{AtmosphereResource, AtmosphereResourceEvent, AtmosphereHandler}
import org.atmosphere.config.service.AtmosphereHandlerService
import java.util.Date
import com.fasterxml.jackson.databind.ObjectMapper
import org.json4s._
import org.json4s.jackson._

object ChatAtmosphereHandler {
  private case class ChatData(author: String, text: String) {
    override def toString: String = """{"text":"%s","author":"%s","time":%s}""".format(text, author, new Date().getTime)
  }
}

@AtmosphereHandlerService(path = "/chat")
class ChatAtmosphereHandler extends AtmosphereHandler {

  import ChatAtmosphereHandler._
  implicit val formats: Formats = DefaultFormats

  def onRequest(r: AtmosphereResource) {
    val req = r.getRequest

    if (req.getMethod.equalsIgnoreCase("GET")) {
      if (req.getHeader("negotiating") == null) {
        r.suspend()
      } else {
        r.getResponse.getWriter.write("OK")
      }
    } else if (req.getMethod.equalsIgnoreCase("POST")) {
      r.getBroadcaster.broadcast(req.getReader.readLine.trim)
    }
  }

  def onStateChange(event: AtmosphereResourceEvent) {
    val r = event.getResource
    val res = r.getResponse
    val writer = res.getWriter

    if (event.isSuspended) {
      val body = parse(event.getMessage.toString)
      val author = (body \ "author").extract[String]
      val message = (body \ "text").extract[String]

      writer.write(ChatData(author, message).toString)

      import AtmosphereResource.TRANSPORT._
      r.transport() match {
        case JSONP | AJAX | LONG_POLLING => r.resume()
        case _ => writer.flush()
      }

    } else if (!event.isResuming) {
      event.broadcaster.broadcast(ChatData("Someone", "Say bye bye").toString)
    }
  }

  def destroy() {}
}