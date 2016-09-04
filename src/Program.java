import java.io.IOException;

public class Program {
    public static int TLENGTH = 1000;
    public static int VELOCITY = 100;
    public static int MAINTICK = 16; // ~1000 / 60 = 60fps

    // Racer threads
    private static Racer first;
    private static Racer second;
    private static Racer third;

    // Time counter leading
    private static double fTime = 0;
    private static double sTime = 0;
    private static double tTime = 0;

    // Global timer
    private static int timer = 0;

    public static void main(String[] args) throws IOException {
        setGlobalParams(args);
        createRacerThreads();


        printTrackInfo();
        startRacerThreads();
        // Running a "while loop" that starts the threads racers
        //  and prints information about their position.
        do {
            timer++;
            incrementLeaderTimeCounter();
            printTrackInfo();
            try {
                Thread.sleep(MAINTICK);
            } catch (InterruptedException e) {
            }
        } while (isSomeOneFinish() == 0);

        //Finish all threads.
        finishRacerThreads();

        printFinalInfo();
    }

    private static int isSomeOneFinish() {
        // if first racer finished, and he is FARTHER than the second and third
        if (!first.isAlive() && (first.getPos() > second.getPos()) && (first.getPos() > third.getPos()))
            return 1;

        // if second racer finished, and he is FARTHER than the first and third
        if (!second.isAlive() && (second.getPos() > first.getPos()) && (second.getPos() > third.getPos()))
            return 2;

        // if third racer finished, and he is FARTHER than the first and second
        if (!third.isAlive() && (third.getPos() > first.getPos()) && (third.getPos() > second.getPos()))
            return 3;

        // return randomly number of one of racers,
        //  if during our MAINTICK, more than 1 racer finished and...

        // ...the position of the first and second racer are EQUAL
        if (first.getPos() >= TLENGTH && first.getPos() == second.getPos()) {
            int a = (int) (Math.random() * 2) + 1;  // [1, 2]
            return a;
        }

        // ...the position of the first and third racer are EQUAL
        if (first.getPos() >= TLENGTH && first.getPos() == third.getPos()) {
            int a = (int) (Math.random() * 2) + 1;  // [1, 2]
            if (a != 1) {
                a = 3;                              // 2 to 3
            }
            return a;
        }

        // ...the position of the second and third racer are EQUAL
        if (second.getPos() >= TLENGTH && second.getPos() == third.getPos()) {
            int a = (int) (Math.random() * 2) + 2;  // [2, 3]
            return a;
        }

        // ...the position of all the racers EQUAL
        if (first.getPos() >= TLENGTH &&
                first.getPos() == second.getPos() &&
                first.getPos() == third.getPos()) {
            int a = (int) (Math.random() * 3) + 1;  // [1, 3]
            return a;
        }

        return 0;
    }

    private static void printFinalInfo() {
        System.out.println(isSomeOneFinish() + " racer finished first!");
        // printing and calculating percent of leading time for each racer
        timer--;
        fTime = 100 * fTime / timer;
        sTime = 100 * sTime / timer;
        tTime = 100 * tTime / timer;
        // or if you wanna nice view but not perfect data (error ~1%):
        tTime = 100 - fTime - sTime;

        System.out.printf("Racer #1 leading time: %3.0f%% \n", fTime);
        System.out.printf("Racer #2 leading time: %3.0f%% \n", sTime);
        System.out.printf("Racer #3 leading time: %3.0f%% \n", tTime);
    }

    private static void finishRacerThreads() {
        first.finish();
        second.finish();
        third.finish();
    }

    private static void startRacerThreads() {
        first.start();
        second.start();
        third.start();
    }

    private static void createRacerThreads() {
        first = new Racer();
        second = new Racer();
        third = new Racer();
    }

    private static void printTrackInfo() {
        System.out.print("Tick: " + timer + "     " + "Track length: " + TLENGTH + "\n");
        System.out.println("| first | second | third |");
        System.out.print("| ");
        System.out.printf("%4d", first.getPos());
        System.out.print("  | ");
        System.out.printf("%5d", second.getPos());
        System.out.print("  | ");
        System.out.printf("%4d", third.getPos());
        System.out.print("  | \n\n");
    }

    private static void incrementLeaderTimeCounter() {
        if (first.getPos() > second.getPos() && first.getPos() > third.getPos()) {
            fTime++;
        }
        if (second.getPos() > first.getPos() && second.getPos() > third.getPos()) {
            sTime++;
        }
        if (third.getPos() > first.getPos() && third.getPos() > second.getPos()) {
            tTime++;
        }
    }

    private static void setGlobalParams(String[] args) {
        if (args.length != 0) {
            if (args.length != 3 && args.length > 0) {
                System.out.printf(" ! Be sure that you enter params correctly!" +
                        "\n !   Params:  TrackLength   CarsVelocity   RefreshTick" +
                        "\n !   Default: %11d   %12d   %11d", TLENGTH, VELOCITY, MAINTICK);
                System.exit(0);
            }

            if (args.length == 3) {
                TLENGTH = Math.abs(Integer.parseInt(args[0]));
                VELOCITY = Math.abs(Integer.parseInt(args[1]));
                MAINTICK = Math.abs(Integer.parseInt(args[2]));
            }
        }
    }
}

