package filter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static int THREADS = 8;
    public static int HORIZONTAL_MODE = 0;
    public static int VERTIACAL_MODE = 1;

    public static void main(String[] args) throws IOException {
        File file = new File("res/input.jpg");
        BufferedImage input = ImageIO.read(file);
        Filter filter = new Filter(input);
        BufferedImage output = filter.getImage(THREADS, HORIZONTAL_MODE);
        ImageIO.write(output, "jpg", new File("res/output.jpg"));
    }
}
