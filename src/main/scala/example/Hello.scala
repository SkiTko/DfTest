package example


import akka.NotUsed
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._


object Hello {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val dfRepo = new DfRepo
    val dfInsertActor = system.actorOf(DfInsertActor.props(16, dfRepo), "DfInsertActor")

    val src = scala.io.Source.fromFile("おっきい.txt", "UTF-8")

    // DF 値を集計するので、1文書に同じ単語が複数現れないようにするため、toSet する。
    val lines = src.getLines().flatMap(_.split(" ").toSet)
    val source: Source[String, NotUsed] = Source(lines.toStream)

    // ask のタイムアウト。念のため長めにとっておく。
    implicit val askTimeout = Timeout(24.hours)
    val graph = source.mapAsync(16) { word =>
      dfInsertActor ? word
    }

    val task = graph.runWith(Sink.ignore)

    Await.result(task, Duration.Inf)
    system.terminate()
    src.close()
  }
}
