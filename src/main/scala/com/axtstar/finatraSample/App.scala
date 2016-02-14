package com.axtstar.finatraSample

import com.twitter.finagle.{SimpleFilter, Service}
import com.twitter.finatra.http.request.RequestUtils
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.HttpServer
import com.twitter.finagle.http.{Response, Request}
import com.twitter.finatra.http.Controller
import javax.inject.Inject

import com.twitter.finatra.request.FormParam
import com.twitter.inject.requestscope.FinagleRequestScope
import com.twitter.util.Future

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  // override default behaviour
  override val disableAdminHttpServer = false

  override def configureHttp(router: HttpRouter): Unit = {
    router
      // filter
      .filter[UserFilter]
      // controller
      .add[ExampleController]
  }
}

class UserFilter @Inject()(
                            requestScope: FinagleRequestScope)
  extends SimpleFilter[Request, Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    println(request)
    service(request)
  }
}
// Controller
class ExampleController extends Controller {

  post("/post") { request: UsersRequest =>
    response.
      ok.
      json(s"""
             {
             "result": "ok",
             "name": "${request.name}さんこんにちは"
             "fortune": "${FortuneClient.getFortune}"
             }
           """)
  }

  // http://127.0.0.1:8888/fortune
  get("/fortune") { request: Request =>
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

  post("/multipartParamsEcho") { request: Request =>
    RequestUtils.multiParams(request).keys
  }
}

case class UsersRequest(@FormParam name: String)

