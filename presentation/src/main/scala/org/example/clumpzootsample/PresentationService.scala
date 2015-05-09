package org.example.clumpzootsample

import io.getclump.{Clump, ClumpSource}
import org.example.clumpzootsample.Filters.requestLogFilter

object PresentationService extends App {
  val tracksService = Create.clientFor[Tracks]("localhost", 3333)
  val usersService = Create.clientFor[Users]("localhost", 2222)
  val tracks = Clump.source(tracksService.list _)(_.id)
  val users = Clump.source(usersService.list _)(_.id)
  Create.serverFor[EnrichedTracks]("EnrichedTracks", 1111, new EnrichedTracksController(tracks, users), requestLogFilter)
}

class EnrichedTracksController(tracks: ClumpSource[Long, Track], users: ClumpSource[Long, User]) extends EnrichedTracks {
  override def get(trackId: Long) = enrichedTrack(trackId).get

  override def list(trackIds: List[Long]) = Clump.traverse(trackIds)(enrichedTrack).list

  private def enrichedTrack(trackId: Long) = for {
    track <- tracks.get(trackId)
    user <- users.get(track.creatorId)
  } yield {
    EnrichedTrack(track.title, s"${user.firstName} ${user.lastName}")
  }
}


