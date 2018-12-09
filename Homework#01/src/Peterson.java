public class Peterson {
    private static boolean[] flag = {false, false};
    private static volatile int victim = -1;

    private int other(int numb) {
        return numb == 0 ? 1 : 0;
    }

    void lock(int numb) {
        flag[numb] = true;
        victim = numb;
        while (flag[other(numb)] && victim == numb) {}
    }

    void unlock(int numb) {
        flag[numb] = false;
    }
}