package UI.WordCards;

import SearchCrawling.WordInfo;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Estrella on 2016/12/18.
 */
public class WordCard {
    private String word="";
    private String pronUK="";
    private String pronUS="";
    private List<String> translation=new LinkedList<>();

    JPanel cardPanel=new JPanel(new BorderLayout());

    public String getWord(){return word;}
    public String getPronUK(){return pronUK;}
    public String getPronUS() {return pronUS;}
    public List<String> getTranslation(){return translation;}

    public JPanel getCardPanel() {return cardPanel;}
    private void generatePanel(){
        JTextArea textArea=new JTextArea();
        textArea.setText(word+"\n");
        if(pronUK.length()>0)
            textArea.append("英 "+pronUK);
        if(pronUS.length()>0)
            textArea.append("  美 "+pronUS+"\n");
        if(translation.size()>0)
            for(String t:translation)
                textArea.append(t+"\n");
        textArea.setEditable(false);
        textArea.setLineWrap(true);//自动换行
        textArea.setOpaque(false);//背景透明
        JScrollPane scroll = new JScrollPane();
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//垂直滚动条自动出现
        scroll.setOpaque(false);//透明
        scroll.getViewport().setOpaque(false);//透明


        cardPanel.add(scroll,BorderLayout.CENTER);
        cardPanel.setBorder(BorderFactory.createTitledBorder("单词卡："+word));
        //cardPanel.add(new JLabel("wordCard"));
    }

    public WordCard(String word, String pronUK, String pronUS, List<String> translation){
        this.word=word;
        this.pronUK=pronUK;
        this.pronUS=pronUS;
        this.translation=translation;
        generatePanel();
    }
    public WordCard(String word, List<String> translation){
        this.word=word;
        this.pronUK="";
        this.pronUS="";
        this.translation=translation;
        generatePanel();
    }
    public WordCard(WordInfo wordInfo){
        if(wordInfo!=null) {
            this.word = wordInfo.getWord();
            this.pronUK = wordInfo.getPronUK();
            this.pronUS = wordInfo.getPronUS();
            this.translation = wordInfo.getDscrp();
            generatePanel();
        }
    }

    public boolean equals(Object o) {
        if (o instanceof WordCard) {
            WordCard w = (WordCard) o;
            return (this.word.equals(w.word)
                    && this.pronUK.equals(w.pronUK)
                    && this.pronUS.equals(w.pronUS)
                    && this.translation.equals(w.translation));
        }
        return super.equals(o);
    }
}
