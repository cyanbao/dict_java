package SearchCrawling;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Estrella on 2016/12/6.
 */
class SearchResult {
    protected String word;
    protected String url;
    protected String pronUK;
    protected String pronUS;
    protected List<String> description=new LinkedList<>();
    protected List<String> suggestion=new LinkedList<>();
    protected boolean found;
    public SearchResult(String word){
        this.word=word;
        url="";
        pronUK="";
        pronUS="";
        description = new LinkedList<>();
        suggestion = new LinkedList<>();
        found=false;
    }
    String getWord(){return  word;}
    String getUrl(){return url;}
    String getPronUK(){return pronUK;}
    String getPronUS(){return pronUS;}
    List<String> getDescription(){return description;}
    List<String> getSuggestion(){return suggestion;}
    boolean isFound(){return found;}
}
