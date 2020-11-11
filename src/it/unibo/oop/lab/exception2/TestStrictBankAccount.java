package it.unibo.oop.lab.exception2;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test to test
 * {@link StrictBankAccount}.
 * 
 */
public final class TestStrictBankAccount {

    /**
     * Used to test Exceptions on {@link StrictBankAccount}.
     */
    @Test
    public void testBankOperations() {
        /*
         * 1) Creare due StrictBankAccountImpl assegnati a due AccountHolder a
         * scelta, con ammontare iniziale pari a 10000 e nMaxATMTransactions=10
         * 
         * 2) Effetture un numero di operazioni a piacere per verificare che le
         * eccezioni create vengano lanciate soltanto quando opportuno, cioè in
         * presenza di un id utente errato, oppure al superamento del numero di
         * operazioni ATM gratuite.
         */
    	final int initialBalance = 10_000;
    	final int maxATMOps = 10;
    	StrictBankAccount first = new StrictBankAccount(0, initialBalance, maxATMOps);
    	BankAccount second = new StrictBankAccount(1, initialBalance, maxATMOps);
    	
    	assertEquals(initialBalance, first.getBalance(), 1e-4);
    	assertEquals(initialBalance, second.getBalance(), 1e-4);
    	
    	try {
    		first.withdraw(0, 10_001);
    		fail("We succeded in withdrawing more money than we have!");
    	} catch(NotEnoughFoundsException e) {
    		assertNotNull(e.getLocalizedMessage());
    	}
    	try {
    		second.withdraw(0, 10);
    		fail("We succeded in withdrawing from another bank account!");
    	} catch(WrongAccountHolderException e) {
    		assertNotNull(e.getLocalizedMessage());
    	}
    	
    	try {
    		second.withdrawFromATM(1, 10);
    	} catch(Exception e) {
    		fail("This withdrawal should have succeded");
    	}
    	
    	try {
    		second.withdrawFromATM(1, initialBalance + 1);
    		fail("We were able to overdraft");
    	} catch(NotEnoughFoundsException e) {
    		assertNotNull(e.getMessage());
    	}

    	
    	for(int i = 0; i < maxATMOps - 1; i++) {
    		second.withdrawFromATM(1, 1);
    	}
    	
    	try {
    		second.withdrawFromATM(1, 10);
    		fail("This withdrawal shouldn't have succeded");
    	} catch(TransactionsOverQuotaException e) {
    		assertNotNull(e.getMessage());
    	}
    	assertEquals(maxATMOps, second.getTransactionCount());

    	
    	try {
    		second.depositFromATM(1, 10);
    		fail("This deposit shouldn't have succeded");
    	} catch(TransactionsOverQuotaException e) {
    		assertNotNull(e.getMessage());
    	}
    	
    	assertEquals(maxATMOps, second.getTransactionCount());
    	
    	try {
    		first.withdraw(0, first.getBalance());
    	} catch(Exception e) {
    		fail("This withdrawal should have been successful! Instead we got " + e.getLocalizedMessage());
    	}
    	
    	try {
    		first.computeManagementFees(0);
    		fail("In theory the account is empty so the fees cannot be deducted");
    	} catch (NotEnoughFoundsException e) {
    		assertNotNull(e.getLocalizedMessage());
    	}
    	
    	try {
    		first.depositFromATM(0, 50);
    	} catch(Exception e) {
    		fail("This deposit should have been successful! Instead we got " + e.getLocalizedMessage());
    	}
    	
    	try {
    		first.depositFromATM(1, 50);
    		fail("The user id doesn't match so we're donating money! Yay!");
    	} catch (WrongAccountHolderException e) {
    		assertNotNull(e.getLocalizedMessage());
    	}
    	
    	try {
    		first.computeManagementFees(1);
    		fail("The user id doesn't match so this shouldn't happen!");
    	} catch (WrongAccountHolderException e) {
    		assertNotNull(e.getLocalizedMessage());
    	}
    	
    	try {
    		first.computeManagementFees(0);
    	} catch (Exception e) {
    		fail("This operation shold have succeded! Instead, we've got " + e);
    	}
    }
}
