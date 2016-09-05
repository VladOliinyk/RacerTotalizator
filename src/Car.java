

public class Car extends Thread {

    private boolean isFinished = false;
    private volatile int distance = 0;

    public int getDistance() {
        return distance;
    }

    public void finish() {
        isFinished = true;
    }

    public boolean isFinish() {
        return isFinished;
    }

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
