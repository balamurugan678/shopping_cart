# Shopping Cart service

- A shopping cart application which serves the purpose of checkout in a shop. 

## What is inside the application

* The application provides API for checkout in a shop. 
* The API has been written in Scala using Akka.HTTP library. 
* The API mainly deals with Apples and Oranges in the cart and neglects the other items. 
* Apples cost 60p and oranges cost 25p.

## How to run the application

Use either one of the following options:

1. run the command `sbt run`
2. create a *fat jar* with the command `sbt assembly` and then `java -jar target/scala-2.11/shopping_cart-assembly-1.0.0.jar`
3. The default hostname and port has been specified in the application.conf as `localhost` and `8080` respectively. There is an provision to change these as well. Use `-Dhttp.host=something_else` as an environment variable when we the jar. 
4. The same approach goes for overriding the default port as well.

## To hit the checkout API

* Once the application is up(either via jar or in an IDE), hit the api using the Checkout Shopping Cart URL `http://localhost:8080/shopping`. The endpoint is a POST operation. 

* The sample request to the shopping cart endpoint:

```
  
{
    "items":[ "Apple", "Apple", "Orange", "Apple"] 
}
 
```

Sample response from the shopping cart endpoint:

```
{
  "total": "£2.05"
}
```


## To hit the offer API

* Once the application is up(either via jar or in an IDE), hit the api using the Checkout Shopping Cart URL `http://localhost:8080/offer`. The endpoint is a POST operation. 

* The sample request to the shopping cart offer endpoint:

```
  
{
    "items":[ "Apple", "Apple", "Orange", "Apple"] 
}
  
```

Sample response from the shopping cart offer endpoint:

```
{
  "total": "£1.45"
}
```


## Testing framework

* The shopping cart API uses Scalatest as the testing framework
* The tests can be found under the `test` directory.


## To specify the log file configuration

* Shopping Cart service uses typesafe's logging framework with logback as a wrapper for SLF4J.
* Use `-Dlogback.configurationFile` as an environment variable to specify the logback properties(Log location, Appender config, Rolling file config)


## Shopping Cart - Json Marshallers

* The shopping cart API uses Json4S for (un)marshalling purposes.
* The default Json marshaller - `akka-http-spray-json` could have boiler-plate code for different case classes and Json4S would avoid that. 


  