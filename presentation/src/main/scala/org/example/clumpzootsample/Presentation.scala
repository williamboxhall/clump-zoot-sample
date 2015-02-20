package org.example.clumpzootsample

import com.twitter.util.Future
import net.fwbrasil.zoot.core.mapper.JacksonStringMapper
import java.net.InetSocketAddress
import com.twitter.finagle.builder.ServerBuilder
import net.fwbrasil.zoot.finagle.FinagleServer
import com.twitter.finagle.builder.ClientBuilder
import net.fwbrasil.zoot.core.{Api, Client, Server}
import com.twitter.finagle.http.Http
import scala.concurrent
import scala.concurrent.ExecutionContext.Implicits.global
import net.fwbrasil.zoot.finagle.FinagleClient
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import com.twitter.finagle.http.RichHttp
import com.twitter.finagle.http.Request

object Presentation extends App {


  private implicit val mirror = scala.reflect.runtime.currentMirror
  private implicit val mapper = new JacksonStringMapper

  val tracks = new Tracks {
    override def get(id: Long): Future[Track] = ???
  }
  val users = new Users {
    override def get(id: Long): Future[User] = ???
  }

  val server = {
    val serverBuilder =
      ServerBuilder()
        .codec(RichHttp[Request](Http()))
        .bindTo(new InetSocketAddress(1111))
        .name("EnrichedTracks")

    new FinagleServer(Server[EnrichedTracks](new EnrichedTracksController(tracks, users)), serverBuilder.build)
  }

  println("Started Presentation service.")

}

class EnrichedTracksController(tracks: Tracks, users: Users) extends EnrichedTracks {
  override def get(trackId: Long) = for {
    track <- tracks.get(trackId)
    user <- users.get(track.creatorId)
  } yield {
    EnrichedTrack(track.title, s"${user.firstName} ${user.lastName}")
  }
}


