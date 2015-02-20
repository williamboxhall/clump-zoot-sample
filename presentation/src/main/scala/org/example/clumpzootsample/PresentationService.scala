package org.example.clumpzootsample

import java.net.InetSocketAddress

import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.{Http, Request, RichHttp}
import net.fwbrasil.zoot.core.mapper.JacksonStringMapper
import net.fwbrasil.zoot.core.{Client, Server}
import net.fwbrasil.zoot.finagle.{FinagleClient, FinagleServer}

import scala.concurrent.ExecutionContext.Implicits.global

object PresentationService extends App {

  private implicit val mirror = scala.reflect.runtime.currentMirror
  private implicit val mapper = new JacksonStringMapper

  val users = {
    val clientBuilder = ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .hosts(s"localhost:2222")
      .hostConnectionLimit(5)

    Client[Users](FinagleClient(clientBuilder.build))
  }

  val tracks = {
    val clientBuilder = ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .hosts(s"localhost:3333")
      .hostConnectionLimit(5)

    Client[Tracks](FinagleClient(clientBuilder.build))
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


