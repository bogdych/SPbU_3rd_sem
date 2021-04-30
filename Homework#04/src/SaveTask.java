import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveTask implements Runnable {
    Document doc;

    public SaveTask(Document doc) {
        this.doc = doc;
    }

    @Override
    public void run() {
        System.out.println("Processing: saving page with url " + doc.location() + " on disk");
        BufferedWriter writer = null;
        try
        {
            String path = Main.resDir.getName() + File.separator + doc.location().replace("/","%").replace(":", "")+".html";
            writer = new BufferedWriter( new FileWriter(path));
            writer.write(doc.toString());
            }
        catch (IOException e){}
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Main.pool.getActiveCount() == 1) {
            Main.pool.shutdown();
        }
    }
}
