# MAFPJ
MAF Parser in Java


Code by Marten Jaeger, extracted from a larger project by Peter Robinson, and slightly modified.
Still needs TLC
To run it clone the project and cd into it. Download a MAF file (suggestion: get chrM.maf.gz from
http://hgdownload.soe.ucsc.edu/goldenPath/mm9/multiz30way/maf/).



```aidl
$ mvn package
$ java -jar target/MafPj.jar /path/to/chrM.maf.gz

```
