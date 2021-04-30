
import org.apache.spark.sql.{DataFrame, Encoders, Row, SaveMode, SparkSession}
import org.apache.spark.sql.cassandra._
import com.datastax.spark.connector._
import org.apache.spark.sql
import org.apache.spark.sql.types.StructType
import org.elasticsearch.spark.sql._

object ScalaApp {


  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("Hello Spark App")
      .config("spark.cassandra.connection.port","9042")
      .config("spark.cassandra.connection.host","localhost")
      .config("spark.es.nodes","localhost")
      .config("spark.es.port","9200")
      .config("spark.es.nodes.wan.only", "true")
      .getOrCreate()

    val schema = Encoders.product[NetflixTitle].schema

    val data= spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .schema(schema)
      .csv("D:\\Solutis\\Solutis\\spark\\estud-spark\\src\\main\\scala\\netflix_titles.csv")

//    saveCassandra(data)

    saveEs(data)
//
//    val esData = getEsData(spark, schema)
//    esData.show(10)
  }

  def saveCassandra(df: DataFrame): Unit = {
    df
      .write
      .cassandraFormat(table = "netflix", keyspace = "test")
      .mode(SaveMode.Append)
      .save()
  }

  def saveEs(df: DataFrame): Unit = {
    df.saveToEs("netflix")
  }

  def getEsData(spark: SparkSession, schema: StructType):DataFrame = {
    spark.read.format("es")
      .schema(schema)
      .load("netflix")
  }
}
