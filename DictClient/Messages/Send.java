package Messages;

import UI.ServerInferface;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Cyan on 2016/12/18.
 */
public class Send implements ServerInferface{
    public static Message SendMessage(Message message) {
        while (true) {
            Socket socket = null;
            ObjectOutputStream toSeverObject = null;
            ObjectInputStream fromSeverObject = null;
            //将客户端的对象数据流输出到服务器端去
            try {
                socket = new Socket(IP_ADDR, PORT);
                //使用ObjectOutputStream和ObjectInputStream进行对象数据传输
                toSeverObject = new ObjectOutputStream(socket.getOutputStream());
                fromSeverObject = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                //将客户端的对象数据流输出到服务器端去
                toSeverObject.writeObject(message);
                toSeverObject.flush();
                //读取服务器端返回的对象数据流
                Object object = fromSeverObject.readObject();
                Message receive;
                if (object != null) {
                    receive = (Message) object;
                    return receive;
                }
                toSeverObject.close();
                fromSeverObject.close();
            } catch (ConnectException e) {
                //e.printStackTrace();
                return null;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            finally {
                try {
                    fromSeverObject.close();
                } catch (Exception ex) {
                    return null;
                }
                try {
                    toSeverObject.close();
                } catch (Exception ex) {
                    return null;
                }
                try {
                    socket.close();
                } catch (Exception ex) {
                    return null;
                }
            }
        }
    }
}
