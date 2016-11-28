package Windows;
import Database.DBHelper;
import JudgeEnter.DocumentSizeFilter;
import JudgeEnter.DocumentSizeListener;
import JudgeEnter.RegexDocument;
import Randomdata.Captcha;
import Randomdata.RandomCharData;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Cyan on 2016/11/26.
 */
public class Login extends Main implements Windows{
    private JPanel contentPane;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JTextField validateTextField;
    private String Validate;
    private JLabel img_label;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public Login() {
        setTitle("用户登录");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        /*用户名*/
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(usernamePanel);
        JLabel usernameLabel = new JLabel("用户名：");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernamePanel.add(usernameLabel);
        usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(12);
        usernameTextField.setDocument(new RegexDocument("[0-9a-zA-Z]*"));

        /*登录密码*/
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(passwordPanel);
        JLabel passwordLabel = new JLabel("密码：    ");
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setColumns(12);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordPanel.add(passwordField);

        /*验证码*/
        JPanel validatePanel = new JPanel();
        validatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(validatePanel);
        JLabel validateLabel = new JLabel("验证码：");
        validateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        validatePanel.add(validateLabel);

        validateTextField = new JTextField();
        validateTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        validatePanel.add(validateTextField);
        validateTextField.setColumns(3);
        validateTextField.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        Captcha captcha = new Captcha();
        img_label = new JLabel(new ImageIcon(captcha.getCaptchaImage()));
        Validate = captcha.getValidateStr().toLowerCase();
        validatePanel.add(img_label);
        /*刷新按钮*/
        JButton refreshButton = new JButton("换一张");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        validatePanel.add(refreshButton);
        refreshButton.addActionListener(new refreshButtonListener());

        /*按钮板块*/
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel);
        /*登录按钮*/
        JButton submitButton = new JButton("登录");
        submitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        buttonPanel.add(submitButton);
        submitButton.addActionListener(new submitButtonListener());
        /*注册按钮*/
        JButton registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        buttonPanel.add(registerButton);
        registerButton.addActionListener(new registerButtonListener());
        pack();//自动调节窗体大小


    }
    public void refreshLogin(){
        usernameTextField.setText("");
        passwordField.setText("");
        validateTextField.setText("");
        //换一张新图
        Captcha captcha = new Captcha();
        img_label.setIcon(new ImageIcon(captcha.getCaptchaImage()));
        Validate = captcha.getValidateStr().toLowerCase();

    }
    private class submitButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            DBHelper db = new DBHelper();
            String input_username = usernameTextField.getText();
            String input_password = String.valueOf(passwordField.getPassword());
            String input_validate = validateTextField.getText().toLowerCase();
            /*非空校验*/
            if(input_username.isEmpty()){
                JOptionPane.showMessageDialog(null,"用户名不能为空！","警告信息",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(input_password.isEmpty()){
                JOptionPane.showMessageDialog(null,"密码不能为空！","警告信息",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(input_validate.isEmpty()){
                JOptionPane.showMessageDialog(null,"验证码不能为空！","警告信息",JOptionPane.WARNING_MESSAGE);
                return;
            }

            /*合法性校验*/
            if(!db.exists(input_username)){
                JOptionPane.showMessageDialog(null,"用户名不存在！","警告信息",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!db.check(input_username,input_password)){
                JOptionPane.showMessageDialog(null,"密码错误！","警告信息",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!input_validate.equals(Validate)){
                JOptionPane.showMessageDialog(null,"验证码错误！","警告信息",JOptionPane.WARNING_MESSAGE);
                System.out.println("验证码不正确"+input_validate+Validate+"hhh");
                return;
            }
            /*登陆成功跳转到大界面,还需要修改，销毁原登录页面*/
            JOptionPane.showMessageDialog(null,"登录成功！","提示信息",JOptionPane.PLAIN_MESSAGE);
            main_frame.LoginButton.setVisible(false);
            main_frame.RegisterButton.setVisible(false);
            main_frame.UserButton.setText(input_username);
            main_frame.UserButton.setVisible(true);
            main_frame.LogoutButton.setVisible(true);
            dispose();
        }
    }

    private class registerButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            register_frame.refreshRegister();
            register_frame.setVisible(true);
            dispose();
        }
    }

    private class refreshButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //换一张新图
            Captcha captcha = new Captcha();
            img_label.setIcon(new ImageIcon(captcha.getCaptchaImage()));
            Validate = captcha.getValidateStr().toLowerCase();
        }
    }
}
