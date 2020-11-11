package it.unibo.oop.lab.exception2;

public final class TransactionsOverQuotaException extends IllegalStateException {

	private static final long serialVersionUID = -3009288257950124390L;

	/**
     * 
     * @return the string representation of instances of this class
     */
    @Override
    public String toString() {
        return "Transaction quota exceeded";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

}
