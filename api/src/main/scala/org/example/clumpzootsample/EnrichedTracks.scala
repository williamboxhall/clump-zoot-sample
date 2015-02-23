package org.example.clumpzootsample

import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

import scala.concurrent.Future

trait EnrichedTracks extends Api {

  @endpoint(method = GET, path = "/enrichedtracks/:trackId")
  def get(trackId: Long): Future[EnrichedTrack]

  @endpoint(method = GET, path = "/enrichedtracks")
  def list(trackIds: List[Long]): Future[List[EnrichedTrack]]
}

case class EnrichedTrack(title: String, creator: String)