package UI;

import JudgeEnter.RegexDocument;
import Messages.*;
import Randomdata.Captcha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Cyan on 2016/12/7.
 */
public class LoginFrame extends DictFrame implements ServerInferface {
    private JPanel ContentPanel = new JPanel();
    private JTextField UsernameTextField = new JTextField();
    private JPasswordField PasswordField = new JPasswordField();
    private JTextField ValidateTextField = new JTextField();
    private String ValidateString;
    private JLabel ImgLabel;
    private JButton RefreshButton = new JButton("换一张");
    private JButton SubmitButton = new JButton("提交");
    private JButton ExitButton = new JButton("退出");


    // private BoxLayout boxLayout = new BoxLayout(ContentPanel,BoxLayout.Y_AXIS);
    public LoginFrame() {
        setTitle("用户登录");
        setContentPane(ContentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(350, 200);
        setLocationRelativeTo(null);

        ContentPanel.setLayout(new BoxLayout(ContentPanel, BoxLayout.PAGE_AXIS));
        SetContentPanel();
        SetListener();

    }

    private void SetContentPanel() {
        AddUsernamePanel();
        AddPasswordPanel();
        AddValidatePanel();
        AddButtonPanel();
    }

    private void AddUsernamePanel() {
        JPanel UsernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(UsernamePanel);
        JLabel UsernameLabel = new JLabel("用户名：");
        UsernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        UsernamePanel.add(UsernameLabel);
        UsernameTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        UsernamePanel.add(UsernameTextField);
        UsernameTextField.setColumns(12);
        /*TODO：设置输入格式*/
        UsernameTextField.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
    }

    private void AddPasswordPanel() {
        JPanel PasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(PasswordPanel);
        JLabel PasswordLabel = new JLabel("密码：    ");
        PasswordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        PasswordPanel.add(PasswordLabel);
        PasswordField.setColumns(12);
        PasswordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        PasswordPanel.add(PasswordField);
    }

    private void AddValidatePanel() {
        JPanel ValidatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(ValidatePanel);
        JLabel ValidateLabel = new JLabel("验证码：");
        ValidateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ValidatePanel.add(ValidateLabel);

        ValidateTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ValidatePanel.add(ValidateTextField);
        ValidateTextField.setColumns(3);

        //生成验证码
        ValidateTextField.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        Captcha captcha = new Captcha();
        ImgLabel = new JLabel(new ImageIcon(captcha.getCaptchaImage()));
        ValidateString = captcha.getValidateStr().toLowerCase();
        ValidatePanel.add(ImgLabel);

        //刷新按钮
        RefreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ValidatePanel.add(RefreshButton);
    }

    private void AddButtonPanel() {
        JPanel ButtonPanel = new JPanel();
        ContentPanel.add(ButtonPanel);
        //提交按钮
        SubmitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ButtonPanel.add(SubmitButton);
        //退出按钮
        ExitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ButtonPanel.add(ExitButton);
    }

    private void SetListener() {
        SubmitButton.addActionListener(new SubmitButtonListener());
        ExitButton.addActionListener(new ExitButtonListener());
        RefreshButton.addActionListener(new RefreshButtonListener());
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /*TODO:将获取消息传送到服务器*/
            String input_username = UsernameTextField.getText();
            String input_password = String.valueOf(PasswordField.getPassword());
            String input_validate = ValidateTextField.getText().toLowerCase();
            //非空校验
            if (input_username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (input_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "密码不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (input_validate.isEmpty()) {
                JOptionPane.showMessageDialog(null, "验证码不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //验证码校验
            if (!input_validate.equals(ValidateString)) {
                JOptionPane.showMessageDialog(null, "验证码错误！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // User user = new User(input_username,input_password);
            List<String> list = new ArrayList<String>();
            list.add(input_username);
            list.add(input_password);
            //System.out.println(input_username+"hhhhhhh\n");
            Message message = new Message(Message.SendLoginMessage, list);
            /*TODO:非法性校验
            * 传到服务器端的数据库，传回信息，判断是否成功登陆*
            * */

            Message recceiveId = Send.SendMessage(message);
          //  int recceiveId = SendLoginMessage(message);/*需要修改*/

            if(recceiveId==null){
                JOptionPane.showMessageDialog(null,"网络错误！","提示信息",JOptionPane.PLAIN_MESSAGE);
                dispose();
                new LoginFrame();
            } else if(recceiveId.getId()==Message.LoginSuccess){
                /* 登陆成功销毁页面*/
                JOptionPane.showMessageDialog(null,"登录成功！","提示信息",JOptionPane.PLAIN_MESSAGE);
                dict_frame.LoginButton.setVisible(false);
                dict_frame.RegisterButton.setVisible(false);
                dict_frame.IDButton.setVisible(true);
                dict_frame.IDButton.setText(input_username);
                dict_frame.LogoutButton.setVisible(true);
                dispose();
                return;
            }
            else if(recceiveId.getId()==Message.NotExistUsername){
                JOptionPane.showMessageDialog(null,"该用户名不存在！","提示信息",JOptionPane.PLAIN_MESSAGE);
                dispose();
                new LoginFrame();
            }
            else if(recceiveId.getId()==Message.PasswordError){
                JOptionPane.showMessageDialog(null,"密码错误！","提示信息",JOptionPane.PLAIN_MESSAGE);
                dispose();
                new LoginFrame();
            }            else{
                JOptionPane.showMessageDialog(null,"未知错误！","提示信息",JOptionPane.PLAIN_MESSAGE);
                dispose();
                new LoginFrame();
            }
        }
    }

    private class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class RefreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //换一张新图
            Captcha captcha = new Captcha();
            ImgLabel.setIcon(new ImageIcon(captcha.getCaptchaImage()));
            ValidateString = captcha.getValidateStr().toLowerCase();
        }
    }

   /* private int SendLoginMessage(Messages message) {
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
                Messages receive;
                if (object != null) {
                    receive = (Messages) object;
                    return receive.getId();
                }
                toSeverObject.close();
                fromSeverObject.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fromSeverObject.close();
                } catch (Exception ex) {
                }
                try {
                    toSeverObject.close();
                } catch (Exception ex) {
                }
                try {
                    socket.close();
                } catch (Exception ex) {
                }
            }
        }
    }*/
}