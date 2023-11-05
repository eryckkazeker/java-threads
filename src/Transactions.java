public class Transactions {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        ConnectionPool connectionPool = new ConnectionPool();

        new Thread(new DatabaseAccessTask(connectionPool, manager)).start();
        new Thread(new DatabaseProcedureTask(connectionPool, manager)).start();
    }
}

class TransactionManager {
    public void begin() {
        System.out.println("Initiating transaction...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ConnectionPool {
    public String getConnection() {
        System.out.println("Getting connection...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "connection";
    }
}

class DatabaseAccessTask implements Runnable {

    private ConnectionPool connectionPool;
    private TransactionManager transactionManager;

    public DatabaseAccessTask(ConnectionPool connectionPool, TransactionManager transactionManager) {
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    @Override
    public void run() {
        synchronized(connectionPool) {
            System.out.println("pool lock acquired");
            connectionPool.getConnection();

            synchronized(transactionManager) {
                System.out.println("transaction mgr lock acquired");
                transactionManager.begin();
            }
        }        
    }

}

class DatabaseProcedureTask implements Runnable {

    private ConnectionPool connectionPool;
    private TransactionManager transactionManager;

    public DatabaseProcedureTask(ConnectionPool connectionPool, TransactionManager transactionManager) {
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    @Override
    public void run() {
        synchronized(transactionManager) {
            System.out.println("transaction mgr lock acquired");
            transactionManager.begin();
            synchronized(connectionPool) {
                System.out.println("pool lock acquired");
                connectionPool.getConnection();
            }   
        }
             
    }

}
