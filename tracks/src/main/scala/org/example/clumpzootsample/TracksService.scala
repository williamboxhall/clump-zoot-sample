package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.finagle.FutureBridge._

object TracksService extends App {
  Build.serverFor[Tracks]("Tracks", 3333, new TracksController)
}

class TracksController extends Tracks {
  override def get(id: Long) = Future.value(Track(id, id * 10, s"song$id"))
}

