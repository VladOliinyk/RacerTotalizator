class Racer extends Thread {
    private boolean tFinish = false;
    private volatile int rPos = 0;

    public int getPos() {
        return rPos;
    }

    public void finish()
    {
        tFinish = true;
    }

    @Override
    public void run() {
        do {
            if (!tFinish)
            {
                rPos += (int)(Math.random()*Program.VELOCITY)+1;
                if (rPos >= Program.TLENGTH) {
                    finish();
                }
            } else
                return;

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {}
        }
        while (true);
    }
}
