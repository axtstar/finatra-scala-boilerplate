# finatraSample

[![Build Status](https://travis-ci.org/axtstar/finatraSample.svg?branch=master)](https://travis-ci.org/axtstar/finatraSample)

#motivation
Finatra is some kind of api server for microservices.
I'm very excited in founding twitter's finatra!

It's based on Finagle, RPC high concurrecncy system behind.

So I tried to write some sample code in order to comprehending them.

#environment

sbt 0.13.8 or later

scala 2.11

#how to compile
This sample contains two main classes, so you have to select one main class like the below.

//compile

>sbt assembly

//to start server

>java -cp target/scala-2.11/finatraSample-assembly-0.0.1-SNAPSHOT.jar com.axtstar.finatraSample.ExampleServerMain

You can access locally to the server at <a href='http://localhost:8888/foo'>http://localhost:8888/foo</a>, then get the JSON.

   {
      "name": "Bob",
      "age": 19
    }

//to start client

>java -cp target/scala-2.11/finatraSample-assembly-0.0.1-SNAPSHOT.jar com.axtstar.finatraSample.Client

The client access to <a href='http://localhost:8888/post'>http://localhost:8888/post</a> by POST method and get the JSON.

ok:200
JSON:
             {
             "result": "ok",
             "name": "山田太郎さんこんにちは"
             "fortune": ""
             }
