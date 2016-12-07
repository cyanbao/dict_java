package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 样例图
 * -----------------------------------------------------------
 *  UpLeftPanel:翻译/单词本/用户/消息 | UpRightPanel:搜索/网络       <---UpPanel
 * -----------------------------------------------------------
 *
 *
 *CenterPanel：包括四个card
 *
 *
 * --------------------------------------------------
 */


/**
 * Created by Cyan on 2016/12/6.
 */
public class DictFrame extends JFrame {
    private JPanel ContentPanel = new JPanel();
    //第一栏
    private JPanel UpPanel=new JPanel();
    private JButton TransButton = new JButton();
    private JButton BookButton = new JButton();
    private JButton UserButton = new JButton();
    private JButton MessageButton =new JButton();
    private JButton LoginButton= new JButton();
    private JButton LogoutButton =new JButton();

    //主模块
    private CardLayout cardLayout =new CardLayout();
    private JPanel CenterPanel = new JPanel();
    //主模块分四个面板
    //1.翻译面板
    private JPanel TransPanel = new JPanel();
    private JButton LastWordButton = new JButton();//上一个查询词
    private  JButton NextWordButton = new JButton();//下一个查询词
    private JTextField SearchWordTextField = new JTextField();//查询内容
    private JButton SearchWordButton =  new JButton();//翻译单词
    private JButton OnlineSearchButton = new JButton();//在线查找单词按钮（链接百度）

    //2.单词本面板
    private JPanel BookPanel = new JPanel();

    //3.用户面板
    private JPanel UserPanel = new JPanel();

    //4.消息面板
    private JPanel MessagePanel = new JPanel();

    public DictFrame(){
        JFrame MainFrame = new JFrame();
        MainFrame.setContentPane(ContentPanel);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.pack();
        MainFrame.setVisible(true);
        MainFrame.setSize(600,600);

        //布局
        ContentPanel.setLayout(new BorderLayout(0,0));
        AddNorthPanel();
        AddCenterPanel();

        //监听
        SetListener();
    }

     private void AddNorthPanel(){
         UpPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
         UpPanel.setBackground(new Color(-4270593));
         ContentPanel.add(UpPanel,BorderLayout.NORTH);
         AddUpLeftPanel();
         AddUpRightPanel();
     }

    private void AddUpLeftPanel(){
        JPanel UpLeftPanel = new JPanel();
        UpLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        UpLeftPanel.setBackground(new Color(-4270593));
        UpPanel.add(UpLeftPanel);

        TransButton.setBackground(new Color(-4270593));
        TransButton.setEnabled(true);
        TransButton.setFont(new Font(TransButton.getFont().getName(), TransButton.getFont().getStyle(), 16));
        TransButton.setText("翻译");
        TransButton.setBorderPainted(false);
        UpLeftPanel.add(TransButton);

        BookButton.setBackground(new Color(-4270593));
        BookButton.setEnabled(true);
        BookButton.setFont(new Font(BookButton.getFont().getName(), BookButton.getFont().getStyle(), 16));
        BookButton.setText("单词本");
        BookButton.setBorderPainted(false);
        UpLeftPanel.add(BookButton);

        UserButton.setBackground(new Color(-4270593));
        UserButton.setEnabled(true);
        UserButton.setFont(new Font(UserButton.getFont().getName(), UserButton.getFont().getStyle(), 16));
        UserButton.setText("用户");
        UserButton.setBorderPainted(false);
        UpLeftPanel.add(UserButton);

        MessageButton.setBackground(new Color(-4270593));
        MessageButton.setEnabled(true);
        MessageButton.setFont(new Font(MessageButton.getFont().getName(), MessageButton.getFont().getStyle(), 16));
        MessageButton.setText("消息");
        MessageButton.setBorderPainted(false);
        UpLeftPanel.add(MessageButton);
    }

