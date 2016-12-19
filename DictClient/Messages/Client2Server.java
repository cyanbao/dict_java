package Messages;

import UI.WordCards.WordCard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Messages.Message.*;

/**
 * Created by Estrella on 2016/12/19.
 */
public class Client2Server {
    static public boolean isLiked(String userName, String word, String srcWeb){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(word);
        list.add(srcWeb);
        Message message = new Message(AskIsLiked,list);
        Message rMessage = Send.SendMessage(message);
        if(rMessage.getId() == OperatorSuccess)
            return true;
        else return false;
    }
    static public int likedNum(String userName,String word, String srcWeb){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(word);
        list.add(srcWeb);
        Message message = new Message(AskLikeNumMessage,list);
        Message rMessage = Send.SendMessage(message);
        if(rMessage.getId() == OperatorSuccess)
            return Integer.getInteger(rMessage.getMessageList().get(0));
        else return 0;
    }

    static public boolean setLikedNum(String userName,String word, String srcWeb, int like){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(word);
        list.add(srcWeb);
        list.add(Integer.toString(like));
        Message message = new Message(SendLikeWordMessage,list);
        Message rMessage = Send.SendMessage(message);
        return (rMessage.getId() == OperatorSuccess);
    }

    static public ArrayList<String> getOnlineUser(){
        Message message = new Message(AskOnLineUser,null);
        Message rMessage = Send.SendMessage(message);
        if (rMessage.getId() == OperatorSuccess)
            return (ArrayList<String>)rMessage.getMessageList();
        else return new ArrayList<String>();
    }

    static public ArrayList<String> getOfflineUser(){
        Message message = new Message(AskOffLineUser,null);
        Message rMessage = Send.SendMessage(message);
        if (rMessage.getId() == OperatorSuccess)
            return (ArrayList<String>)rMessage.getMessageList();
        else return new ArrayList<String>();
    }

    static public ArrayList<WordCard> getUserColList(String userName){//返回单词本
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        Message message = new Message(AskCollectWords,list);
        Message rMessage = Send.SendMessage(message);
        List<String> info=new ArrayList<>();
        ArrayList<WordCard> result = new ArrayList<WordCard>();
        if (rMessage.getId() == OperatorSuccess) {
            info = rMessage.getMessageList();
            for(int i =0;i<info.size();i++){
                String[] tmpCard=info.get(i).split("|");
                LinkedList<String> tmpTrans=new LinkedList<>();
                tmpTrans.add(tmpCard[3]);
                result.add(new WordCard(tmpCard[0],tmpCard[1],tmpCard[2],tmpTrans));
            }

        }
        return result;
    }
    static public boolean delUserColeList(String userName, String word){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(word);
        Message message = new Message(DelCollectWords,list);
        Message rMessage = Send.SendMessage(message);
        return (rMessage.getId() == OperatorSuccess);
    }

    static public boolean AddUserColeList(String userName, WordCard card){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(card.getWord());
        list.add(card.getPronUK());
        list.add(card.getPronUS());
        String trans = "";
        for(int i=0;i<card.getTranslation().size();i++)
            trans+=card.getTranslation().get(i);
        list.add(trans);
        Message message = new Message(AddCollectWords,list);
        Message rMessage = Send.SendMessage(message);
        return (rMessage.getId() == OperatorSuccess);
    }

    static public boolean sendCard(String userName, String trgName, WordCard card){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(trgName);
        list.add(card.getWord());
        list.add(card.getPronUK());
        list.add(card.getPronUS());
        String trans = "";
        for(int i=0;i<card.getTranslation().size();i++)
            trans+=card.getTranslation().get(i);
        list.add(trans);
        Message message = new Message(SendMyWordMessage,list);
        Message rMessage = Send.SendMessage(message);
        return (rMessage.getId() == OperatorSuccess);
    }
    static public boolean sendCard(String userName, String trgName, String text){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        list.add(trgName);
        list.add(text);
        Message message = new Message(SendMyWordMessage,list);
        Message rMessage = Send.SendMessage(message);
        return (rMessage.getId() == OperatorSuccess);
    }
    static public ArrayList<String> recieveMessage(String userName){
        ArrayList<String> list = new ArrayList<>();
        list.add(userName);
        Message message = new Message(RecieveMyWordMessage,list);
        Message rMessage = Send.SendMessage(message);
        if (rMessage.getId() == OperatorSuccess)
            return (ArrayList<String>)rMessage.getMessageList();
        else return new ArrayList<String>();
    }
}
