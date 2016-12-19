package SearchCrawling;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Estrella on 2016/12/8.
 */
public class WordInfo implements Comparable<WordInfo> {
    private String word;
    private SearchResult searchResult;
    private int like;
    //private boolean collect;
    private String srcWeb;
    private boolean visual;

    public WordInfo(String word, String srcWeb) throws IOException, TimeoutException {
        this.word=word;
        /*Message message = new Message(Message.SendLogoutMessage,null);
        Message receive = Send.SendMessage(message);
        if(receive.getId()==Message.OperatorSuccess){
            like = Integer.getInteger(message.getMessageList().get(0));
        }
        else*/ like = 0;
        this.srcWeb = srcWeb;
        if(srcWeb=="有道")
            searchResult=new ResultYoudao(word);
        else if(srcWeb=="金山")
            searchResult=new ResultIciba(word);
        else if(srcWeb=="必应")
            searchResult=new ResultBing(word);
        else searchResult=null;
        visual = false;
    }



    public String getWord(){return word;}
    public int getLike(){return like;}
    public String getSrcWeb(){return srcWeb;}
    public boolean isVisual(){return visual;}
    public void setVisual(boolean visual){this.visual=visual;}
    //public boolean isCollect(){return collect;};
    //public void setCollect(boolean collect){this.collect=collect;}


    public String getPronUK(){
        if(searchResult!=null)
            return searchResult.getPronUK();
        else return null;
    }
    public String getPronUS(){
        if(searchResult!=null)
            return searchResult.getPronUS();
        else return null;
    }
    public List<String> getDscrp(){
        if(searchResult!=null)
            return searchResult.getDescription();
        else return null;
    }
    public List<String> getSggst(){
        if(searchResult!=null)
            return searchResult.getSuggestion();
        else return null;
    }
    public boolean isFound() {
        if (searchResult != null)
            return searchResult.isFound();
        else return false;
    }
    public String getUrl(){
        if(searchResult!=null)
            return searchResult.getUrl();
        else return null;
    }

    public void setLike(int like){this.like=like;}



    @Override
    public int compareTo(WordInfo o) {
        if(o.visual && !this.visual)
            return 2;
        else if(!o.visual && this.visual)
            return -2;
        else {
            if(o.like > this.like)
                return 1;
            else if(o.like < this.like)
                return -1;
            else return 0;
        }
    }
}
