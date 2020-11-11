package it.unibo.oop.lab.exception2;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;

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
    	BankAccount first = new StrictBankAccount(0, initialBalance, maxATMOps);
    	BankAccount second = new StrictBankAccount(1, initialBalance, maxATMOps);
    	
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
    	
    	for(int i = 0; i < maxATMOps - 1; i++) {
    		second.withdrawFromATM(1, 1);
    	}
    	
    	try {
    		second.withdrawFromATM(0, 10);
    		fail("This withdrawal shouldn't have succeded");
    	} catch(TransactionsOverQuotaException e) {
    		assertNotNull(e.getMessage());
    	}
    }
}
