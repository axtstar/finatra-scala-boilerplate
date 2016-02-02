import com.twitter.finagle.{Http, Service, Status}
import com.twitter.finagle.http
import com.twitter.finatra.request._
import com.twitter.finatra.validation._
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, DefaultHttpResponse}
import org.joda.time.DateTime

object Client extends App {
  override def main(args: Array[String]): Unit = {
    val client: Service[http.Request, http.Response] = Http.newService("localhost:8888")
    // get
    val requestGet = http.Request(http.Method.Get, "/hello")
    requestGet.host = "localhost"
    val responseGet: Future[http.Response] = client(requestGet)

    responseGet.onSuccess{ respGet:http.Response =>
      println(respGet)
      respGet
    }
    Await.ready(responseGet)

    // put
    val request = http.Request(http.Method.Get, "/users")
    request.host = "localhost"
    val userRequest = new UsersRequest(10,"test",false)
    request.setContentString(userRequest.toString)
    val response: Future[http.Response] = client(request)
    response.onSuccess { resp: http.Response =>
      resp.status match {
        case HttpResponseStatus.OK =>
          println("GET success: " + resp)
          println("GET success: " + resp.getContentString())
        case _ =>
          println("GET code: " + resp.status)
      }

    }.onFailure { cause:Throwable =>
      println("GET failed: " + cause)
    }
    // put


    Await.ready(response)
  }
}

object ScalaClient extends App {
  val client: Service[http.Request, http.Response] = Http.newService("www.scala-lang.org:80")
  val request = http.Request(http.Method.Get, "/")
  request.host = "www.scala-lang.org"
  val response: Future[http.Response] = client(request)
  response.onSuccess { resp: http.Response =>
    println("GET success: " + resp)
  }
  Await.ready(response)
}

case class UsersRequest(
                         @QueryParam max: Int,
                         @QueryParam startDate: String,
                         @QueryParam verbose: Boolean = false)