    private void AddUpRightPanel(){
        JPanel UpRightPanel = new JPanel();
        UpRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        UpRightPanel.setBackground(new Color(-4270593));
        UpPanel.add(UpRightPanel);

        LoginButton.setBackground(new Color(-4270593));
        LoginButton.setEnabled(true);
        LoginButton.setFont(new Font(LoginButton.getFont().getName(), LoginButton.getFont().getStyle(), 16));
        LoginButton.setText("登录");
        LoginButton.setBorderPainted(false);
        UpRightPanel.add(LoginButton);

        LogoutButton.setBackground(new Color(-4270593));
        LogoutButton.setEnabled(true);
        LogoutButton.setFont(new Font(LogoutButton.getFont().getName(), LogoutButton.getFont().getStyle(), 16));
        LogoutButton.setText("注册");
        LogoutButton.setBorderPainted(false);
        UpRightPanel.add(LogoutButton);
    }

    private void AddCenterPanel(){
        CenterPanel.setLayout(cardLayout);
        ContentPanel.add(CenterPanel,BorderLayout.CENTER);
        AddTransPanel();
        AddBookPanel();
        AddUserPanel();
        AddMessagePanel();
        /*
        JLabel tmp1 = new JLabel();
        tmp1.setText("翻译");TransPanel.add(tmp1);
        JLabel tmp2 = new JLabel();
        tmp2.setText("单词本");
        JLabel tmp3 = new JLabel();
        tmp3.setText("用户");
        JLabel tmp4 = new JLabel();
        tmp4.setText("消息");

        BookPanel.add(tmp2);
        UserPanel.add(tmp3);
        MessagePanel.add(tmp4);*/
    }

    private void AddTransPanel(){
        CenterPanel.add(TransPanel,"TransPanel");
        TransPanel.setLayout(new BorderLayout());

        JPanel NorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TransPanel.add(NorthPanel,BorderLayout.NORTH);

        Image temp;
        ImageIcon ArrowLeft = new ImageIcon("icon/ArrowLeft.png");
        temp= ArrowLeft.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ArrowLeft = new ImageIcon(temp);
        LastWordButton = new JButton(ArrowLeft);
        LastWordButton.setBackground(new Color(-1513240));
        ImageIcon ArrowRight = new ImageIcon("icon/ArrowRight.png");
        temp =ArrowRight.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ArrowRight = new ImageIcon(temp);
        NextWordButton = new JButton(ArrowRight);
        NextWordButton.setBackground(new Color(-1513240));
        ImageIcon Search = new ImageIcon("icon/Search.png");
        temp =Search.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        Search = new ImageIcon(temp);
        SearchWordButton = new JButton(Search);
        SearchWordButton.setBackground(new Color(-1513240));
        ImageIcon Globe = new ImageIcon("icon/Globe.png");
        temp =Globe.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        Globe = new ImageIcon(temp);
        OnlineSearchButton = new JButton(Globe);
        OnlineSearchButton.setBackground(new Color(-1513240));

        SearchWordTextField.setColumns(20);
        SearchWordTextField.setFont(new Font(TransButton.getFont().getName(), TransButton.getFont().getStyle(), 16));
        NorthPanel.add(LastWordButton);
        NorthPanel.add(NextWordButton);
        NorthPanel.add(SearchWordTextField);
        NorthPanel.add(SearchWordButton);
        NorthPanel.add(OnlineSearchButton);

    }

    private void AddBookPanel(){
        CenterPanel.add(BookPanel,"BookPanel");
    }

    private void AddUserPanel(){
        CenterPanel.add(UserPanel,"UserPanel");
    }
    private void AddMessagePanel(){
        CenterPanel.add(MessagePanel,"MessagePanel");
    }
    private void SetListener(){
        TransButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CenterPanel,"TransPanel");
            }
        });

        BookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CenterPanel,"BookPanel");
            }
        });

        UserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CenterPanel,"UserPanel");
            }
        });

        MessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CenterPanel,"MessagePanel");
            }
        });
    }
}
