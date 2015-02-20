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

object UsersService extends App {

  private implicit val mirror = scala.reflect.runtime.currentMirror
  private implicit val mapper = new JacksonStringMapper

  val server = {
    val serverBuilder =
      ServerBuilder()
        .codec(RichHttp[Request](Http()))
        .bindTo(new InetSocketAddress(2222))
        .name("EnrichedTracks")

    new FinagleServer(Server[Users](new UsersController), serverBuilder.build)
  }

  println("Started Users service.")
}

class UsersController extends Users {
  override def get(id: Long) = Future.value(User(id, s"first$id", s"last$id"))
}

