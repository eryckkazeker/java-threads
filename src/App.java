public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Main Thread");
        NewThread t = new NewThread(5);
        Thread thread = new Thread(t);
        thread.start();
        Thread.sleep(1000);
    }
}

class NewThread implements Runnable {

    private int length;
    

    public NewThread(int length) {
        this.length = length;
    }

    @Override
    public void run() {
        for (int i = length; i>0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

}
