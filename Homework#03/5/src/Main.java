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

    public static void main(String[] args) throws InterruptedException {
        amountOfCommands = 4;
        commands = new Command[amountOfCommands];

        commands[0] = new Command(45, 40);
        commands[1] = new Command(30, 50);
        commands[2] = new Command(105, 40);
        commands[3] = new Command(90, 20);

        multithreadedVersion();

        System.out.println("Turtle's coordinates:\n\tx = " + result.x + "\n\ty = " + result.y);
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