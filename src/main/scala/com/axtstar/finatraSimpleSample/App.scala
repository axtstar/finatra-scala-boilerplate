package com.axtstar.finatraSimpleSample

import com.twitter.finagle.{SimpleFilter, Service}
import com.twitter.finatra.http.request.RequestUtils
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.HttpServer
import com.twitter.finagle.http.{Response, Request}
import com.twitter.finatra.http.Controller
import javax.inject.Inject

import com.twitter.finatra.request.{QueryParam, FormParam}
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
      // Get Method
      .add[GetController]
      // Post method
      .add[PostController]
  }
}

// Controller get
class GetController extends Controller {

  get("/hello") { request: UsersParam =>
    response.
      ok.
      json(s"""
    {
      "name": "${request.name}",
      "age": ${request.age}
    }
           """)
  }

  // http://127.0.0.1:8888/hello
  get("/helloHTML") { request: Request =>
    response.ok.body("<a href='http://127.0.0.1:8888/hello'>Hello?name=system&age=2</a>")

  }

  // http://127.0.0.1:8888/files/xxx/xxxx/xxxx
  get("/files/:*") { request: Request =>
    request.params("*")
  }
}

class PostController extends Controller{
  post("/hello") { request: UsersParam =>
    response.
      ok.
      json(s"""
             {
             "result": "ok",
             "name": "${request.name}(${request.age})さんこんにちは"
             }
           """)
  }

  post("/multipartParamsEcho") { request: Request =>
    RequestUtils.multiParams(request).keys
  }
}

case class UsersParam(@QueryParam name: String,
                      @QueryParam age:Int)

class UserFilter @Inject()(
                            requestScope: FinagleRequestScope)
  extends SimpleFilter[Request, Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    println(request)
    service(request)
  }
}
