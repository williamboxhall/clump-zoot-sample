package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

trait Users extends Api {

  @endpoint(method = GET, path = "/:id")
  def get(id: Long): Future[User]

}

case class User(id: Long, firstName: String, lastName: String)