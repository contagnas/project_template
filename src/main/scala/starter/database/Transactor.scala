package starter.database

import cats.effect.{Blocker, ContextShift, IO}
import doobie.util.{ExecutionContexts, transactor}

object Transactor {
  def xa(implicit cs: ContextShift[IO]) =
    transactor.Transactor.fromDriverManager[IO] (
      "org.postgresql.Driver",
      "jdbc:postgresql:LOAD_FROM_PROP",
      "LOAD_FROM_PROP",
      "LOAD_FROM_VAULT",
      Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )
}
