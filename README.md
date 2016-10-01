# finatraSimpleSample

#Motivation

Finatra is some kind of api server for microservices.
I'm very excited in be found twitter's Finatra!

It's based on Finagle, RPC high concurrecncy system behind.

So I tried to write some simple sample code in order to comprehending them.

#Environment

sbt 0.13.8 or later
scala 2.11

#How to compile
This sample contains two main classes, so you have to select one main class like the below.

compile

```scala
sbt assembly
```

to start server

```bash
java -cp target/scala-2.11/finatraSimpleSample-assembly-0.0.1.jar com.axtstar.finatraSimpleSample.ExampleServerMain
```

You can access locally to the server at <a href='http://localhost:8888/hello?name=Bob&age=19'>http://localhost:8888/hello?name=Bob&age=19</a>, then get the JSON below.

```
   {
      "name": "Bob",
      "age": 19
    }
```
