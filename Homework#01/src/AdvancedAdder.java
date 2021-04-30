import java.util.concurrent.locks.Lock;

public class AdvancedAdder implements Runnable{
    private StringBuilder str;
    private int id;
    private int counts;
    private Lock lock;

    public AdvancedAdder(StringBuilder str, int id, int counts, Lock lock) {
        this.str = str;
        this.id = id;
        this.counts = counts;
        this.lock = lock;
    }

    @Override
    public void run(){
        for (int i = 0; i < counts; i++) {
            lock.lock();
            try {
                str.append(id).append(" equals ").append(id).append("; ");
            }
            finally {
                lock.unlock();
            }
        }
    }
}
