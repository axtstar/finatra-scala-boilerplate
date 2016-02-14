package com.axtstar.finatraSample

import com.twitter.inject.server.TwitterServer
import org.mockito.Mockito
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

/**
  * Created by axt on 2016/02/14.
  */
class AppTest extends FeatureTest {
  override val server = new EmbeddedHttpServer(new ExampleServer)

  "test feature 1" should {

    "respond accordingly" in {
      server.httpGet(
        path = "/name",
        andExpect = Status.Ok)
    }
  }
}
