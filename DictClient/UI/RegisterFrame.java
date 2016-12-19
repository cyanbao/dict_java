package UI;

import JudgeEnter.DocumentSizeFilter;
import JudgeEnter.DocumentSizeListener;
import JudgeEnter.RegexDocument;
import Messages.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Cyan on 2016/12/7.
 */
public class RegisterFrame extends DictFrame implements ServerInferface {
    private JPanel ContentPanel = new JPanel();
    private JTextField UsernameTextField = new JTextField();
    private JPasswordField Password1Field = new JPasswordField();
    private JPasswordField Password2Field = new JPasswordField();
    private JTextField EmailTextField = new JTextField();
    AbstractDocument doc;
    private JButton SubmitButton = new JButton("提交");
    private JButton ExitButton = new JButton("退出");

    public RegisterFrame() {
        setTitle("用户注册");
        setContentPane(ContentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(350, 300);
        setLocationRelativeTo(null);

        ContentPanel.setLayout(new BoxLayout(ContentPanel, BoxLayout.PAGE_AXIS));
        SetContentPanel();
        SetListener();

    }

    private void SetContentPanel() {
        //用户名
        AddUsernamePanel();
        //密码
        AddPassword1Panel();
        AddPassword2Panel();
        //邮箱
        AddEmailPanel();
        //注册&退出按钮
        AddButtonPanel();
    }

    private void AddUsernamePanel() {
        JPanel UsernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(UsernamePanel);
        JLabel UsernameLabel = new JLabel("用户名：    ");
        UsernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        UsernamePanel.add(UsernameLabel);
        UsernameTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        UsernamePanel.add(UsernameTextField);
        UsernameTextField.setColumns(12);
        /*TODO：设置输入长度*/
        doc = (AbstractDocument) UsernameTextField.getDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(20));// 限制文本域内可以输入字符长度为20
        JLabel UsernameTipLabel = new JLabel();
        doc.addDocumentListener(new DocumentSizeListener(UsernameTipLabel, 20));
        UsernamePanel.add(UsernameTipLabel);
        //usernameTextField.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        /*用户名格式提示*/
        JPanel UsernameTipsPanel = new JPanel();
        JLabel UsernameTips = new JLabel("                           " +
                "用户名仅限英文字符及数字（区分大小写,至少三位）");
        UsernameTips.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        UsernameTipsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        UsernameTipsPanel.add(UsernameTips);
        ContentPanel.add(UsernameTipsPanel);
    }

    private void AddPassword1Panel() {
        JPanel Password1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(Password1Panel);
        JLabel Label = new JLabel("输入密码：");
        Label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        Password1Panel.add(Label);
        Password1Field.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        Password1Field.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        Password1Field.setColumns(12);
        Password1Panel.add(Password1Field);
        doc = (AbstractDocument) Password1Field.getDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(20));// 限制文本域内可以输入字符长度为20
        JLabel CountLabel = new JLabel();
        doc.addDocumentListener(new DocumentSizeListener(CountLabel, 20));
        Password1Panel.add(CountLabel);
        JPanel TipsPanel = new JPanel();
        JLabel TipsLabel = new JLabel("                            " +
                "密码仅限英文字符及数字（区分大小写,至少六位）");
        TipsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        TipsLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        TipsPanel.add(TipsLabel);
        ContentPanel.add(TipsPanel);
    }

    private void AddPassword2Panel() {
        JPanel Password2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(Password2Panel);
        JLabel Password2Label = new JLabel("确认密码：");
        Password2Label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        Password2Panel.add(Password2Label);
        Password2Field.setDocument(new RegexDocument("[0-9a-zA-Z]*"));
        Password2Field.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        Password2Field.setColumns(12);
        Password2Panel.add(Password2Field);
    }

    private void AddEmailPanel() {
        JPanel EmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ContentPanel.add(EmailPanel);
        JLabel EmailLabel = new JLabel("输入邮箱：");
        EmailLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        EmailPanel.add(EmailLabel);
        EmailTextField = new JTextField();
        EmailTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        EmailPanel.add(EmailTextField);
        EmailTextField.setColumns(12);
        doc = (AbstractDocument) EmailTextField.getDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(40));// 限制文本域内可以输入字符长度为40
        JLabel EmailTipLabel = new JLabel();
        doc.addDocumentListener(new DocumentSizeListener(EmailTipLabel, 40));
        EmailPanel.add(EmailTipLabel);

    }

    private void AddButtonPanel() {
        JPanel ButtonPanel = new JPanel();
        ContentPanel.add(ButtonPanel);
        ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.LINE_AXIS));
         /*提交按钮*/
        SubmitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ButtonPanel.add(SubmitButton);
        /*退出按钮*/
        ExitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        ButtonPanel.add(ExitButton);
    }


    private void SetListener() {
        SubmitButton.addActionListener(new SubmitButtonListener());
        ExitButton.addActionListener(new ExitButtonListener());
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input_username = UsernameTextField.getText();
            String input_password1 = String.valueOf(Password1Field.getPassword());
            String input_password2 = String.valueOf(Password2Field.getPassword());
            String input_email = EmailTextField.getText();
         /*非空校验*/
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
        /*TODO:数据库校验*/

            int choose = JOptionPane.showConfirmDialog(null, "确认注册？", "注册确认", JOptionPane.YES_NO_OPTION);
            if (choose == 0) {
                List<String> list = new ArrayList<String>();
                list.add(input_username);
                list.add(input_password1);
                list.add(input_email);
                Message message = new Message(Message.SendRegisterMessage, list);
                Message receiveId = Send.SendMessage(message);
                if (receiveId.getId() == Message.RegisterSuccess) {
                    dispose();
                    choose = JOptionPane.showConfirmDialog(null, "注册成功，是否登录？", "成功注册", JOptionPane.YES_NO_OPTION);
                    if (choose == 0) {
                        new LoginFrame();
                    }
                    return;
                } else if (receiveId.getId() == Message.ExistUsername) {
                    JOptionPane.showMessageDialog(null, "该用户名已存在！", "提示信息", JOptionPane.PLAIN_MESSAGE);
                    dispose();
                    new RegisterFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "未知错误！", "提示信息", JOptionPane.PLAIN_MESSAGE);
                    dispose();
                    new RegisterFrame();
                }
            }
            else return;
        }
    }

    private class ExitButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }

}
