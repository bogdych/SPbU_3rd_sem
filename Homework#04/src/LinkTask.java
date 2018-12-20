import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LinkTask implements Runnable{
    private String address;
    private int depth;
    private Document doc;

    public LinkTask(String address, int depth) {
        this.address = address;
        this.depth = depth;
    }

    @Override
    public void run() {
        try {
            Connection a = Jsoup.connect(address);
            doc = a.get();
        } catch (IOException e) {
            System.out.println("Processing: error: failed to get a page " + doc.location());
            return;
        }
        System.out.println("Processing: url " + doc.location() + " added to queue");

        Elements links = doc.select("a[href]");
        if (depth < Main.max_depth) {
            for (Element link : links) {
                String url = link.attr("abs:href");
                if (!Main.urls.contains(url)) {
                    Main.urls.add(url);
                    Main.pool.execute(new LinkTask(url, depth + 1));
                } else {
                    System.out.println("Processing: url " + url + " already added to queue");
                }
            }
        }
        Main.pool.execute(new SaveTask(doc));
    }
}
