package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.finagle.FutureBridge._

object UsersService extends App {
  val server = Create.serverFor[Users]("Users", 2222, new UsersController)
}

class UsersController extends Users {
  override def get(userId: Long) = Future.value(userFor(userId))

  override def list(userIds: Set[Long]) = Future.value(userIds.map(userFor))

  private def userFor(trackId: Long): User = User(trackId, s"first$trackId", s"last$trackId")
}

