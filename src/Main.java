public class Main {
    public static StringBuilder str = new StringBuilder();

    public static void main(String[] args) {
        Adder add1 = new Adder("Hello!");
        Adder add2 = new Adder("My name is Mark.");
        Adder add3 = new Adder("What about you?");

        Thread thread1 = new Thread(add1);
        Thread thread2 = new Thread(add2);
        Thread thread3 = new Thread(add3);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
        }
        catch (InterruptedException e) {}

        try {
            thread2.join();
        }
        catch (InterruptedException e) {}

        try {
            thread3.join();
        }
        catch (InterruptedException e) {}

        System.out.println(str.toString());
    }
}
