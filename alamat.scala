#!/usr/local/bin/amm
import $ivy.`com.github.tototoshi::scala-csv:1.3.6`
import $ivy.`com.lihaoyi::requests:0.1.4`
import $ivy.`com.lihaoyi::ujson:1.0.0`

import ujson._
import scala.util._
import com.github.tototoshi.csv._

val reader  = CSVReader.open("data/data-rs.csv")
val faskes  = reader.allWithHeaders

val okWriter  = CSVWriter.open("data/alamat.csv")
val errWriter  = CSVWriter.open("data/no-alamat.csv")

okWriter.writeRow(List("kode_rs", "nama_rs", "alamat_rs", "telepon_rs"))
faskes.foreach{ rs =>
   val kode = rs("kode_rs")
   val alamat =s"http://sirs.yankes.kemkes.go.id/sirsservice/sisrute/detailrs.php?kode=$kode"
   try {
     val rsdata = ujson.read(requests.get(alamat).text)(0)
     val row = List(kode, rs("nama_rs"), rsdata("Alamat").str.trim, rsdata("Telepon").str.trim)
     okWriter.writeRow(row)
     println(row.mkString(" "))
   } catch {
     case _ =>  errWriter.writeRow(kode :: rs("nama_rs") :: "N/A" :: Nil)
                println(f"${rs("nama_rs").trim}%-50s Ndak bisa diliat datanya")
   }
}

okWriter.close()
errWriter.close()


