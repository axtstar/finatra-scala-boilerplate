package com.axtstar.finatraSample

import com.twitter.finatra.http.request.RequestUtils
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.HttpServer
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.validation.{PastTime, Max}
import javax.inject.Inject

import com.twitter.finatra.request.{FormParam, QueryParam}
import org.joda.time.DateTime

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  // override default behaviour
  override val disableAdminHttpServer = false

  //override val defaultMaxRequestSize = 10.megabytes

  //override val modules = Seq(
  //  DoEverythingModule)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .add[ExampleController]
  }
}

// Controller
class ExampleController extends Controller {

  // http://127.0.0.1:8888/ping
  get("/ping") { request: Request =>
    info("ping")
    "pong"
  }

  // http://127.0.0.1:8888/name
  get("/name") { request: Request =>
    response.ok.body("山田太郎")
  }

  // http://127.0.0.1:8888/hello
  get("/hello") { request: Request =>
    response.ok.body("<a href='http://127.0.0.1:8888/hello'>Hello</a>")

  }

  // http://127.0.0.1:8888/users/xxxxxx
  get("/users/:id") { request: Request =>
    "You looked up " + request.params("id")
  }

  // http://127.0.0.1:8888/files/xxx/xxxx/xxxx
  get("/files/:*") { request: Request =>
    request.params("*")
  }

  // http://127.0.0.1:8888/admin/finatra/where NG
  // http://127.0.0.1:9990/admin/finatra/where OK
  get("/admin/finatra/where") { request: Request =>
    response.ok.body("where is here?")
  }

  // http://127.0.0.1:8888/users?max=10&start_date=2014-05-30TZ&verbose=true
  get("/users") { request: UsersRequest =>
    info(request)
    request
  }

  get("/foo") { request: Request =>
    response.
      ok.
      header("a", "b").
      json("""
    {
      "name": "Bob",
      "age": 19
    }
           """)
  }

  post("/post") { request: Request =>
    response.
    ok.
      json("""
    {
      "id": "111"
    }
           """)

  }

  post("/multipartParamsEcho") { request: Request =>
    RequestUtils.multiParams(request).keys
  }
}

case class UsersRequest(
                         @QueryParam max: Int,
                         @QueryParam startDate: String,
                         @QueryParam verbose: Boolean = false)

