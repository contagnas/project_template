package starter.service

import java.sql.SQLException

import starter.api.GreetingsApi
import starter.api.GreetingsApi.{NewPerson, Person}
import starter.database.Database
import cats.data.EitherT
import cats.effect.{ContextShift, IO}
import cats.syntax.semigroupk._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.http4s.HttpRoutes
import tapir.server.http4s._

class Service(xa: Transactor[IO])(implicit cs: ContextShift[IO]) {
  def addPerson(name: String): IO[Either[String, GreetingsApi.Person]] =
    Database.addPerson(NewPerson(name)).attemptSql.transact(xa).leftToMessage

  def deletePerson(id: Int): IO[Either[String, GreetingsApi.Person]] =
    Database.deletePerson(id).attemptSql.transact(xa).leftToMessage

  def greetPeople(u: Unit): IO[Either[String, List[String]]] =
    Database.getPeople.map(_.map {
      case Person(_, name) => s"Hello, $name!"
    }).attemptSql.transact(xa).leftToMessage

  val routes: HttpRoutes[IO] = List(
    GreetingsApi.addPersonToGreet.toRoutes(addPerson),
    GreetingsApi.deletePersonToGreet.toRoutes(deletePerson),
    GreetingsApi.getGreetings.toRoutes(greetPeople),
  ).reduce(_ <+> _)

  private implicit class SqlExceptionMessage[T](ioet: IO[Either[SQLException, T]]) {
    def leftToMessage: IO[Either[String, T]] = EitherT(ioet).leftMap(_.getMessage).value
  }
}
