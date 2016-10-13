package com.shopping.checkout.service

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.annotation.tailrec


trait ShoppingRestController extends Directives {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val shoppingCartRoutes = new ShoppingRestService().route

}

class ShoppingRestService extends Directives {

  val route = pathPrefix("shopping") {
    shoppingGetRoute
  }

  import com.shopping.checkout.domain.ShoppingCartProtocol._

  val config = ConfigFactory.load()
  val priceApple = config.getDouble("price.apple")
  val priceOrange = config.getDouble("price.orange")

  def shoppingGetRoute =
    post {
      entity(as[Cart]) {
        cart => complete {
          Price(s"£${totalCost(cart.items, priceApple, priceOrange)}")
        }
      }
    }


  def totalCost(items: List[String], priceApple: BigDecimal, priceOrange: BigDecimal): BigDecimal = {
    @tailrec
    def pricing(itemList: List[String], priceAccumulator: BigDecimal): BigDecimal = {
      itemList match {
        case "Apple" :: tail => pricing(tail, priceAccumulator + priceApple)
        case "Orange" :: tail => pricing(tail, priceAccumulator + priceOrange)
        case _ :: tail => pricing(tail, priceAccumulator)
        case Nil => priceAccumulator
      }
    }
    pricing(items, 0)
  }

}



