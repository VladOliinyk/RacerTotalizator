/**
 * Car thread class.
 */
public class Car extends Thread {

    /**
     * Boolean variable that contain thread 'car' status.
     */
    private boolean isFinished = false;

    /**
     * Integer variable that contain thread 'car' distance from the start.
     */
    private volatile int distance = 0;

    /**
     * Distance getter.
     * @return distance - thread 'car' distance from the start.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Method which sets isFinished variable to true.
     */
    public void finish() {
        isFinished = true;
    }

    /**
     * Thread status checker.
     * @return isFinished - boolean variable that contain thread 'car' status.
     */
    public boolean isFinish() {
        return isFinished;
    }

    /**
     * Overrided method which increases 'car' distance with some delay.
     */
    @Override
    public void run() {
        do {
            if (!isFinished) {
                distance += (int) (Math.random() * Program.MAX_VELOCITY + Program.MAX_VELOCITY * 0.90);
                if (distance >= Program.TRACK_LENGTH) {
                    finish();
                }

                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {}
            } else {
                distance = 0;
                isFinished = false;
                return;
            }
        }
        while (true);
    }
}
