package com.shopping.checkout.service

import javax.ws.rs.Path

import akka.http.scaladsl.server.Directives
import com.typesafe.config.ConfigFactory
import io.swagger.annotations._

import scala.annotation.tailrec

/**
  * Service class to find the total price of a Cart
  */
@Path("/shopping")
@Api(value = "/shopping", description = "Operations about shopping",  produces = "application/json")
class CheckoutRestService extends Directives {

  val checkoutRoutes = pathPrefix("shopping") {
    shoppingCheckoutPostRoute
  }

  import com.shopping.checkout.domain.ShoppingCartProtocol._

  val config = ConfigFactory.load()
  val priceApple = config.getDouble("price.apple")
  val priceOrange = config.getDouble("price.orange")

  @ApiOperation(value = "Checkout for Shopping Cart", nickname = "shoppingCartCheckout", httpMethod = "POST", consumes = "application/json", produces = "application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "GetBooking", dataType = "com.shopping.checkout.domain.ShoppingCartProtocol$Cart", paramType = "body", required = true)
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 201, message = "Entity Created"),
    new ApiResponse(code = 500, message = "Internal Server Error")
  ))
  def shoppingCheckoutPostRoute =
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
