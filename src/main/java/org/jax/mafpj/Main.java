package org.jax.mafpj;

public class Main {


    // I went here  http://hgdownload.soe.ucsc.edu/goldenPath/mm9/multiz30way/maf/
    // chrM.maf.gz
    // run program as java -jar target/MafPj /home/robinp/data/chrM.maf.gz
    // obviously adjust path
    public static void main(String[] args) {
        String pathToMaf=args[0];
        MafFastaParser mfp = new MafFastaParser(pathToMaf);
        boolean test;
        while (test = mfp.parseBlock()) {
            System.out.println(test);
            System.out.println(mfp.block.toString());
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        System.out.println(test);
        System.out.println(mfp.block.toString());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        mfp.terminate();
    }
}
