package org.jax.mafpj;

/**
 * @author mjaeger
 *
 */
public class InvalidReferenceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 668323801577210847L;

    public InvalidReferenceException() {
        // TODO Auto-generated constructor stub
    }

    public InvalidReferenceException(String s, String s2) {
        super("The reference IDs do not match: "+s+" vs. "+s2);
    }

}