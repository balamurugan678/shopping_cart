package com.shopping.checkout.domain

import com.shopping.checkout.marshallers.JsonSupport

/**
  * Entity objects with Json4s marshalling support
  */
object ShoppingCartProtocol extends JsonSupport {

  case class Cart(items:List[String])

  case class Price(total:String)

}

