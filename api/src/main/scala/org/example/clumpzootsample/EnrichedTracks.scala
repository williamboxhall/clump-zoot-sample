package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.core.Api
import net.fwbrasil.zoot.core.request.RequestMethod._

trait EnrichedTracks extends Api {

  @endpoint(method = GET, path = "/:id")
  def get(trackId: Long): Future[EnrichedTrack]

}

case class EnrichedTrack(title: String, creator: String)