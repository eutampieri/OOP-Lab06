package it.unibo.oop.lab.exception2;

public final class WrongAccountHolderException extends IllegalArgumentException {

	private static final long serialVersionUID = 7090181622478672333L;
	/**
     * 
     * @return the string representation of instances of this class
     */
    @Override
    public String toString() {
        return "Wrong account holder";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }


}
