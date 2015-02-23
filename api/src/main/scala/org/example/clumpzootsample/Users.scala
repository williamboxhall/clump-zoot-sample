package org.example.clumpzootsample

import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

import scala.concurrent.Future

trait Users extends Api {

  @endpoint(method = GET, path = "/users/:id")
  def get(userId: Long): Future[User]

  @endpoint(method = GET, path = "/users")
  def list(userIds: Set[Long]): Future[Set[User]]
}

case class User(id: Long, firstName: String, lastName: String)