package Messages;

import java.util.List;

/**
 * Created by Cyan on 2016/12/17.
 */
public class Message implements java.io.Serializable{
    /*消息种类对应id
    * 1:发送登录信息给服务器
    * 2：发送注册消息给服务器
    * 3：发送点赞信息给服务器
    * 4：请求服务器传回各个翻译器的点赞数量
    * 5：发送添加到单词本信息给服务器
    * 6：请求服务器查看登录人数
    * 7：发送信息给用户
    * */

    public static int SendLoginMessage = 1;
    public static int SendRegisterMessage = 2;
    public static int SendLikeWordMessage = 3;
    public static int AskLikeNumMessage = 4;
    public static int SendMyWordMessage=5;
    public static int RecieveMyWordMessage=22;//收信息
    public static int AskOnlineNum =6;
    public static int SendToOthersMessage = 7;
    public static int NotExistUsername =8;
    public static int PasswordError = 9;
    public static int LoginSuccess = 10;
    public static int ExistUsername=11;
    public static int RegisterSuccess = 12;
    public static int SendLogoutMessage = 13;
    public static int LogoutSuccess = 14;
    public static int LogoutFailure=15;
    public static int OperatorFailure= 16;
    public static int OperatorSuccess = 17;
    public static int AskIsLiked = 18;
    public static int AskOnLineUser=19;//上线用户名
    public static int AskOffLineUser=20;//离线用户名
    public static int AskCollectWords=21;//获取单词本
    public static int DelCollectWords=22;//删除单词本单词
    public static int AddCollectWords=23;//添加单词到单词本

    public static int UnknownError = 100;

    private int id;//消息种类
    private List<String> messageList;

    public Message(int id,List<String>messageList){
        this.id = id;
        this.messageList = messageList;
    }
    public Message(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }




}
