package com.shopping.checkout.domain

import com.shopping.checkout.marshallers.JsonSupport


object ShoppingCartProtocol extends JsonSupport {

  case class Cart(items:List[String])

  case class Price(total:String)

}

