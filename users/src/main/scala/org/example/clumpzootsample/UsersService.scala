package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.finagle.FutureBridge._

object UsersService extends App {
  val server = Build.serverFor[Users]("Users", 2222, new UsersController)
}

class UsersController extends Users {
  override def get(id: Long) = Future.value(User(id, s"first$id", s"last$id"))
}

