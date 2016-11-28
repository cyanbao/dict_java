package JudgeEnter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created by Cyan on 2016/11/27.
 */
public class RegexDocument extends PlainDocument {
    private static final long serialVersionUID = 1L;
    private String regex = null;

    public RegexDocument(){
        super();
    }

    public RegexDocument(String regex){
        this();
        this.regex = regex;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
        if (str == null){
            return;
        }
        if (regex != null){
            if (!new StringBuilder(getText(0, getLength())).insert(offs, str).toString().matches(regex)){
                return;
            }
            super.insertString(offs, str, a);
        }
        else{
            super.insertString(offs, str, a);
        }
    }
}
