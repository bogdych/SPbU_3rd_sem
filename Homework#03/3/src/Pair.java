public class Pair {
    public int a;
    public int b;

    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static Pair sumPairs(Pair p1, Pair p2) {
        Pair res = new Pair(0, 0);
        res.a = p1.a * p2.a;
        res.b = p2.a * p1.b + p2.b;
        return res;
    }
}