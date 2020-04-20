Tools
-

Install Scala scripting tool from @lihaoyi
http://ammonite.io/

Check address/bedmonitor:
```
amm address.scala
amm bedmonitor.scala
```
Contoh hasil data:
- https://github.com/wibisono/cekdata/blob/master/data/bedmonitor.csv
- https://github.com/wibisono/cekdata/blob/master/data/alamat.csv

Data rumah sakit (data-rs.csv) perlu manual clean up dari datars.php karena formatnya tab separated file tapi ada inkonsistensi penggunaan tab dan quote.

Yankes links
-

- data-rs.csv => http://sirs.yankes.kemkes.go.id/fo/json/datars.php
- bedmonitor => http://sirs.yankes.kemkes.go.id/integrasi/data/bed_monitor.php?satker=3172013
- alamat => http://sirs.yankes.kemkes.go.id/sirsservice/sisrute/detailrs.php?kode=1103010


Kesimpulan Sementara
-

Dari 2900 [rumah sakit](https://github.com/wibisono/cekdata/blob/master/data/data-rs.csv) ada 1388 yang memiliki data [bed monitoring](https://github.com/wibisono/cekdata/blob/master/data/bedmonitor.csv) dan 1249 yang memiliki data [alamat](https://github.com/wibisono/cekdata/blob/master/data/alamat.csv)
