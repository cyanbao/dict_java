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
class ResultIciba extends SearchResult {
    ResultIciba(String word) throws IOException, TimeoutException {
        super(word);
        word.replaceAll(" ","%20");
        url = "http://www.iciba.com/"+word;
        Document doc = Jsoup.connect(url).get();

        Elements description = doc.select(".in-base").select("ul").select(".clearfix");
        if(description.size()>0) {//查到
            this.found=true;
            Elements pron = doc.select(".base-speak").select("span");
            String prontext = pron.text();
            if(prontext.length()>0) {
                this.pronUK = prontext.substring(prontext.indexOf('['), prontext.indexOf(']')+1);
                this.pronUS = prontext.substring(prontext.lastIndexOf('['), prontext.length());
            }
            for (Element d : description) {
                if (d.text().length() > 0)
                    this.description.add(d.text());
            }
        }else {
            Elements suggestion = doc.select(".sug-words").select("a");
            for (Element s : suggestion) {
                if (s.text().length() > 0)
                    this.suggestion.add(s.text());
            }
        }

    }
}
