package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.finagle.FutureBridge._

object TracksService extends App {
  Create.serverFor[Tracks]("Tracks", 3333, new TracksController)
}

class TracksController extends Tracks {
  override def get(trackId: Long) = Future.value(trackFor(trackId))

  override def list(trackIds: List[Long]) = Future.value(trackIds.map(trackFor))

  private def trackFor(trackId: Long) = Track(trackId, trackId * 10, s"song$trackId")
}

