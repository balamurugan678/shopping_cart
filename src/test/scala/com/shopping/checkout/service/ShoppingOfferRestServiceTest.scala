package com.shopping.checkout.service

import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.shopping.checkout.controller.ShoppingRestController
import org.scalatest.{Matchers, WordSpec}

class ShoppingOfferRestServiceTest extends WordSpec with Matchers with ScalatestRouteTest with ShoppingRestController {

  import com.shopping.checkout.domain.ShoppingCartProtocol._

  "Shopping Cart API" should {

    "Posting to /offer should add the price of the items in an offer price" in {

      val jsonRequest = ByteString(
        s"""
           {
           |	"items":[ "Apple", "Apple", "Orange", "Apple" ]
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/offer",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))


      postRequest ~> shoppingCartRoutes ~> check {
        handled.shouldBe(true)
        status.isSuccess() shouldEqual true
        responseAs[Price].total shouldEqual ("£1.45")
      }
    }

    "Posting to /offer should add the offer price of the items with 10 Apples" in {

      val jsonRequest = ByteString(
        s"""
           {
           |	"items":[ "Apple", "Apple", "Apple", "Apple", "Apple", "Apple", "Apple", "Apple","Apple", "Apple"  ]
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/offer",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))


      postRequest ~> shoppingCartRoutes ~> check {
        handled.shouldBe(true)
        status.isSuccess() shouldEqual true
        responseAs[Price].total shouldEqual ("£3.00")
      }
    }

    "Posting to /offer should add the offer price of the items with 10 Oranges" in {

      val jsonRequest = ByteString(
        s"""
           {
           |	"items":[ "Orange", "Orange", "Orange", "Orange", "Orange", "Orange", "Orange", "Orange","Orange", "Orange"  ]
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/offer",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))


      postRequest ~> shoppingCartRoutes ~> check {
        handled.shouldBe(true)
        status.isSuccess() shouldEqual true
        responseAs[Price].total shouldEqual ("£1.75")
      }
    }


    "Posting to /offer should add the offer price of the Apples and Oranges only and not for others" in {

      val jsonRequest = ByteString(
        s"""
           {
           |	"items":[ "Apple", "Orange", "Apple", "Orange", "Pineapple", "Banana", "Mango", "Passion","Cranberry", "Orange" ]
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/offer",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))


      postRequest ~> shoppingCartRoutes ~> check {
        handled.shouldBe(true)
        status.isSuccess() shouldEqual true
        responseAs[Price].total shouldEqual ("£1.10")
      }
    }

    "not handle the invalid json" in {
      Post("/shoppingCart", "{\"items\":\"1\"}") ~> shoppingCartRoutes ~> check {
        handled.shouldBe(false)
      }
    }

  }

}
