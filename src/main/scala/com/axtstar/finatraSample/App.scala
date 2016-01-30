package com.axtstar.finatraSample

//import DoEverythingModule
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.HttpServer
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import javax.inject.Inject

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

  get("/hello") { request: Request =>
    response.ok.body("<a href='http://127.0.0.1:8888/hello'>Hello</a>")

  }
}

