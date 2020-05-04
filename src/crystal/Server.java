package crystal;

public class Server {
    boolean workingAnimation = true;

    public synchronized void handleAnimation() {
        while (!workingAnimation) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        workingAnimation = false;
        notify();
    }

    public synchronized void handleGameLogic() {
        while (workingAnimation) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        workingAnimation = true;
        notify();
    }
}
