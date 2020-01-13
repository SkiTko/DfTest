package example

import com.mongodb.casbah.Imports._


/**
  * Created by takao on 17/02/26.
  */
class DfRepo {

  private[this] val mongoClient = MongoClient("127.0.0.1", 27017)
  private[this] val mongoDB = mongoClient("df")


  def upsert(word: String): Unit = {
    val coll = mongoDB("df")
    coll.update(
      MongoDBObject("word" -> word),
      $inc("df" -> 1),
      upsert = true)
  }
}
