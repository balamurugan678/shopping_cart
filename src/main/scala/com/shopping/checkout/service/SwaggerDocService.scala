package com.shopping.checkout.service

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.swagger.akka.model.Info
import com.github.swagger.akka.{HasActorSystem, SwaggerHttpService}

import scala.reflect.runtime.{universe => ru}


class SwaggerDocService(address: String, port: Int, system: ActorSystem) extends SwaggerHttpService with HasActorSystem {
  override implicit val actorSystem: ActorSystem = system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override val apiTypes = Seq(ru.typeOf[CheckoutRestService], ru.typeOf[CheckoutOfferRestService])
  override val host = address + ":" + port
  override val basePath = "/"
  override val apiDocsPath = "api-docs"
  override val info = Info(description = "Swagger docs for Shopping Cart Checkout service", version = "1.0", title = "Shopping Cart API", termsOfService = "Shopping Cart API terms and conditions")
}
