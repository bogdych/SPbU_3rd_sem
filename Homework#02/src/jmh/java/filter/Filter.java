package filter;

import java.awt.image.BufferedImage;

public class Filter {
    public BufferedImage input;
    public BufferedImage output;
    public volatile int lineCounter;
    public int maxIndex;

    private final int HORIZONTAL_MODE = 0;
    private final int VERTIACAL_MODE = 1;

    public Filter (BufferedImage input) {
        this.input = input;
        this.output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
    }

    public BufferedImage getImage(int threads, int mode) {
        lineCounter = 0;
        maxIndex = mode == HORIZONTAL_MODE ? input.getHeight() : input.getWidth();
        FilterThread[] filters = new FilterThread[threads];

        for (int i = 0; i < threads; i++) {
            filters[i] = new FilterThread(this, mode);
            filters[i].start();
        }

        for (int i = 0; i < threads; i++) {
            try {
                filters[i].join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}