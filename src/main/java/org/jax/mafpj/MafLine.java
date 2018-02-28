package org.jax.mafpj;





/**
 * A MAF line contains the data of one alignment in a MAF block
 * e.g. the ref, start, stop, strand, ref length, aligned sequences, alignment score, ...<br><br>
 * <code>
 * s gorGor1.Supercontig_0439211 0 184 +   616 GATCACAGGTCTATCACCCT...<br>
 * q gorGor1.Supercontig_0439211               57889999999999999999...<br>
 * i gorGor1.Supercontig_0439211 N 0 I 21<br>
 * </code>
 * @author mjaeger
 *
 */
public class MafLine {
    private String ref_assembly;
    private String ref_id;

    private String chromosom;
    private int start		= -1;
    private int end			= -1;
    private boolean strand;
//	private int ref_length;

    private StringBuffer	sequence;
    private StringBuffer	algn_quality;

    /**
     * Returns the Chromosom id (e.g chr1, supercontig1234)
     * @return the chromosom
     */
    public String getChromosom() {
        return chromosom;
    }
    /**
     * Stes the Chromosom id (e.g chr1, supercontig1234)
     * @param chromosom the chromosom to set
     */
    public void setChromosom(String chromosom) {
        this.chromosom = chromosom;
    }
    /**
     * Returns the reference assembly (eg. mm9, gorgor1)
     * @return the ref_assembly
     */
    public String getRef_assembly() {
        return ref_assembly;
    }
    /**
     * Sets the reference assembly (eg. mm9, gorgor1)
     * @param ref_assembly the ref_assembly to set
     */
    public void setRef_assembly(String ref_assembly) {
        this.ref_assembly = ref_assembly;
    }
    /**
     * Returns the reference id (e.g. )
     * @return the ref_id
     */
    public String getRef_id() {
        return ref_id;
    }
    /**
     * Sets the reference id (e.g. )
     * @param ref_id the ref_id to set
     */
    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }
    /**
     * Returns the start position in the block.
     * @return the start
     */
    public int getStart() {
        return start;
    }
    /**
     * Sets the start position in the block.
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }
    /**
     * Returns the end position in the block.
     * @return the end
     */
    public int getEnd() {
        return end;
    }
    /**
     * Sets the end position in the block.
     * @param end the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }
    /**
     * Returns the strand of the aligned sequence (e.g. true = +, false = -)
     * @return the strand
     */
    public boolean isStrand() {
        return strand;
    }
    /**
     * Sets the strand of the aligned sequence (e.g. true = +, false = -)
     * @param strand the strand to set
     */
    public void setStrand(boolean strand) {
        this.strand = strand;
    }
    /**
     * Returns the sequence of the block.
     * @return the sequence
     */
    public StringBuffer getSequence() {
        return sequence;
    }
    /**
     * Returns the sequence of the block.
     * @param sequence the sequence to set
     */
    public void setSequence(StringBuffer sequence) {
        this.sequence = sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = new StringBuffer(sequence);
    }
    /**
     * @return the algn_quality
     */
    public CharSequence getAlgn_quality() {
        return algn_quality;
    }
    /**
     * @param algn_quality the algn_quality to set
     */
    public void setAlgn_quality(StringBuffer algn_quality) {
        this.algn_quality = algn_quality;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ">" +ref_assembly +
                "\t" + ref_id +
                "\t" + chromosom +
                ":" + start +
                "-" + end +
                "(" + (strand ? "+" : "-")+")"+
                "\n" + sequence +
                (algn_quality == null ? "" : "\n" + algn_quality);
    }

    /**
     * This will append the data from line2 to the current {@link MafLine}.
     * @param line2
     * @throws IllegalMafArgumentException
     */
    public void append(MafLine line2) throws IllegalMafArgumentException{
        // ref_id
        if(!this.ref_id.equals(line2.ref_id))
            throw new IllegalMafArgumentException(this.ref_id+" != "+line2.ref_id);
        // ref_assembly
        if(!this.ref_assembly.equals(line2.ref_assembly))
            throw new IllegalMafArgumentException(this.ref_assembly+" != "+line2.ref_assembly);
        // chromosom
//		if(this.chromosom != null && !this.chromosom.equals(line2.chromosom))
//			throw new IllegalMAFarguments(this.chromosom+" != "+line2.chromosom);
        // strand
//		System.out.println(line2);
//		if(this.strand ^ line2.strand){
//			throw new IllegalMAFarguments(this.strand+" != "+line2.strand);
//		}
        // start
        if(this.start == -1 & line2.start != -1)
            this.start = line2.start;
        // end
        if(this.end < line2.end)
            this.end	= line2.end;
        // sequence
        this.sequence.append(line2.sequence);



    }


}
