
  import org.apache.spark.sql.{DataFrame, Dataset, Encoders, Row, SaveMode, SparkSession}
  import org.apache.spark.sql.cassandra._
  import org.apache.spark.sql.types.StructType
  import org.elasticsearch.spark.sql._

  object ScalaApp {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local")
      .appName("Hello Spark App")
      .config("spark.cassandra.connection.port","9042")
      .config("spark.cassandra.connection.host","localhost")
      .config("spark.es.nodes","localhost")
      .config("spark.es.port","9200")
      .config("spark.es.nodes.wan.only", "true")
      .getOrCreate()

    import spark.implicits._

    def main(args: Array[String]): Unit = {

      val schema =  Encoders.product[NetflixTitle].schema
      val data = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .schema(schema)
        .csv(ClassLoader.getSystemResource("netflix_titles.csv").getFile)
        .as[NetflixTitle]

//      data.filter($"release_year" > 100L)
//      data.filter(it => it.release_year > 100L)


      saveCassandra(data)

      saveEs(data.toDF())

      val esData = getEsData(spark, schema)
      esData.show(10)


      Thread.sleep(1000000)
    }

    def saveCassandra(df: Dataset[NetflixTitle]): Unit = {
      df
        .write
        .cassandraFormat(table = "netflix", keyspace = "test")
        .mode(SaveMode.Append)
        .save()
    }

      def saveEs(df: DataFrame): Unit = {
        df.saveToEs("netflix")
      }

    def getEsData(spark: SparkSession, schema: StructType): Dataset[NetflixTitle] = {
      spark.read.format("es")
        .schema(schema)
        .load("netflix")
        .as[NetflixTitle]
    }
  }
