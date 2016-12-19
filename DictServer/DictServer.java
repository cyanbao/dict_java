import Database.DBHelper;
import Messages.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;


/**
 * Created by Cyan on 2016/12/17.
 */
public class DictServer extends JFrame{
    private JTextArea jta =new JTextArea();
    private DBHelper db = new DBHelper();
    public static void main(String[] args){
        new DictServer();
    }


    public DictServer() {
        //Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);

        setTitle("DictMultiThreadServer");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            //create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("MultiThreadServer Started at " + new Date() + '\n');

            //Number a client
            int clientNo = 1;

            //Create an obeject output stream
            /*TODO:...*/

            while (true) {
                //listen for a new connection request
                Socket socket = serverSocket.accept();

                jta.append("Starting thread for client" + clientNo + " at " + new Date() + '\n');

                InetAddress inetAddress = socket.getInetAddress();
                jta.append("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                jta.append("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");

                //Create a new thread for the connection
                HandleAClient task = new HandleAClient(socket);

                //Start a new thread for the connection
                new Thread(task).start();

                //Increment clientNo
                clientNo++;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    //Inner class
    class HandleAClient implements Runnable{
        private Socket socket;//A connected socket

        public HandleAClient(Socket socket){
            this.socket=socket;
        }
        public void run(){
            try{
                ObjectInputStream fromClientObject = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                ObjectOutputStream toClientObject = new ObjectOutputStream(socket.getOutputStream());
                while(true){
                    /*TODO:处理传来的消息*/
                    Object object = fromClientObject.readObject();
                    Message receive = (Message)object;
                    int id = receive.getId();
                    if(id==Message.SendLoginMessage){
                        List<String> list = receive.getMessageList();
                        User user = new User(list.get(0),list.get(1));
                        jta.append("登录请求->"+"用户名："+user.getUsername()+"密码："+user.getPassword()+'\n');
                        /*TODO:数据库处理*/
                        boolean UserExists = db.UserExists(user.getUsername());
                        if(UserExists==false){
                            Message send = new Message(Message.NotExistUsername);
                            toClientObject.writeObject(send);
                        }
                        else {
                            boolean PasswordCheck =db.PasswordCheck(user.getUsername(),user.getPassword());
                             if (PasswordCheck == false) {
                                Message send = new Message(Message.PasswordError);
                                toClientObject.writeObject(send);
                            } else if (PasswordCheck == true) {
                                if(db.UserState(user.getUsername(),true)) {
                                    Message send = new Message(Message.LoginSuccess);
                                    toClientObject.writeObject(send);
                                }
                                else{
                                    Message send = new Message(Message.UnknownError);
                                    toClientObject.writeObject(send);
                                }
                            } else {
                                Message send = new Message(Message.UnknownError);
                                toClientObject.writeObject(send);
                            }
                        }
                    }
                    else if(id==Message.SendRegisterMessage){
                        List<String> list = receive.getMessageList();
                        User user = new User(list.get(0),list.get(1),list.get(2));
                        jta.append("注册请求->"+"用户名："+user.getUsername()+"密码："+user.getPassword()+"邮箱："+user.getEmail()+'\n');
                        /*数据处理*/
                        boolean UserExists = db.UserExists(user.getUsername());
                        if(UserExists==true){
                            Message send = new Message(Message.ExistUsername);
                            toClientObject.writeObject(send);
                        }
                        else if(UserExists==false){
                            boolean register =db.UserRegister(user);
                            if(register==true) {
                                Message send = new Message(Message.RegisterSuccess);
                                toClientObject.writeObject(send);
                            }
                            else{
                                Message send = new Message(Message.UnknownError);
                                toClientObject.writeObject(send);
                            }
                        }
                        else{
                            Message send = new Message(Message.UnknownError);
                            toClientObject.writeObject(send);
                        }
                    }
                    else if(id==Message.SendLogoutMessage){
                        List<String> list = receive.getMessageList();
                        jta.append("登出请求->"+"用户名："+list.get(0));
                        if(db.UserState(list.get(0),false)){
                            Message send = new Message(Message.LogoutSuccess);
                            toClientObject.writeObject(send);
                        }
                        else{
                            Message send = new Message(Message.LogoutFailure);
                            toClientObject.writeObject(send);
                        }
                    }
                    else if(id==Message.SendLikeWordMessage){
                        List<String> list = receive.getMessageList();
                        jta.append("点赞请求->"+"用户名："+list.get(0)+"单词："+list.get(1)+"翻译器："+list.get(2)+"\n");
                        if(db.LikeExists(list)==false){
                            if(db.AddLike(list)==true) {
                                Message send = new Message(Message.OperatorSuccess);
                                toClientObject.writeObject(send);
                            }
                            else{
                                Message send = new Message(Message.OperatorFailure);
                                toClientObject.writeObject(send);
                            }
                        }
                        else{
                            Message send = new Message(Message.OperatorFailure);
                            toClientObject.writeObject(send);
                        }
                    }
                    else if(id==Message.AskLikeNumMessage){
                        List<String> list = receive.getMessageList();
                        jta.append("请求赞数->"+"用户名："+list.get(0)+"单词："+list.get(1)+"翻译器："+list.get(2)+"\n");
                        String num = db.GetLikeNum(list.get(0),list.get(2));
                        List<String> Slist = new ArrayList<String>();
                        Slist.add(num);
                        Message send = new Message(Message.OperatorSuccess,Slist);
                        toClientObject.writeObject(send);
                    }
                    else if(id==Message.AskIsLiked){
                        List<String> list = receive.getMessageList();
                        jta.append("是否点赞->"+"用户名："+list.get(0)+"单词："+list.get(1)+"翻译器："+list.get(2)+"\n");
                        if(db.LikeExists(list)==true){
                            Message send = new Message(Message.OperatorSuccess);
                            toClientObject.writeObject(send);
                        }
                        else {
                            Message send = new Message(Message.OperatorFailure);
                            toClientObject.writeObject(send);
                        }
                    }
                    else if(id==Message.AskOnLineUser){
                        jta.append("获取在线人数"+"\n");
                        List<String> list = db.GetOnUser();
                        Message send = new Message(Message.OperatorSuccess,list);
                        toClientObject.writeObject(send);
                    }
                    else if(id==Message.AskOffLineUser){
                        jta.append("获取离线人数"+"\n");
                        List<String> list = db.GetOffUser();
                        Message send = new Message(Message.OperatorSuccess,list);
                        toClientObject.writeObject(send);
                    }
                    else if(id==Message.AskCollectWords){
                       /*单词本功能待完善*/
                        List<String> list = receive.getMessageList();
                       jta.append("获取单词本请求->"+"用户名"+list.get(0));
                        List<String>Slist =db.GetBook(list.get(0));
                        Message send = new Message(Message.OperatorSuccess,Slist);
                        toClientObject.writeObject(send);
                    }
                    else if(id ==Message.AddCollectWords){
                        List<String> list = receive.getMessageList();
                        jta.append("添加单词->"+"用户名："+list.get(0)+"单词："+list.get(1));
                        db.AddWord(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4));
                        Message send = new Message(Message.OperatorSuccess,null);
                        toClientObject.writeObject(send);
                    }
                    else if(id==Message.DelCollectWords){
                        List<String> list = receive.getMessageList();
                        jta.append("删除单词->"+"用户名："+list.get(0)+"单词："+list.get(1));
                        db.DeleteWord(list.get(0),list.get(1));
                        Message send = new Message(Message.OperatorSuccess,null);
                        toClientObject.writeObject(send);
                    }
                    else if(id==Message.SendMyWordMessage){
                        List<String> list = receive.getMessageList();
                        jta.append("发送信息请求->"+"\n");
                         if( db.SendMessage(list.get(0),list.get(1),list.get(2))==true){
                             Message send = new Message(Message.OperatorSuccess);
                             toClientObject.writeObject(send);
                         }
                         else{
                             Message send = new Message(Message.OperatorFailure);
                             toClientObject.writeObject(send);
                         }
                    }
                    else if(id==Message.RecieveMyWordMessage){
                        List<String> list = receive.getMessageList();
                        List<String>Slist = db.GetMessage(list.get(0));
                        Message send = new Message(Message.OperatorSuccess,Slist);
                        toClientObject.writeObject(send);
                    }
                    else{
                        jta.append("未知指令\n");
                    }

                }
            }catch (IOException e){
                System.err.println(e);
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
