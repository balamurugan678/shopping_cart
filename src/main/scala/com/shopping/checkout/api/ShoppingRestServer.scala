package com.shopping.checkout.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.shopping.checkout.controller.ShoppingRestController
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging


class ShoppingRestServer(implicit val system: ActorSystem,
                         implicit val materializer: ActorMaterializer) extends ShoppingRestController with LazyLogging {

  def startServer(asset: Route, address: String, port: Int) = {
    Http().bindAndHandle(asset, address, port)
    logger.info("Shopping Cart service has been started in the port " + port)
  }


}

object ShoppingRestServer extends App {

  implicit val actorSystem = ActorSystem("shopping-cart-rest-server")
  implicit val materializer = ActorMaterializer()

  val shoppingCartRoutes = new ShoppingRestServer().shoppingCartRoutes

  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  val server = new ShoppingRestServer()
  server.startServer(shoppingCartRoutes, host, port)

}
