package starter.database

import cats.effect.{ExitCode, IO, IOApp}
import doobie.implicits._

object Initialize extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =
    sql"""
      create table if not exists person(
        person_id serial,
        name text not null check(name <> '')
      )
    """
      .update.run.transact(Transactor.xa)
      .map(_ => ExitCode.Success)
}
