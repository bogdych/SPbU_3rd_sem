import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomThreadPool implements Executor {
    private Thread[] threads;
    private PoolTask[] runs;
    private int THREADS;
    private volatile boolean isRunning;
    private CustomTaskQueue queue;
    private Lock lock = new ReentrantLock();

    public CustomThreadPool(int THREADS, CustomTaskQueue queue) {
        this.THREADS = THREADS;
        this.queue = queue;
        threads = new Thread[THREADS];
        runs = new PoolTask[THREADS];
        isRunning = true;
        for (int i = 0; i < THREADS; i++) {
            runs[i] = new PoolTask();
            threads[i] = new Thread(runs[i]);
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (isRunning) {
            queue.offer(runnable);
        }

    }

    public void shutdown() {
        isRunning = false;
    }

    public int getActiveCount() {
        int activeThreads = 0;
        for (PoolTask r : runs) {
            if (r.isActive) {
                activeThreads++;
            }
        }
        return activeThreads;
    }

    private class PoolTask implements Runnable {
        boolean isActive = false;

        @Override
        public void run() {
            while (isRunning) {
                Runnable elem = queue.poll();
                if (elem != null) {
                    isActive = true;
                    elem.run();
                    lock.lock();
                    if (Main.pool.getActiveCount() == 1 && queue.isEmpty()) {
                        Main.pool.shutdown();
                    }
                    isActive = false;
                    lock.unlock();
                }
            }
        }
    }


}