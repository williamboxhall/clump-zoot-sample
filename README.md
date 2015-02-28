# Clump + Zoot sample application

## Summary

This project aims to demonstrate how [Zoot](https://github.com/fwbrasil/zoot) and [Clump](https://github.com/getclump/clump) enable a developer to make a hyper-minimal microservices ecosystem.

[Zoot](https://github.com/fwbrasil/zoot) is a routing and API specification framework that allows the developer to specify strongly-typed microservice interfaces to share between client and server, and hides JSON marshalling/unmarshalling details. Zoot is compatible with different Future-based web frameworks, this example uses [Finagle](https://twitter.github.io/finagle/) which is built on top of [Netty](http://netty.io/)

[Clump](https://github.com/getclump/clump) is a service composition library that abstracts away the concerns of bulk-fetching, batching and retries and the code-wrangling required to do this manually.

## Running

To start all 3 services:

```
make start-all
```

You may then perform a REST request to fetch tracks from `EnrichedTrackService,` which aggregates calls to `TrackService` and `UserService`

```
curl 'http://localhost:1111/enrichedtracks/1'
curl 'http://localhost:1111/enrichedtracks?trackIds=\[1,2,3\]'
```

To kill all 3 services:

```
make stop-all
```

## Code preview

Together, [Zoot](https://github.com/fwbrasil/zoot) and [Clump](https://github.com/getclump/clump) allow you write minimal microservices that look like this:

```scala
...
trait Tracks extends Api {

  @endpoint(method = GET, path = "/tracks")
  def list(trackIds: List[Long]): Future[List[Track]]

}

case class Track(id: Long, creatorId: Long, title: String)
...
object TracksService extends App {
  Build.serverFor[Tracks]("Tracks", 3333, new TracksController)
}

class TracksController extends Tracks {
  override def list(trackIds: List[Long]) = Future.value(trackIds.map(track))

  private def track(trackId: Long) = Track(trackId, trackId * 10, s"song$trackId")
}
...

object PresentationService extends App {
  val tracksService = Build.clientFor[Tracks]("localhost", 3333)
  val usersService = Build.clientFor[Users]("localhost", 2222)
  val tracks = Clump.source(tracksService.list)(_.id)
  val users = Clump.source(usersService.list)(_.id)
  Build.serverFor[EnrichedTracks]("EnrichedTracks", 1111, new EnrichedTracksController(tracks, users))
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

```



