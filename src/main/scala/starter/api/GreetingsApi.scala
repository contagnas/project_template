package starter.api

import io.circe.generic.auto._
import tapir._
import tapir.json.circe._

object GreetingsApi {
  case class Person(id: Int, name: String)
  case class NewPerson(name: String)

  val addPersonToGreet: Endpoint[String, String, Person, Nothing] =
    endpoint.put
    .in("addPerson")
    .in(query[String]("name")
      .description("The person's name")
      .validate(Validator.custom(_.nonEmpty, "Name cannot be empty.")))
    .errorOut(stringBody)
    .out(jsonBody[Person])

  val deletePersonToGreet: Endpoint[Int, String, Person, Nothing] =
    endpoint.delete
    .in("deletePerson")
    .in(query[Int]("personId")
      .description("The ID of the person to delete"))
    .errorOut(stringBody)
    .out(jsonBody[Person])

  val getGreetings: Endpoint[Unit, String, List[String], Nothing] =
    endpoint.get
    .in("getGreetings")
    .description("Get all the greetings")
    .errorOut(stringBody)
    .out(jsonBody[List[String]])

  val endpoints = List(addPersonToGreet, deletePersonToGreet, getGreetings)
}
