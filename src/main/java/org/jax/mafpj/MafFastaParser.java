package org.jax.mafpj;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * * The MAFparser will parse a MAF file obtained from UCSC in either text of gzipped format.
 * The format should be in the following form:<br><br>
 * <code>
 * ##maf version=1 scoring=autoMZ.v1<br>
 * a score=15057.000000<br>
 * s hg19.chrM                   0 187 + 16571 GATCACAGGTCTATCACCCT...<br>
 * s gorGor1.Supercontig_0439211 0 184 +   616 GATCACAGGTCTATCACCCT...<br>
 * q gorGor1.Supercontig_0439211               57889999999999999999...<br>
 * i gorGor1.Supercontig_0439211 N 0 I 21<br>
 * </code>
 * @author mjaeger
 * @author mjaeger
 *
 */
public class MafFastaParser  {
    private static final Logger logger = LogManager.getLogger();
    private final static int UCSC_ID = 1;
    private final static int ASSEMBLY = 2;
    private final static int CHROM = 5;
    private final static int STRAND = 6;
    private Pattern mafBlockPat	= Pattern.compile("^a\\sscore=([\\-\\d.])+$\n(^s.+\n)((^s.+\n)(^q.+\n)?(^i.+\n)?)+(e.+\n)*");
    private File	file;
    private boolean	isGziped;

    protected BufferedReader bufferedReader;
    protected boolean	isFirstBlock	= true;
    protected MafBlock	block;

    private Pattern fastaBlockPat = Pattern.compile("^>(uc[a-z0-9\\.]+)_([A-Za-z0-9]+)[_\\d]+( [\\d]+){3} (([^\\s]+:[\\d]+-[\\d]+)([\\+-]))*$");
    private Pattern coordinatsPat = Pattern.compile("([^\\s]+):([\\d]+)-([\\d]+)$");
    private Matcher matcher;


    /**
     * @param filename
     */
    public MafFastaParser(String filename) {

        this.file = new File(filename);
        logger.trace("parsing file "+filename);
        init();
    }

    /**
     * @param file
     */
    public MafFastaParser(File file) {
        this.file=file;
        init();
    }

    /**
     * This will return the {@link MafBlock} containing the information
     * @return
     */
    public MafBlock getBlock() {
        return block;
    }

    private void init(){
        if(file.getName().endsWith(".gz"))
            this.isGziped	= true;
        try {
            if(this.isGziped)
                this.bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(this.file))));
            else
                this.bufferedReader = new BufferedReader(new FileReader(this.file));
        } catch (FileNotFoundException e) {
            this.logger.fatal("Failed - no file found: "+this.file.getName());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//    	this.block	= new MAFblock();
    }
    public void terminate(){
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public boolean parseBlock() {
        boolean hasnext = false;
        boolean refSet = false;
        this.block	= new MafBlock();
//		 int c = 0;
        String line;
        MafLine mline;
        logger.trace("parsing block top ");
        try {
            // as long as there is no empty line or file end read the block
            while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(" !"+this.line+"!");
                 if ( line.length() == 0 ) continue;
                logger.trace("line="+ line);
                matcher = fastaBlockPat.matcher(line);
                // If this is a
                if (matcher.matches()) {
                    mline = new MafLine();
                    mline.setRef_assembly(matcher.group(ASSEMBLY));
//					if (!refSet)
                    mline.setRef_id(matcher.group(UCSC_ID));
                    if (matcher.group(4) != null) {
                        mline.setStrand(matcher.group(STRAND).equals("+") ? true
                                : false);

                        matcher = coordinatsPat.matcher(matcher.group(CHROM));
                        if (matcher.matches()) {
                            //TODO check if the start and end should be reversed for Strand '-'
                            //TODO check the index!!!
                            mline.setChromosom(matcher.group(1));
                            mline.setStart(Integer.parseInt(matcher.group(2)));
                            mline.setEnd(Integer.parseInt(matcher.group(3)));
                        }
                    }
                    line = bufferedReader.readLine();
                    mline.setSequence(line.toUpperCase());
//					System.out.println(line.toString());
                    if(!refSet){
                        refSet	= true;
                        this.block.setRef(mline);
                    }else
                        this.block.addLine(mline);
                }
            }

			/* now skip the following empty lines and check for file end */
            bufferedReader.mark(100);
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() != 0) {
                    bufferedReader.reset();
                    hasnext = true;
                    break;
                }
                bufferedReader.mark(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//		System.out.println(this.block.toString());
        return hasnext;
    }



}
