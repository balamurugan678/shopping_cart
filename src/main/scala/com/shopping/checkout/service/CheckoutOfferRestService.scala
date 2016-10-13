package com.shopping.checkout.service

import akka.http.scaladsl.server.Directives
import com.typesafe.config.ConfigFactory

/**
  * Service class to find the offer price of a Cart
  */
class CheckoutOfferRestService extends Directives {

  val shoppingOfferRoutes = pathPrefix("offer") {
    shoppingOfferPostRoute
  }

  import com.shopping.checkout.domain.ShoppingCartProtocol._

  val config = ConfigFactory.load()
  val priceApple = config.getDouble("price.apple")
  val priceOrange = config.getDouble("price.orange")

  def shoppingOfferPostRoute =
    post {
      entity(as[Cart]) {
        cart => complete {
          Price(s"£${findOfferCartCost(cart, priceApple, priceOrange)}")
        }
      }
    }


  def findOfferCartCost(cart: Cart, priceApple: Double, priceOrange: Double): (BigDecimal) = {
    val itemReduce = cart.items.groupBy(identity).mapValues(_.size)
    val applesCount = itemReduce.get("Apple").getOrElse(0)
    val applePartition = findOfferPartition(applesCount, 2)
    val appleCost = (priceApple * applePartition._1) + (priceApple * applePartition._2)
    val orangesCount = itemReduce.get("Orange").getOrElse(0)
    val orangePartition = findOfferPartition(orangesCount, 3)
    val orangeCost = orangePartition._1 * priceOrange * 2 + orangePartition._2 * priceOrange
    appleCost + orangeCost
  }

  def findOfferPartition(dividend: Int, divisor: Int) = {
    val q: BigDecimal = dividend / divisor
    val mod: BigDecimal = dividend % divisor
    (q, mod)
  }
}
