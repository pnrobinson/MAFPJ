package org.jax.mafpj;

import java.util.ArrayList;


/**
 * A MAF block contains the data of one block section of the MAF file,
 * e.g. the score, ref and aligned sequences.<br><br>
 * <code>
 * a score=15057.000000<br>
 * s hg19.chrM                   0 187 + 16571 GATCACAGGTCTATCACCCT...<br>
 * s gorGor1.Supercontig_0439211 0 184 +   616 GATCACAGGTCTATCACCCT...<br>
 * q gorGor1.Supercontig_0439211               57889999999999999999...<br>
 * i gorGor1.Supercontig_0439211 N 0 I 21<br>
 * <br>
 * alternative:<br><br>
 *
 * >uc007efa.1_mm9_1_18 58 0 1 chr1:197002747-197002804-<br>
 * ATGGGATCCTTGGGTTCGCTCTGGGTTTTCTTCACTCTCATCACTCCAGGAGTTCTTG<br>
 * >uc007efa.1_rn4_1_18 58 0 1 chr13:111113553-111113610-<br>
 * ATGGGAGCCTTGGGTTCGCTCTGGGTTTTCTTCGCTCTCATCGCTCCGGGAGTTCTTG<br>
 * >uc007efa.1_cavPor2_1_18 58 0 1<br>
 * ----------------------------------------------------------<br>
 * >uc007efa.1_oryCun1_1_18 58 0 1 scaffold_60540:997-1054-<br>
 * ATGGGCGCCGCGGGTCCGCTCTGGGTTTTCTTGGCCGTCCTCGCGCCAGGGGTCTTCG<br>
 * >uc007efa.1_hg18_1_18 58 0 1 chr1:205694387-205694444+<br>
 * ATGGGCGCCGCGGGCCTGCTCGGGGTTTTCTTGGCTCTCGTCGCACCGGGGGTCCTCG<br>
 * </code>
 * @author mjaeger
 *
 */
public class MafBlock {

    private MafLine ref;
    private ArrayList<MafLine> lines;

    private int score;

    /**
     * Returns the MAF line with the reference info.
     * @return the ref
     */
    public MafLine getRef() {
        return ref;
    }

    /**
     * Returns the MAF line with the aligned sequence info.
     * @return the lines
     */
    public ArrayList<MafLine> getLines() {
        return lines;
    }

    /**
     * Returns the score for this alignment block.
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the reference MAF line.
     * @param reference the ref to set
     */
    public void setRef(MafLine reference) {
        this.ref = reference;
    }


    /**
     * Sets the score for this alignment block.
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Adds a {@link MafLine} to the list of aligned lines.
     * @param line the {@link MafLine} to be added
     */
    public void addLine(MafLine line){
        if(this.lines == null)
            this.lines	= new ArrayList<MafLine>();
        this.lines.add(line);
    }

    @Override
    public String toString() {
        StringBuffer blockString	= new StringBuffer(this.ref.toString());
        for (MafLine line : this.lines) {
            blockString.append("\n"+line.toString());
        }
        return blockString.toString();
    }

    /**
     * This will append the data of block2 to the current block if the Ref_ID matches.
     * @param block2
     * @throws InvalidReferenceException
     * @throws IllegalMafArgumentException
     */
    public void append(MafBlock block2) throws InvalidReferenceException, IllegalMafArgumentException {
        if(!this.ref.getRef_id().equals(block2.getRef().getRef_id())){
            throw new InvalidReferenceException();
        }

        // reference
        this.ref.append(block2.getRef());
        // lines
        for(int i=0;i<this.getLines().size();i++){
            this.getLines().get(i).append(block2.getLines().get(i));
        }

    }

}
