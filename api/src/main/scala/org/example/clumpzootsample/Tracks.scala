package org.example.clumpzootsample

import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

import scala.concurrent.Future

trait Tracks extends Api {

  @endpoint(method = GET, path = "/tracks/:trackId")
  def get(trackId: Long): Future[Track]

}

case class Track(id: Long, creatorId: Long, title: String)