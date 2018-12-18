public class AngleTree {
    AngleTree left;
    AngleTree right;
    Double value;
    int left_bound;
    int right_bound;

    public AngleTree(int left_bound, int right_bound) {
        this.left_bound = left_bound;
        this.right_bound = right_bound;
    }

    public void create_tree(int left_bound, int right_bound) {
        if (left_bound == right_bound) {
            this.value = Main.anglePrefixes[left_bound - 1];
        } else {
            int middle = left_bound + (right_bound - left_bound + 1) / 2 - 1;
            left = new AngleTree(left_bound, middle);
            right = new AngleTree(middle + 1, right_bound);
            Thread t1 = new Thread(() -> left.create_tree(left_bound, middle));
            t1.start();
            right.create_tree(middle + 1, right_bound);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public double upsweep() {
        if (value != null) {
            return value;
        } else {
            class Upsweep implements Runnable {
                AngleTree tree;
                Double return_value;

                public Upsweep(AngleTree tree) {
                    this.tree = tree;
                }

                @Override
                public void run() {
                    return_value = tree.upsweep();
                }
            }
            Upsweep run1 = new Upsweep(left);
            Upsweep run2 = new Upsweep(right);
            Thread t1 = new Thread(run1);
            t1.start();
            run2.run();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            value = run1.return_value + run2.return_value;
            return value;
        }
    }

    public void downsweep(double car) {
        value = car;
        if (left_bound == right_bound) {
            Main.anglePrefixes[left_bound - 1] = value;
            return;
        }
        Double calculated_value = value + left.value;
        Thread t1 = new Thread(() -> left.downsweep(value));
        t1.start();
        right.downsweep(calculated_value);
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}