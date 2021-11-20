import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.math._

val spark = SparkSession.builder()
  .master("local[*]")
  // имя приложения в интерфейсе спарка
  .appName("made-demo")
  .getOrCreate()


val df = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .option("sep", ",")
  .csv("tripadvisor_hotel_reviews.csv")

val  doc_num = df.count()

printf("%d", doc_num )

val removeChars = functions.udf { (s: String) => {
  s.replaceAll("[^0-9a-zA-Z\\s$]", "")
}
}


val df1 = df.select(removeChars(col("Review")).as("Review_new"))//, removeChars(col("Review")))

df1.show

val df2 = df1
  .select(lower(col("Review_new")).as("Review lower")) //.drop("name")
  .select(split(col("Review lower")," ").as("Words_list")) //.drop("name")
  .withColumn("doc_id", monotonically_increasing_id())


df2.show

val df3 = df2.withColumn("Words_list", explode(df2.col("Words_list")))

//val df4 = df3
//
val tf = df3.groupBy("doc_id" , "Words_list")
    .agg(count("doc_id") as "num_word1")

//num_word.show

val words_in_doc = df3.groupBy("doc_id")
  .agg(count("doc_id") as "words_in_doc1")

//words_in_doc.show

tf.show
//val tf1 = tf

val tf1 = tf
  .join(words_in_doc, Seq("doc_id"), "inner")
  .withColumn("tf", col("num_word1") / col("words_in_doc1"))

val dff = df3.groupBy("Words_list")
  .agg(countDistinct("doc_id") as  "df")

val dff_new = dff.orderBy(desc("df")).limit(100)

dff_new.show

def calcIdf  (d : Int) : Double = {
   log((doc_num + 1)/(d + 1))
}
val calcIdfUdf = functions.udf { (d: Int) => calcIdf(d) }

val dff_new1 = dff_new.withColumn("idf", calcIdfUdf(col("df")).cast("Double"))


dff_new1.show

val all = tf1
  .join(dff_new1, Seq("Words_list"), "inner")
  .withColumn("tf_idf", col("tf") * col("idf"))
  .select(col("Words_list") as "word", col("tf_idf"))



all.show