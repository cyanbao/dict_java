package Windows;

import Database.DBHelper;
import Database.User;

import JudgeEnter.DocumentSizeListener;
import JudgeEnter.RegexDocument;
import JudgeEnter.DocumentSizeFilter;

import javax.swing.text.AbstractDocument;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;


/**
 * Created by Cyan on 2016/11/27.
 */
public class Register extends JFrame implements Windows{
    private static final long serialVersionUID = 2491294229716316338L;
    private JPanel contentPane;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField emailTextField;
    private JLabel usernameTipLabel=new JLabel();
    private JLabel passwordTipLabel = new JLabel();
    private JLabel emailTipLabel = new JLabel();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Register frame = new Register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Register(){
        setTitle("用户注册");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        AbstractDocument doc;

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(usernamePanel);
        JLabel usernameLabel = new JLabel("用户名：    ");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernamePanel.add(usernameLabel);
        usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(12);
        doc = (AbstractDocument) usernameTextField.getDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(20));// 限制文本域内可以输入字符长度为20
        doc.addDocumentListener(new DocumentSizeListener(usernameTipLabel, 20));
        usernamePanel.add(usernameTipLabel);
        //usernameTextField.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        /*用户名提示*/
        JPanel usernameTipsPanel = new JPanel();
        JLabel usernameTips = new JLabel("                           " +
                "用户名仅限英文字符及数字（区分大小写,至少三位）");
        usernameTips.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        usernameTipsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usernameTipsPanel.add(usernameTips);
        contentPane.add(usernameTipsPanel);


        /*输入密码*/
        JPanel passwordPanel1 = new JPanel();
        passwordPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(passwordPanel1);
        JLabel passwordLabel1 = new JLabel("输入密码：");
        passwordLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordPanel1.add(passwordLabel1);
        passwordField1 = new JPasswordField();
        passwordField1.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        passwordField1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField1.setColumns(12);
        passwordPanel1.add(passwordField1);
        doc = (AbstractDocument) passwordField1.getDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(20));// 限制文本域内可以输入字符长度为20
        doc.addDocumentListener(new DocumentSizeListener(passwordTipLabel, 20));
        passwordPanel1.add(passwordTipLabel);
        //JLabel passwordTips = new JLabel("仅限英文字符及数字（区分大小写）");
        //passwordTips.setFont(new Font("微软雅黑", Font.PLAIN, 8));
        //passwordPanel1.add(passwordTips);
        JPanel passwordTipsPanel = new JPanel();
        JLabel passwordTips = new JLabel("                            " +
                "密码仅限英文字符及数字（区分大小写,至少六位）");
        passwordTips.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        passwordTipsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        passwordTipsPanel.add(passwordTips);
        contentPane.add(passwordTipsPanel);

        /*确认密码*/
        JPanel passwordPanel2 = new JPanel();
        passwordPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(passwordPanel2);
        JLabel passwordLabel2 = new JLabel("确认密码：");
        passwordLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordPanel2.add(passwordLabel2);
        passwordField2 = new JPasswordField();
        passwordField2.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        passwordField2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField2.setColumns(12);
        passwordPanel2.add(passwordField2);


        /*邮箱*/
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(emailPanel);
        JLabel emailLabel = new JLabel("输入邮箱：");
        emailLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        emailPanel.add(emailLabel);
        emailTextField = new JTextField();
        emailTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        emailPanel.add(emailTextField);
        emailTextField.setColumns(12);
        doc = (AbstractDocument) emailTextField.getDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(40));// 限制文本域内可以输入字符长度为40
        doc.addDocumentListener(new DocumentSizeListener(emailTipLabel, 40));
        emailPanel.add(emailTipLabel);

        /*按钮板块*/
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        /*提交按钮*/
        JButton submitButton = new JButton("提交");
        submitButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        submitButton.addActionListener(new submitButtonListener());
        buttonPanel.add(submitButton);
        /*退出按钮*/
        JButton cancelButton = new JButton("退出");
        cancelButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        cancelButton.addActionListener(new cancelButtonListener());
        buttonPanel.add(cancelButton);

        //pack();//自动调节窗体大小


    }

    public void refreshRegister(){
        usernameTextField.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
        emailTextField.setText("");
    }

    private class submitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /*非空校验*/
            String input_username=usernameTextField.getText();
            String input_password1 = String.valueOf(passwordField1.getPassword());
            String input_password2 = String.valueOf(passwordField2.getPassword());
            String input_email = emailTextField.getText();
            DBHelper user_db=new DBHelper();
            if (input_username.isEmpty()) {// 判断用户名是否为空
                JOptionPane.showMessageDialog(null, "用户名不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (input_password1.isEmpty()) {// 判断密码是否为空
                JOptionPane.showMessageDialog(null, "密码不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (input_password2.isEmpty()) {// 判断确认密码是否为空
                JOptionPane.showMessageDialog(null, "确认密码不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (input_email.isEmpty()) {// 判断电子邮箱是否为空
                JOptionPane.showMessageDialog(null, "电子邮箱不能为空！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            /*合法性校验*/
            // 校验用户名是否合法
            if (!Pattern.matches("\\w{3,20}", input_username)) {
                JOptionPane.showMessageDialog(null, "请输入合法的用户名！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // 校验两次输入的密码是否相同
            if (!Pattern.matches("\\w{6,20}", input_password1)) {
                JOptionPane.showMessageDialog(null, "密码至少六位！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!input_password1.equals(input_password2)) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不同！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // 校验电子邮箱是否合法
            if (!Pattern.matches("\\w+@\\w+\\.\\w+", input_email)) {
                JOptionPane.showMessageDialog(null, "请输入合法的电子邮箱！", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
// 校验用户名是否存在
            if (user_db.exists(input_username)) {
                JOptionPane.showMessageDialog(null, "用户名已经存在", "警告信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //不存在则注册
            int choose = JOptionPane.showConfirmDialog(null, "确认注册？", "注册确认",JOptionPane.YES_NO_OPTION);
            if(choose==0){
                User register_user = new User(input_username,input_password1,input_email);
                user_db.save(register_user);
                //System.out.println("注册一个新账号");
            }
            else
                return;
            dispose();
            choose = JOptionPane.showConfirmDialog(null, "注册成功，是否登录？", "成功注册",JOptionPane.YES_NO_OPTION);
            if(choose==0){
                login_frame.setVisible(true);
            }
            return;
        }
    }

    private class cancelButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
            return;
        }
    }
}
