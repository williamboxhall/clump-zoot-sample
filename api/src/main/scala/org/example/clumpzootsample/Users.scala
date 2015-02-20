package org.example.clumpzootsample

import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

import scala.concurrent.Future

trait Users extends Api {

  @endpoint(method = GET, path = "/:id")
  def get(id: Long): Future[User]

}

case class User(id: Long, firstName: String, lastName: String)