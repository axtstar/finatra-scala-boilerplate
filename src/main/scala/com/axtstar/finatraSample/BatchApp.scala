import java.net.URL

import com.twitter.finagle.http.{Method, Request, RequestBuilder}
import com.twitter.finagle.{Http, Service, Status}
import com.twitter.finagle.http
import com.twitter.finatra.request._
import com.twitter.io.Buf
import com.twitter.util.{Promise, Await, Future}
import scalax.io._

object Client extends App {
  override def main(args: Array[String]): Unit = {
    val x = Client1.getJson("")
    var r = ""
    x.foreach(k => r=k.encodeString())
    println("here:" + r)
  }
}

object Client1 extends App {
 def getJson(x:String): Future[http.Response] ={
   val returnVal = new Promise[String]
   val client: Service[http.Request, http.Response] = Http.newService("localhost:8888")
   // put
   val content = "max=10"
   val payload = content.getBytes("UTF-8")
   val request: Request =  RequestBuilder()
     .url(new URL("http://localhost:8888/post"))
     .setHeader("User-Agent","myCrwaler")
     .setHeader("Content-Type","application/x-www-form-urlencoded")
     .setHeader("Content-Length",payload.length.toString)
     .setHeader("Accept", "*/*")
     .build(Method.Post,Option(Buf.Utf8(content)))

   val response: Future[http.Response] = client(request)
   response.onSuccess { resp: http.Response =>
     resp.getStatusCode() match {
       case 200 => {
         returnVal.setValue(resp.encodeString())
       }
       case _ =>
         returnVal.setValue(resp.encodeString())
     }

   }.onFailure { cause:Throwable =>
     returnVal.setException(cause)
   }
   Await.ready(response)
 }
}
