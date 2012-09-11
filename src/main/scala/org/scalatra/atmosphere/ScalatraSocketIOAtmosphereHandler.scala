package org.scalatra
package atmosphere

import org.atmosphere.socketio.cpr.SocketIOAtmosphereHandler
import org.atmosphere.cpr.AtmosphereResource
import org.atmosphere.socketio.SocketIOSessionOutbound
import org.atmosphere.socketio.transport.DisconnectReason
import org.atmosphere.config.service.AtmosphereHandlerService
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor

@AtmosphereHandlerService(path = "/socketio", interceptors = Array(classOf[AtmosphereResourceLifecycleInterceptor]))
class ScalatraSocketIOAtmosphereHandler extends SocketIOAtmosphereHandler {
  def onConnect(event: AtmosphereResource, handler: SocketIOSessionOutbound) {
    println("we got a client")
  }

  def onDisconnect(event: AtmosphereResource, handler: SocketIOSessionOutbound, reason: DisconnectReason) {
    println("client went away")
  }

  def onMessage(event: AtmosphereResource, handler: SocketIOSessionOutbound, message: String) {
    println("we got a message")
  }
}