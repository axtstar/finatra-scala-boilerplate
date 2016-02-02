import java.net.URL

import com.twitter.finagle.http.{Method, FormElement, Request, RequestBuilder}
import com.twitter.finagle.zipkin.host
import com.twitter.finagle.{Http, Service, Status}
import com.twitter.finagle.http
import com.twitter.finatra.request._
import com.twitter.finatra.validation._
import com.twitter.io.Buf
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http.{HttpHeaders, HttpRequest, HttpResponseStatus, DefaultHttpResponse}
import org.joda.time.DateTime
import org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer

object Client extends App {
  override def main(args: Array[String]): Unit = {
    val client: Service[http.Request, http.Response] = Http.newService("localhost:8888")
    // put


    val content = "max=10"
    val payload = content.getBytes("UTF-8")
    val request: Request =  RequestBuilder()
       .url(new URL("http://localhost:8888/post"))
       .setHeader("Content-Type","text/html")
       .setHeader("Content-Length",payload.length.toString)
       .setHeader("Accept", "*/*")
       .build(Method.Post,Option(Buf.Utf8(content)))

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




