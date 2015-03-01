package org.example.clumpzootsample

import net.fwbrasil.zoot.core.filter.Filter
import net.fwbrasil.zoot.core.request.Request

object Filters {
  val requestLogFilter =
    new Filter {
      override def apply(request: Request, next: Service) = {
        println(s"request $request")
        next(request)
      }
    }
}
