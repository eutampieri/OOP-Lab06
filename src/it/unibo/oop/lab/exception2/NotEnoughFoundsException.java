package it.unibo.oop.lab.exception2;

public final class NotEnoughFoundsException extends IllegalStateException {

	private static final long serialVersionUID = -5668302102433109394L;
	/**
     * 
     * @return the string representation of instances of this class
     */
    @Override
    public String toString() {
        return "This account doen't hold enough money";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

}
