package database;

public class TransactionManager {
    private boolean transactionActive = false;

    public void beginTransaction() {
        transactionActive = true;
        System.out.println("Transaction started.");
    }

    public void commit() {
        if (transactionActive) {
            transactionActive = false;
            System.out.println("Transaction committed.");
        }
    }

    public void rollback() {
        if (transactionActive) {
            transactionActive = false;
            System.out.println("Transaction rolled back.");
        }
    }

    public boolean isTransactionActive() {
        return transactionActive;
    }
}
