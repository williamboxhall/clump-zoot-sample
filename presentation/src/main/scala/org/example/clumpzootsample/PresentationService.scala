package org.example.clumpzootsample

import scala.concurrent.ExecutionContext.Implicits.global

object PresentationService extends App {
  val users = Build.clientFor[Users](2222)
  val tracks = Build.clientFor[Tracks](3333)
  val server = Build.serverFor("EnrichedTracks", 1111, new EnrichedTracksController(tracks, users))
}

class EnrichedTracksController(tracks: Tracks, users: Users) extends EnrichedTracks {
  override def get(trackId: Long) = for {
    track <- tracks.get(trackId)
    user <- users.get(track.creatorId)
  } yield {
    EnrichedTrack(track.title, s"${user.firstName} ${user.lastName}")
  }
}


