import java.io.File;
import java.util.concurrent.*;

public class Main {
    private static int THREADS = 8;
    public static int max_depth = 2;
    public static ThreadPoolExecutor pool;
    //static ConcurrentSkipListSet<String> urls = new ConcurrentSkipListSet<>();
    public static CustomConcurrentStringList urls = new CustomConcurrentStringList();
    public static File resDir;

    public static void main(String[] args){
        if (createResFolder()) {
            String root = "https://timetable.spbu.ru/";
            urls.add(root);
            pool = new ThreadPoolExecutor(THREADS, THREADS, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<>());
            LinkTask task = new LinkTask(root, 1);
            pool.execute(task);

        }
        else {
            System.out.println("Couldn't create download directory");
        }
    }

    private static boolean createResFolder() {
        resDir = new File("res");
        boolean result = true;
        if (!resDir.exists()) {
            try{
                resDir.mkdir();
            }
            catch(SecurityException se){
                result = false;
            }
        }
        return result;
    }
}