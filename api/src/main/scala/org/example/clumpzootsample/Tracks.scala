package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

trait Tracks extends Api {

  @endpoint(method = GET, path = "/:id")
  def get(id: Long): Future[Track]

}

case class Track(id: Long, creatorId: Long, title: String, duration: Int)