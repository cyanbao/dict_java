package SearchCrawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Estrella on 2016/12/7.
 */
class ResultBing extends SearchResult {
    ResultBing(String word) throws IOException, TimeoutException, StringIndexOutOfBoundsException {
        super(word);
        word.replaceAll(" ","%20");
        url = "http://cn.bing.com/dict/search?q="+word;
        Document doc = Jsoup.connect(url).get();

        Elements description = doc.select(".qdef").select("ul").select("li");
        if(description.size()>0) {//查到
            this.found=true;
            this.pronUK = doc.select(".hd_pr").text();
            if(pronUK.length()>2) {
                pronUK = pronUK.substring(2);

            }
            this.pronUS = doc.select(".hd_prUS").text();
            if(pronUS.length()>2) {
                pronUS = pronUS.substring(2);
            }
            for (Element d : description) {
                if (d.text().length() > 0)
                    this.description.add(d.text());
            }
        }//无联想
    }
}
