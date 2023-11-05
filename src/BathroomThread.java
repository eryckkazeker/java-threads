public class BathroomThread {
    public static void main(String[] args) {
        BathRoom bathroom = new BathRoom();

        new Thread(new Runnable() {
            @Override
            public void run() {
                bathroom.pee();                
            }
        }, "Pedro").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                bathroom.poop();                
            }
        }, "Joao").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                bathroom.pee();                
            }
        }, "Maria").start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                bathroom.poop();                
            }
        }, "Ana").start();

        Thread limpeza = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    bathroom.clean();
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                                
            }
        }, "Cleaner");

        limpeza.setDaemon(true);
        limpeza.start();
        
    }

}

class BathRoom {
    private boolean isDirty = true;
    public void pee() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+ " knocks on door");

        synchronized(this){
            System.out.println(threadName+ " enters bathroom");

            while(isDirty) {
                waitOutside(threadName);
            }
            System.out.println(threadName+ " pees...");
            this.isDirty = true;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(threadName+ " washes hand");
            System.out.println(threadName+ " leaves bathroom");
        }
        
    }

    public void poop() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+ " knocks on door");

        synchronized(this){
            System.out.println(threadName+ " enters bathroom");
            while(isDirty) {
                waitOutside(threadName);
            }
            System.out.println(threadName+ " poops...");
            this.isDirty = true;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(threadName+ " washes hand");
            System.out.println(threadName+ " leaves bathroom");
        }
    }

    public void clean() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+ " knocks on door");
        synchronized(this){
            System.out.println(threadName+ " enters bathroom");
            if(!this.isDirty) {
                System.out.println(threadName+ " - bathroom is clean");
                return;
            }
            System.out.println(threadName+ " cleans bathroom");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            this.isDirty = false;
            System.out.println(threadName+ " leaves bathroom");
            this.notifyAll();

        }
    }

    private void waitOutside(String threadName) {
        System.out.println(threadName+ " eeew, the bathroom is dirty");
        try {
            this.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
