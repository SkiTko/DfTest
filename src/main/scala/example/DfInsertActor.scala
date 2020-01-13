package example

import akka.actor.{Actor, Props}
import akka.routing.RoundRobinPool

/**
  * Created by takao on 17/02/26.
  */
class DfInsertActor(dfRepo: DfRepo) extends Actor {

  override def receive: Receive = {
    case word: String =>
      // MongoDB に upsert する。
      dfRepo.upsert(word)

      // 戻り値は適当
      sender() ! word
  }
}


object DfInsertActor {
  // Actor の Props を生成する
  def props(parallelism: Int, dfRepo: DfRepo): Props =
    RoundRobinPool(parallelism).props(Props(classOf[DfInsertActor], dfRepo))
}