public class Operator {
    public static Carry sum(Carry x, Carry y) {
        if (y != Carry.M) {
            return y;
        } else {
            return x;
        }
    }
}
