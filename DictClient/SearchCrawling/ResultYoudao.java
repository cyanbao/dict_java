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
class ResultYoudao extends SearchResult {
    private String addition;
    ResultYoudao(String word) throws IOException, TimeoutException{
        super(word);
        word.replaceAll(" ","%20");
        url = "http://dict.youdao.com/w/"+word+"/#keyfrom=dict2.top";
        Document doc = Jsoup.connect(url).get();

        Elements description = doc.select("#phrsListTab").select(".trans-container").select("ul").select("li");
        if(description.size()>0) {//查到
            this.found=true;

            Elements pron = doc.select(".pronounce").select(".phonetic");
            String prontext = pron.text();
            if(prontext.length()>0) {
                this.pronUK = prontext.substring(prontext.indexOf('['), prontext.indexOf(']')+1);
                this.pronUS = prontext.substring(prontext.lastIndexOf('['), prontext.length());
            }

            addition = doc.select("#phrsListTab").select(".trans-container").select(".additional").text();

            for (Element d : description) {
                if (d.text().length() > 0)
                    this.description.add(d.text());
            }
            if(addition.length()>0)
                this.description.add(addition);

        }else {
            Elements suggestionWords = doc.select(".typo-rel").select("p");
            for (Element w : suggestionWords) {
                if (w.text().length() > 0)
                    this.suggestion.add(w.text());
            }
        }
    }
}
