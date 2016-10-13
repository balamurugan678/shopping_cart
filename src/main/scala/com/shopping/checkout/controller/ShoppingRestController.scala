package com.shopping.checkout.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.shopping.checkout.service.{CheckoutOfferRestService, CheckoutRestService}


trait ShoppingRestController extends Directives {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val shoppingCartRoutes = new CheckoutRestService().checkoutRoutes ~ new CheckoutOfferRestService().shoppingOfferRoutes

}
