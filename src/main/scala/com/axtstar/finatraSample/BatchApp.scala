package com.axtstar.finatraSample

import java.net.URL

import com.twitter.finagle.http.{Method, Request, RequestBuilder}
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.io.Buf
import com.twitter.util.{Promise, Await, Future}

object Client extends App {
  override def main(args: Array[String]): Unit = {
    val x = FinagleClient.getJson("山田太郎")
    var body = ""
    var ok = -1
    x.foreach(k => {
      ok = k.status.code
      body = k.getContentString()
    })
    println(s"ok:${ok}")
    println(s"JSON:${body}")
  }
}

object FinagleClient {
 def getJson(name:String): Future[http.Response] ={
   val returnVal = new Promise[String]
   val client: Service[http.Request, http.Response] = Http.newService("localhost:8888")
   // put
   val content = s"name=${name}"
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
