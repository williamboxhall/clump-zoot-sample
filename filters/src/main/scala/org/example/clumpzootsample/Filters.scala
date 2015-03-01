package org.example.clumpzootsample

import net.fwbrasil.zoot.core.filter.Filter
import net.fwbrasil.zoot.core.request.Request

import scala.concurrent.ExecutionContext.Implicits.global

object Filters {
  val requestLogFilter =
    new Filter {
      override def apply(request: Request, next: Service) = {
        println(s"request $request")
        next(request)
      }
    }

  val exceptionLogFilter =
    new Filter {
      override def apply(request: Request, next: Service) = {
        val response = next(request)
        response.onFailure {
          case _ => println(s"Exception was thrown!")
        }
        response
      }
    }
}
