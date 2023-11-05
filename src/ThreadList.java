public class ThreadList {
    public static void main(String[] args) throws InterruptedException {
        MyList list = new MyList();

        for(int i = 0; i<10;i++) {
            new Thread(new AddElementTask(list, i)).start();
        }

        new Thread(new PrintTask(list)).start();
    }
}

class MyList {
    private String[] elements = new String[1000];
    private int index = 0;

    public int size() {
        return this.elements.length;
    }

    public String getElement(int index) {
        return this.elements[index];
    }

    public synchronized void addElement(String element) {
        this.elements[index] = element;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.index++;
        
        if(this.isFull()) {
            System.out.println("notifying");
            this.notify();
        }

    }

    public boolean isFull() {
        return this.index == this.elements.length;
    }
}

class AddElementTask implements Runnable {

    private MyList list;
    private int threadNumber;

    public AddElementTask(MyList list, int threadNum) {
        this.list = list;
        this.threadNumber = threadNum;
    }

    @Override
    public void run() {
        for(int i = 0; i<100;i++) {
            list.addElement("Thread "+threadNumber+" - "+i);
        }
    }

}

class PrintTask  implements Runnable {

    private MyList list;

    public PrintTask(MyList list) {
        this.list = list;
    }

    @Override
    public void run() {
        synchronized(list) {
            if(!list.isFull()) {
                try {
                    System.out.println("Waiting");
                    list.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            for (int i = 0; i<list.size(); i++) {
                System.out.println(i+" - "+list.getElement(i));
            }
        }
        
    }

}