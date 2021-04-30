public class Adder implements Runnable {
    private StringBuilder str;
    private int id;

    public Adder (String data, int id) {
        this.str = new StringBuilder(data);
        this.id = id;
    }

    @Override
    public void run() {
        /*Main.lock.lock(id - 1);
        try {
            for (int i = 0; i < str.length(); i++) {
                //Main.str.append(str.charAt(i));
            }
            //Main.str.append(" ");
        }
        finally {
            Main.lock.unlock(id - 1);
        }*/
    }
}
