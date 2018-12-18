public class Coordinates {
    public double x;
    public double y;

    public Coordinates(double a, double b) {
        this.x = a;
        this.y = b;
    }

    public static Coordinates sumPairs(Coordinates p1, Coordinates p2) {
        p1.x = p1.x + p2.x;
        p1.y = p1.y + p2.y;
        return p1;
    }
}