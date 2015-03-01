package org.example.clumpzootsample

import java.net.InetSocketAddress

import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.{Http, Request, RichHttp}
import net.fwbrasil.zoot.core._
import net.fwbrasil.zoot.core.filter.Filter
import net.fwbrasil.zoot.core.mapper.JacksonStringMapper
import net.fwbrasil.zoot.finagle.{FinagleClient, FinagleServer}

import scala.concurrent.ExecutionContext.Implicits.global

object Create {

  private implicit val mirror = scala.reflect.runtime.currentMirror
  private implicit val mapper = new JacksonStringMapper

  def serverFor[T <: Api : Manifest](name: String, port: Int, controller: T, filters: Filter*) = {
    val serverBuilder = ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .bindTo(new InetSocketAddress(port))
      .name(name)

    val allFilters = filters.foldLeft(noFilter) {
      (x, y) => y andThen x
    }

    val server = new FinagleServer(allFilters andThen Server[T](controller), serverBuilder.build)
    println(s"Started $name service.")
    server
  }

  def clientFor[T <: Api : Manifest](host: String, port: Int) = {
    val clientBuilder = ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .hosts(s"$host:$port")
      .hostConnectionLimit(5)
    Client[T](FinagleClient(clientBuilder.build))
  }

  def noFilter = new Filter {
    override def apply(input: request.Request, next: Service) = next(input)
  }
}
