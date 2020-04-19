#!/usr/local/bin/amm
import $ivy.`com.github.tototoshi::scala-csv:1.3.6`
import com.github.tototoshi.csv._
import java.io._
import $ivy.`com.lihaoyi::requests:0.1.4`
import scala.util._

val reader = CSVReader.open("data/data-rs.csv")
val faskes = reader.allWithHeaders

val perJenis = faskes.groupBy(fs=>fs("jenis_rs"))
val jumlahPerJenis = perJenis.keys.map(k => (k, perJenis(k).size)).toSeq.sortBy(-_._2)
println(jumlahPerJenis.mkString("\n"))

def done(kode: String) = {
    new File(s"$kode.html").isFile || new File(s"$kode.err").isFile
}
faskes.foreach{ rs =>
   val kode = rs("kode_rs")
   if(done(kode)){
     println(rs("nama_rs") + " sudah diproses")
   } else {

     val tryBedmon = Try(requests.get(s"http://sirs.yankes.kemkes.go.id/integrasi/data/bed_monitor.php?satker=$kode"))

     if(tryBedmon.isSuccess){
       val bedmon = tryBedmon.get
       println(s"$kode ${rs("nama_rs")} ${bedmon.statusCode}")
       if(bedmon.statusCode == 200){
         val pw = new PrintWriter(new File(s"$kode.html"))
         pw.write(bedmon.text)
         pw.close
       } else {
         val pw = new PrintWriter(new File(s"$kode.err"))
         pw.write(bedmon.toString)
         pw.close
       }
     }else{
         val pw = new PrintWriter(new File(s"$kode.err"))
         pw.write("gak bisa direquests blas")
         pw.close
     }
   } 
}

