public class Adder implements Runnable {
    private StringBuilder str;

    public Adder (String data) {
        this.str = new StringBuilder(data);
    }

    @Override
    public void run() {
        Main.lock.lock();
        try {
            for (int i = 0; i < str.length(); i++) {
                Main.str.append(str.charAt(i));
            }
            Main.str.append(" ");
        }
        finally {
            Main.lock.unlock();
        }
    }
}
