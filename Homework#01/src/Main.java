import java.util.concurrent.locks.Lock;

public class Main {

    public static void main(String[] args) {
        StringBuilder str = new StringBuilder();
        int THREADS = 4;
        int counts = 100000;
        int amountOfTests = 20;

        TASLock tas = new TASLock();
        long tasRes = lockTest(str, THREADS, counts, amountOfTests, tas);
        System.out.println("TASLock average time - " + (tasRes / 1000000) + "," + (tasRes / 1000 % 1000) + " (ms)");

        str = new StringBuilder();

        TTASLock ttas = new TTASLock();
        long ttasRes = lockTest(str, THREADS, counts, amountOfTests, ttas);
        System.out.println("TTASLock average time - " + (ttasRes / 1000000) + "," + (ttasRes / 1000 % 1000) + " (ms)");
    }

    public static long lockTest(StringBuilder str, int THREADS, int counts, int amountOfTests, Lock lock) {
        Thread[] threads = new Thread[THREADS];
        long[] results = new long[amountOfTests];

        for (int t = 0; t < amountOfTests; t++) {
            for (int i = 0; i < THREADS; i++) {
                threads[i] = new Thread(new AdvancedAdder(str, i, counts, lock));
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < THREADS; i++) {
                threads[i].start();
            }
            for (int i = 0; i < THREADS; i++) {
                try {
                    threads[i].join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long resTime = System.nanoTime() - startTime;
            results[t] = resTime;
        }

        long averageTime = getAverageTime(results);
        return averageTime;
    }

    private static long getAverageTime(long[] results) {
        long res = 0;
        for (int i = 0; i < results.length; i++) {
            res += results[i];
        }
        return res / results.length;
    }

    public static void oldTests() {
        Adder add1 = new Adder("Hello!", 1);
        Adder add2 = new Adder("My name is Mark.", 2);
        //Adder add3 = new Adder("What about you?", 3);

        Thread thread1 = new Thread(add1);
        Thread thread2 = new Thread(add2);
        //Thread thread3 = new Thread(add3);

        thread1.start();
        thread2.start();
        //thread3.start();

        try {
            thread1.join();
        }
        catch (InterruptedException e) {}

        try {
            thread2.join();
        }
        catch (InterruptedException e) {}

        /*try {
            thread3.join();
        }
        catch (InterruptedException e) {}*/

        //System.out.println(str.toString());
    }
}
