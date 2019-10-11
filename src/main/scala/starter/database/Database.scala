package starter.database

import starter.api.GreetingsApi.{NewPerson, Person}
import doobie._
import doobie.implicits._

object Database {
  implicit val logHandle = LogHandler.jdkLogHandler

  def addPerson(person: NewPerson): doobie.ConnectionIO[Person] =
    sql"""insert into person (name) values (${person.name})"""
    .update
    .withUniqueGeneratedKeys[Person]("person_id", "name")

  def deletePerson(personId: Int): doobie.ConnectionIO[Person] =
    sql"""delete from person where person_id = $personId"""
      .update
      .withUniqueGeneratedKeys[Person]("person_id", "name")

  val getPeople: doobie.ConnectionIO[List[Person]] =
    sql"""select person_id, name from person"""
      .query[Person]
      .to[List]
}
