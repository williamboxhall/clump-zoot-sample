package org.example.clumpzootsample

import java.net.InetSocketAddress

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.util.Future
import net.fwbrasil.zoot.core.Server
import net.fwbrasil.zoot.core.mapper.JacksonStringMapper
import net.fwbrasil.zoot.finagle.FinagleServer
import scala.concurrent.ExecutionContext.Implicits.global
import net.fwbrasil.zoot.finagle.FutureBridge._

object TracksService extends App {

  private implicit val mirror = scala.reflect.runtime.currentMirror
  private implicit val mapper = new JacksonStringMapper

  val server = {
    val serverBuilder =
      ServerBuilder()
        .codec(RichHttp[Request](Http()))
        .bindTo(new InetSocketAddress(3333))
        .name("Tracks")

    new FinagleServer(Server[Tracks](new TracksController), serverBuilder.build)
  }

  println("Started Tracks service.")


}

class TracksController extends Tracks {
  override def get(id: Long) = Future.value(Track(id, id * 10, s"song$id"))
}

