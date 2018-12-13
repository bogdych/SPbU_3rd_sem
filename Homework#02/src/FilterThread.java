import java.awt.*;

public class FilterThread extends Thread {
    private Filter filter;
    private int mode;
    private final int RADIUS = 5;

    public FilterThread(Filter filter, int mode){
        this.filter = filter;
        this.mode = mode;
    }

    @Override
    public void run(){
        while (filter.lineCounter < filter.maxIndex) {
            filter.lineCounter++;
            processLine(filter.lineCounter - 1);
        }
    }

    private void processLine(int line) {
        if (mode == 0) {
            for (int i = 0; i < filter.input.getWidth(); i++) {
                processPixel(i, line);
            }
        }
        else {
            for (int i = 0; i < filter.input.getHeight(); i++) {
                processPixel(line, i);
            }
        }
    }

    private void processPixel(int x, int y) {
        int surrPixels = 0;
        int[] colors = new int[3];

        for (int i =- RADIUS; i < RADIUS; i++) {
            for (int j =- RADIUS; j < RADIUS; j++) {
                if (x + i < filter.input.getWidth() && x + i >= 0 && y + j < filter.input.getHeight() && y + j >= 0) {
                    surrPixels++;
                    Color color = new Color(filter.input.getRGB(x + i, y + j));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    colors[0] += red;
                    colors[1] += green;
                    colors[2] += blue;
                }
            }
        }

        setColor(x, y, colors, surrPixels);
    }

    private void setColor (int x, int y, int[] colors, int surrPixels) {
        int new_red = colors[0] / surrPixels;
        int new_green = colors[1] / surrPixels;
        int new_blue = colors[2] / surrPixels;
        int new_color = getIntFromColor(new_red, new_green, new_blue);
        filter.output.setRGB(x, y, new_color);
    }

    public int getIntFromColor(int red, int green, int blue){
        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;
        return 0xFF000000 | red | green | blue;
    }
}
