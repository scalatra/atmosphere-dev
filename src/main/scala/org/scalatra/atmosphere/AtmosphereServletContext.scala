package org.scalatra
package atmosphere

import javax.servlet.ServletContext
import org.atmosphere.cpr.{ApplicationConfig, AtmosphereServlet}
import org.atmosphere.socketio.cpr.SocketIOAtmosphereInterceptor


case class SocketIOConfig(
  transports: Seq[String] = Vector("websocket", "xhr-polling", "jsonp-polling"),
  timeout: Long = 25000,
  hearbeat: Long = 15000,
  suspendTime: Long = 30000,
  bufferSize: Long = 4096
)

class AtmosphereServletContext(context: ServletContext) {

  def enableAtmosphere(
        useNative: Boolean = true,
        useBlocking: Boolean = false,
        socketIoConfig: Option[SocketIOConfig] = None) { // TODO: Add all the relevant config options

    val atmoServlet = classOf[AtmosphereServlet]
    val reg = context.addServlet(atmoServlet.getSimpleName, atmoServlet)
    reg.setInitParameter(ApplicationConfig.PROPERTY_NATIVE_COMETSUPPORT, useNative.toString)
    reg.setInitParameter(ApplicationConfig.PROPERTY_BLOCKING_COMETSUPPORT, useBlocking.toString)
    socketIoConfig foreach { cfg =>
      reg.setInitParameter(SocketIOAtmosphereInterceptor.BUFFER_SIZE_INIT_PARAM, cfg.bufferSize.toString)
      reg.setInitParameter(SocketIOAtmosphereInterceptor.SOCKETIO_HEARTBEAT, cfg.hearbeat.toString)
      reg.setInitParameter(SocketIOAtmosphereInterceptor.SOCKETIO_TIMEOUT, cfg.timeout.toString)
      reg.setInitParameter(SocketIOAtmosphereInterceptor.SOCKETIO_SUSPEND, cfg.suspendTime.toString)
      reg.setInitParameter(SocketIOAtmosphereInterceptor.SOCKETIO_TRANSPORT, cfg.transports.mkString(","))
    }
    reg.setLoadOnStartup(0)
    reg.addMapping("/socketio/*")
  }


}