package it.unibo.oop.lab.exception2;

import com.sun.tools.javac.util.MandatoryWarningHandler;

/**
 * Class modeling a BankAccount with strict policies: getting money is allowed
 * only with enough founds, and there are also a limited number of free ATM
 * transaction (this number is provided as a input in the constructor).
 * 
 */
public class StrictBankAccount implements BankAccount {

    private final int usrID;
    private double balance;
    private int totalTransactionCount;
    private final int maximumAllowedATMTransactions;
    private static final double ATM_TRANSACTION_FEE = 1;
    private static final double MANAGEMENT_FEE = 5;
    private static final double TRANSACTION_FEE = 0.1;

    /**
     * 
     * @param usrID
     *            user id
     * @param balance
     *            initial balance
     * @param maximumAllowedAtmTransactions
     *            max no of ATM transactions allowed
     */
    public StrictBankAccount(final int usrID, final double balance, final int maximumAllowedAtmTransactions) {
        this.usrID = usrID;
        this.balance = balance;
        this.maximumAllowedATMTransactions = maximumAllowedAtmTransactions;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void deposit(final int usrID, final double amount) throws WrongAccountHolderException{
        try {
        	this.checkUser(usrID);
            this.balance += amount;
            increaseTransactionsCount();
        } catch(WrongAccountHolderException e) {
        	throw e;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void withdraw(final int usrID, final double amount)throws WrongAccountHolderException, NotEnoughFoundsException {
    	try {
    		checkUser(usrID);
    		if (isWithdrawAllowed(amount)) {
    			this.balance -= amount;
    			increaseTransactionsCount();
    		} else {
    			throw new NotEnoughFoundsException();
    		}
    	} catch(WrongAccountHolderException e) {
        	throw e;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void depositFromATM(final int usrID, final double amount) throws WrongAccountHolderException, TransactionsOverQuotaException {
        try {
            this.deposit(usrID, amount - StrictBankAccount.ATM_TRANSACTION_FEE);
            increaseATMTransactionsCount();
        } catch(WrongAccountHolderException|
        		TransactionsOverQuotaException e) {
        	throw e;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void withdrawFromATM(final int usrID, final double amount) throws WrongAccountHolderException, NotEnoughFoundsException, TransactionsOverQuotaException {
        if (totalTransactionCount < maximumAllowedATMTransactions) {
        	try {
        		this.withdraw(usrID, amount + StrictBankAccount.ATM_TRANSACTION_FEE);
        		// Why don't we increase the transaction count here?
        		// It would prevent me from checking the transaction count!
        	} catch(WrongAccountHolderException|
        			NotEnoughFoundsException e) {
        		throw e;
        	}
        } else {
        	throw new TransactionsOverQuotaException();
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public int getTransactionCount() {
        return totalTransactionCount;
    }

    /**
     * 
     * @param usrID
     *            id of the user related to these fees
     */
    public void computeManagementFees(final int usrID) throws WrongAccountHolderException, NotEnoughFoundsException {
        final double feeAmount = MANAGEMENT_FEE + (totalTransactionCount * StrictBankAccount.TRANSACTION_FEE);
        try {
        	checkUser(usrID);
        	if (isWithdrawAllowed(feeAmount)) {
        		balance -= MANAGEMENT_FEE + totalTransactionCount * StrictBankAccount.TRANSACTION_FEE;
        		totalTransactionCount = 0;
        	} else {
        		throw new NotEnoughFoundsException();
        	}
        } catch(WrongAccountHolderException e) {
        	throw e;
        }
    }

    private void checkUser(final int id) throws WrongAccountHolderException {
        if (this.usrID != id) {
        	throw new WrongAccountHolderException();
        }
    }

    private boolean isWithdrawAllowed(final double amount) {
        return balance > amount;
    }
    
    private void increaseTransactionsCount() {
    	totalTransactionCount++;
    }
    private void increaseATMTransactionsCount() throws TransactionsOverQuotaException {
    	if (totalTransactionCount < maximumAllowedATMTransactions) {
    		this.increaseTransactionsCount();
    	} else {
    		throw new TransactionsOverQuotaException();
    	}
    }
}
