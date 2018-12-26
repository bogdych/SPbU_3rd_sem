public class Main {
    private static int THREADS = 4;
    private static int amountOfCommands;
    public static Command[] commands;
    public static double[] angles;
    public static volatile double[] anglePrefixes;
    public static Coordinates[] coordinates;
    public static volatile Coordinates[] prefixes;
    public static volatile Coordinates result;
    private static Thread[] threads;
    private static int step;
    public static int MAX_THREADS = 4;


    public static void main(String[] args){
        amountOfCommands = 1000000;
        commands = getCommands();

        int amountOfTests = 20;
        long[][] results = new long[MAX_THREADS + 1][amountOfTests];

        for (int t = 1; t < MAX_THREADS + 1; t++) {
            for (int i = 0; i < amountOfTests; i++) {
                THREADS = (int) Math.pow(2, t - 1);
                long startTime = System.nanoTime();
                try {
                    multithreadedVersion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long resTime = System.nanoTime() - startTime;
                results[t][i] = resTime;
            }
        }

        for (int i = 0; i < amountOfTests; i++) {
            long startTime = System.nanoTime();
            singlethreadedVersion();
            long resTime = System.nanoTime() - startTime;
            results[0][i] = resTime;
        }

        System.out.println("Average time (ms)");
        System.out.println("Single-Threaded version - " + (getAverageTime(results[0]) / 1000000) + "," + (getAverageTime(results[0]) / 1000 % 1000));
        System.out.println("Multithreaded version:");
        for (int i = 1; i < MAX_THREADS + 1; i++) {
            System.out.println(((int) (Math.pow(2, i - 1))) + ". " + (getAverageTime(results[i]) / 1000000) + "," + (getAverageTime(results[i]) / 1000 % 1000));
        }


       /* multithreadedVersion();

        System.out.println("Turtle's coordinates:\n\tx = " + result.x + "\n\ty = " + result.y);*/
    }

    public static long getAverageTime(long[] array) {
        long res = 0;
        for (int i = 0; i < array.length; i++) {
            res += array[i];
        }
        return res / array.length;
    }

    private static Command[] getCommands(){
        Command[] res = new Command[amountOfCommands];
        for (int i = 0; i < amountOfCommands; i++) {
            res[i] = new Command(((int) (Math.floor(Math.random() * 360))), (int) (Math.floor(Math.random() * 100)));
        }
        return res;
    }

    private static void multithreadedVersion() throws InterruptedException{
        anglePrefixes = new double[THREADS];
        angles = new double[amountOfCommands];
        for (int i = 0; i < amountOfCommands; i++) {
            angles[i] = commands[i].angle;
        }
        step = amountOfCommands / THREADS;

        threads = new Thread[THREADS];
        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new AnglePrefixMaker(i * step, (i + 1) * step, i));
            threads[i].start();
        }
        AnglePrefixMaker angleLast = new AnglePrefixMaker((THREADS - 1) * step, amountOfCommands, THREADS - 1);
        angleLast.run();

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }

        startAnglePrefixScan();

        prefixes = new Coordinates[THREADS];
        coordinates = new Coordinates[amountOfCommands];
        for (int i = 0; i < amountOfCommands; i++) {
            coordinates[i] = new Coordinates(Math.cos(Math.toRadians(angles[i])) * commands[i].length, Math.sin(Math.toRadians(angles[i])) * commands[i].length);
        }

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new PrefixMaker(i * step, (i + 1) * step, i));
            threads[i].start();
        }
        PrefixMaker last = new PrefixMaker((THREADS - 1) * step, amountOfCommands, THREADS - 1);
        last.run();

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }
        startPrefixScan();
    }

    private static void startAnglePrefixScan() throws InterruptedException{
        AngleTree head = new AngleTree(1, Main.THREADS);
        head.create_tree(1, Main.THREADS);
        head.upsweep();
        head.downsweep(0);

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new AnglePrefixHandler(i * step, (i + 1) * step, i));
            threads[i].start();
        }

        AnglePrefixHandler last = new AnglePrefixHandler((THREADS - 1) * step, amountOfCommands, THREADS - 1);
        last.run();


        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }
    }

    private static void startPrefixScan() {
        Tree tree = new Tree(1, THREADS);
        tree.create_tree(1, THREADS);
        result = tree.upsweep();
    }

    private static void singlethreadedVersion() {
        Coordinates tmp = new Coordinates(0, 0);
        Coordinates dif = new Coordinates(0,0);
        int angleDif = 0;
        for (int i = 0; i < amountOfCommands; i++) {
            angleDif += commands[i].angle;
            dif.x = Math.cos(Math.toRadians(angleDif)) * commands[i].length;
            dif.y = Math.sin(Math.toRadians(angleDif)) * commands[i].length;
            tmp = Coordinates.sumPairs(tmp, dif);
        }
        result = tmp;
    }
}