name := "estud-spark"

version := "0.1"

scalaVersion := "2.12.3"

// spark version which fit with the app
val sparkVersion = "3.0.0"

// managed dependencies (ivy, maven, etc.)
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.datastax.spark" % "spark-cassandra-connector_2.12" % "3.0.1",
  "org.apache.spark" %% "spark-catalyst" % "3.0.2",
  "joda-time" % "joda-time" % "2.10.10",
  "org.elasticsearch" % "elasticsearch-spark-30_2.12" % "7.12.1",
  "org.apache.httpcomponents" % "httpclient" % "4.5.13"
)
