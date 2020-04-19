#!/usr/local/bin/amm
import $ivy.`com.github.tototoshi::scala-csv:1.3.6`
import $ivy.`com.lihaoyi::requests:0.1.4`
import $ivy.`com.lihaoyi::ujson:1.0.0`
import $ivy.`net.ruippeixotog::scala-scraper:2.2.0`

import ujson._
import scala.util._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import com.github.tototoshi.csv._

val reader  = CSVReader.open("data/data-rs.csv")
val faskes  = reader.allWithHeaders
val browser = JsoupBrowser()

val okWriter  = CSVWriter.open("data/verified.csv")
val errWriter  = CSVWriter.open("data/nodata.csv")


faskes.foreach{ rs =>
   val kode = rs("kode_rs")
   val bed_monitor = s"http://sirs.yankes.kemkes.go.id/integrasi/data/bed_monitor.php?satker=$kode"
   try {
     val rsdata = browser.get(bed_monitor) 
     val rows = rsdata >> element("tbody") >> elementList("tr") >> elementList("td") >> text("td")
     // Trying to get the last updated (excluding last row thus init)
     val updated = rows.init.map(_.last).toSeq.sorted.head
     val summary = (kode :: (rs("nama_rs").trim :: rows.last.tail)) :+ updated
     okWriter.writeRow(summary)
     println(f"""$kode ${rs("nama_rs").trim}%-50s ${summary.mkString(" ")} $updated """)
   } catch {
     case _ =>  errWriter.writeRow(kode :: rs("nama_rs") :: "N/A" :: Nil)
                println(f"${rs("nama_rs").trim}%-50s Ndak bisa diliat datanya")
   }
}

okWriter.close()
errWriter.close()


