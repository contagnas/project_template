package starter

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import starter.api.GreetingsApi
import starter.database.Transactor
import starter.service.Service
import tapir.docs.openapi._
import tapir.openapi.circe.yaml._
import tapir.swagger.http4s.SwaggerHttp4s

object Main extends IOApp {
  private val docs = GreetingsApi.endpoints.toOpenAPI("GreetingsApi", "1.0").toYaml

  val app = Router(
    "/" -> new Service(Transactor.xa).routes,
    "/docs" -> new SwaggerHttp4s(docs).routes
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(app)
      .serve
      .drain
      .compile
      .drain
      .map(_ => ExitCode.Success)
  }
}
