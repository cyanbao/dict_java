package UI;


import Messages.Client2Server;
import Messages.Message;
import Messages.Send;
import SearchCrawling.WordInfo;
import UI.WordCards.WordCard;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static UI.ServerInferface.dict_frame;

;

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
    private String fontUp = "微软雅黑";
    private JPanel UpPanel = new JPanel();
    private JButton TransButton = new JButton();
    private JButton BookButton = new JButton();
    private JButton UserButton = new JButton();
    private JButton MessageButton = new JButton();

    /*改*/
    protected JButton LoginButton= new JButton();
    protected JButton RegisterButton =new JButton();
    protected JButton LogoutButton= new JButton();
    protected JButton IDButton =new JButton();



    //主模块
    private CardLayout cardLayout = new CardLayout();
    private JPanel CenterPanel = new JPanel();
    //主模块分四个面板
    //1.翻译面板
    private JPanel TransPanel = new JPanel();
    private JButton LastWordButton = new JButton();//上一个查询词
    private JButton NextWordButton = new JButton();//下一个查询词
    private JTextField SearchWordTextField = new JTextField();//查询内容
    private JButton SearchWordButton = new JButton();//翻译单词
    private JButton OnlineSearchButton = new JButton();//在线查找单词按钮（链接百度）
    private LinkedList<String> historyWord=new LinkedList<>();
    private ListIterator<String> historyIt=historyWord.listIterator();

    private String curWord;
    private WordInfo DscrpList[] = new WordInfo[3];

    private JCheckBox icibaCheckBox = new JCheckBox("金山", true);//金山复选框
    private JCheckBox youdaoCheckBox = new JCheckBox("有道", true);//有道复选框
    private JCheckBox bingCheckBox = new JCheckBox("必应", true);//必应复选框

    private JPanel DscrpPanel[] = new JPanel[3];
    private JTextArea DscrpTextArea[] = new JTextArea[3];
    private JButton DscrpLikeButton[] = new JButton[3];
    private JButton DscrpCollectButton[]= new JButton[3];
    private JButton DscrpShareButton[]= new JButton[3];

    //2.单词本面板
    private JPanel BookPanel = new JPanel(new BorderLayout());
    private JButton nextWordCardButton=new JButton();
    private JButton preWordCardButton=new JButton();
    private JButton delWordCardButton=new JButton();
    private JButton shareWordCardButton=new JButton();
    private ArrayList<WordCard> collectwords =new ArrayList<>();//单词卡
    private CardLayout wordCardLayout =new CardLayout();//卡片布局
    private JPanel cardContainPanel = new JPanel();
    private JComboBox wordCardComboBox = new JComboBox();

    //3.用户面板
    private JPanel UserPanel = new JPanel(new BorderLayout());
    private WordCard curShare=null;
    private JComboBox onLineUserComboBox = new JComboBox();
    private JComboBox offLineUserComboBox = new JComboBox();
    private JButton sendMssgButton = new JButton("发送");
    private JTextArea mssgTextArea = new JTextArea();//发送信息
    private CardLayout userCardLayout =new CardLayout();//卡片布局
    private JPanel sendMssgContainPanel = new JPanel();



    //4.消息面板
    private JPanel MessagePanel = new JPanel(new BorderLayout());
    private JPanel rcvMssgPanel = new JPanel(new VerticalFlowLayout());
    private LinkedList<String> rcvMssgList=new LinkedList<String>();

    public DictFrame() {
        setContentPane(ContentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(600, 600);

        //布局
        ContentPanel.setLayout(new BorderLayout(0, 0));
        AddNorthPanel();
        AddCenterPanel();

        //监听
        SetListener();
    }

    private void AddNorthPanel() {
        UpPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        UpPanel.setBackground(new Color(-4270593));
        ContentPanel.add(UpPanel, BorderLayout.NORTH);
        AddUpLeftPanel();
        AddUpRightPanel();
    }

    private void AddUpLeftPanel() {
        JPanel UpLeftPanel = new JPanel();
        UpLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        UpLeftPanel.setBackground(new Color(-4270593));
        UpPanel.add(UpLeftPanel);

        TransButton.setBackground(new Color(-4270593));
        TransButton.setEnabled(true);
        TransButton.setFont(new Font(fontUp, Font.PLAIN, 16));
        TransButton.setText("翻译");
        TransButton.setBorderPainted(false);
        UpLeftPanel.add(TransButton);

        BookButton.setBackground(new Color(-4270593));
        BookButton.setEnabled(true);
        BookButton.setFont(new Font(fontUp, Font.PLAIN, 16));
        BookButton.setText("单词本");
        BookButton.setBorderPainted(false);
        UpLeftPanel.add(BookButton);

        UserButton.setBackground(new Color(-4270593));
        UserButton.setEnabled(true);
        UserButton.setFont(new Font(fontUp, Font.PLAIN, 16));
        UserButton.setText("用户");
        UserButton.setBorderPainted(false);
        UpLeftPanel.add(UserButton);

        MessageButton.setBackground(new Color(-4270593));
        MessageButton.setEnabled(true);
        MessageButton.setFont(new Font(fontUp, Font.PLAIN, 16));
        MessageButton.setText("消息");
        MessageButton.setBorderPainted(false);
        UpLeftPanel.add(MessageButton);
    }

    private void AddUpRightPanel() {
        /*JPanel UpRightPanel = new JPanel();
        UpRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        UpRightPanel.setBackground(new Color(-4270593));
        UpPanel.add(UpRightPanel);

        LoginButton.setBackground(new Color(-4270593));
        LoginButton.setEnabled(true);
        LoginButton.setFont(new Font(fontUp, Font.PLAIN, 16));
        LoginButton.setText("登录");
        LoginButton.setBorderPainted(false);
        UpRightPanel.add(LoginButton);

        RegisterButton.setBackground(new Color(-4270593));
        RegisterButton.setEnabled(true);
        RegisterButton.setFont(new Font(fontUp, Font.PLAIN, 16));
        RegisterButton.setText("注册");
        RegisterButton.setBorderPainted(false);
        UpRightPanel.add(RegisterButton);*/
        JPanel UpRightPanel = new JPanel();
        UpRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        UpRightPanel.setBackground(new Color(-4270593));
        UpPanel.add(UpRightPanel);

        LoginButton.setBackground(new Color(-4270593));
        LoginButton.setEnabled(true);
        LoginButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        LoginButton.setText("登录");
        setVisible(true);
        LoginButton.setBorderPainted(false);
        UpRightPanel.add(LoginButton);

        RegisterButton.setBackground(new Color(-4270593));
        RegisterButton.setEnabled(true);
        RegisterButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        RegisterButton.setText("注册");
        RegisterButton.setVisible(true);
        RegisterButton.setBorderPainted(false);
        UpRightPanel.add(RegisterButton);

        IDButton.setBackground(new Color(-4270593));
        IDButton.setEnabled(true);
        IDButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        IDButton.setVisible(false);
        IDButton.setBorderPainted(false);
        UpRightPanel.add(IDButton);

        LogoutButton.setBackground(new Color(-4270593));
        LogoutButton.setEnabled(true);
        LogoutButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        LogoutButton.setVisible(false);
        LogoutButton.setText("登出");
        LogoutButton.setBorderPainted(false);
        UpRightPanel.add(LogoutButton);
    }

    private void AddCenterPanel() {
        CenterPanel.setLayout(cardLayout);
        ContentPanel.add(CenterPanel, BorderLayout.CENTER);
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

    private void AddTransPanel() {
        CenterPanel.add(TransPanel, "TransPanel");
        TransPanel.setLayout(new BorderLayout());

        JPanel NorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TransPanel.add(NorthPanel, BorderLayout.NORTH);

        Image temp;
        ImageIcon ArrowLeft = new ImageIcon("icon/ArrowLeft.png");
        temp = ArrowLeft.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ArrowLeft = new ImageIcon(temp);
        LastWordButton = new JButton(ArrowLeft);
        LastWordButton.setBackground(new Color(-1513240));
        ImageIcon ArrowRight = new ImageIcon("icon/ArrowRight.png");
        temp = ArrowRight.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ArrowRight = new ImageIcon(temp);
        NextWordButton = new JButton(ArrowRight);
        NextWordButton.setBackground(new Color(-1513240));
        ImageIcon Search = new ImageIcon("icon/Search.png");
        temp = Search.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        Search = new ImageIcon(temp);
        SearchWordButton = new JButton(Search);
        SearchWordButton.setBackground(new Color(-1513240));
        ImageIcon Globe = new ImageIcon("icon/Globe.png");
        temp = Globe.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
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

        JPanel OnlineDscrpPanel = new JPanel(new BorderLayout());//单词解释


        JPanel CenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TransPanel.add(CenterPanel, BorderLayout.CENTER);
        CenterPanel.add(OnlineDscrpPanel);

        JPanel CheckBoxPanel = new JPanel(new FlowLayout());//复选框
        CheckBoxPanel.add(icibaCheckBox);
        CheckBoxPanel.add(youdaoCheckBox);
        CheckBoxPanel.add(bingCheckBox);


        JPanel AllDscrpPanel = new JPanel(new GridLayout(3, 1));


        for (int i = 0; i < 3; i++) {
            DscrpPanel[i] = new JPanel(new BorderLayout());
            DscrpPanel[i].setPreferredSize(new Dimension(550, 150));

            DscrpTextArea[i] = new JTextArea();
            DscrpTextArea[i].setVisible(true);
            DscrpTextArea[i].setEditable(false);
            DscrpTextArea[i].setLineWrap(true);//自动换行
            DscrpTextArea[i].setOpaque(false);//背景透明
            JScrollPane scroll = new JScrollPane();
            scroll = new JScrollPane(DscrpTextArea[i]);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//垂直滚动条自动出现
            scroll.setOpaque(false);//透明
            scroll.getViewport().setOpaque(false);//透明




            ImageIcon DisLike = new ImageIcon("icon/DisLike.png");
            Image image = DisLike.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            DisLike = new ImageIcon(image);
            DscrpLikeButton[i] = new JButton(DisLike);
            DscrpLikeButton[i].setVisible(true);
            DscrpLikeButton[i].setPreferredSize(new Dimension(80, 50));

            ImageIcon NotCol;
            if(collectwords.contains(DscrpList[i]))
                NotCol = new ImageIcon("icon/Collect.png");
            else NotCol = new ImageIcon("icon/NotCollect.png");
            image = NotCol.getImage().getScaledInstance(18, 16, Image.SCALE_DEFAULT);
            NotCol = new ImageIcon(image);
            DscrpCollectButton[i] = new JButton(NotCol);
            DscrpCollectButton[i].setBackground(new Color(-1513240));
            DscrpCollectButton[i].setPreferredSize(new Dimension(80, 50));


            ImageIcon ShareWordCard = new ImageIcon("icon/Share.png");
            image = ShareWordCard.getImage().getScaledInstance(13, 15, Image.SCALE_DEFAULT);
            ShareWordCard = new ImageIcon(image);
            DscrpShareButton[i] = new JButton(ShareWordCard);
            DscrpShareButton[i].setBackground(new Color(-1513240));
            DscrpShareButton[i].setPreferredSize(new Dimension(80, 50));


            DscrpPanel[i].setVisible(false);

            LastWordButton = new JButton(ArrowLeft);
            LastWordButton.setBackground(new Color(-1513240));



            JPanel DscrpButtonPanel = new JPanel(new GridLayout(3,1));
            DscrpButtonPanel.add(DscrpLikeButton[i], BorderLayout.EAST);
            DscrpButtonPanel.add(DscrpCollectButton[i], BorderLayout.EAST);
            DscrpButtonPanel.add(DscrpShareButton[i], BorderLayout.EAST);
            DscrpPanel[i].add(DscrpButtonPanel,BorderLayout.EAST);
            DscrpPanel[i].add(scroll, BorderLayout.CENTER);

            AllDscrpPanel.add(DscrpPanel[i]);
        }


        OnlineDscrpPanel.add(CheckBoxPanel, BorderLayout.NORTH);
        OnlineDscrpPanel.add(AllDscrpPanel, BorderLayout.CENTER);
        OnlineDscrpPanel.setPreferredSize(new Dimension(550, 450));


    }

    private void AddBookPanel() {
        CenterPanel.add(BookPanel, "BookPanel");

        JPanel CardControlPanel=new JPanel(new FlowLayout());

        ImageIcon ArrowLeft = new ImageIcon("icon/ArrowLeft.png");
        Image image = ArrowLeft.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ArrowLeft = new ImageIcon(image);
        preWordCardButton = new JButton(ArrowLeft);
        preWordCardButton.setBackground(new Color(-1513240));

        ImageIcon ArrowRight = new ImageIcon("icon/ArrowRight.png");
        image = ArrowRight.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ArrowRight = new ImageIcon(image);
        nextWordCardButton = new JButton(ArrowRight);
        nextWordCardButton.setBackground(new Color(-1513240));

        ImageIcon DelCard = new ImageIcon("icon/DeleteCard.png");
        image = DelCard.getImage().getScaledInstance(18, 16, Image.SCALE_DEFAULT);
        DelCard = new ImageIcon(image);
        delWordCardButton = new JButton(DelCard);
        delWordCardButton.setBackground(new Color(-1513240));

        ImageIcon ShareWordCard = new ImageIcon("icon/Share.png");
        image = ShareWordCard.getImage().getScaledInstance(13, 15, Image.SCALE_DEFAULT);
        ShareWordCard = new ImageIcon(image);
        shareWordCardButton = new JButton(ShareWordCard);
        shareWordCardButton.setBackground(new Color(-1513240));

        CardControlPanel.add(preWordCardButton);
        CardControlPanel.add(nextWordCardButton);
        CardControlPanel.add(delWordCardButton);
        CardControlPanel.add(shareWordCardButton);
        cardContainPanel.setLayout(wordCardLayout);

        wordCardComboBox.setBorder(BorderFactory.createTitledBorder("单词本"));
        wordCardComboBox.setBackground(new Color(-1513240));
        BookPanel.add(cardContainPanel,BorderLayout.CENTER);
        BookPanel.add(CardControlPanel,BorderLayout.SOUTH);
        BookPanel.add(wordCardComboBox,BorderLayout.NORTH);
    }

    private void AddUserPanel() {
        CenterPanel.add(UserPanel, "UserPanel");
        JPanel chooseUserPanel = new JPanel(new GridLayout(1,2));
        chooseUserPanel.add(onLineUserComboBox);
        chooseUserPanel.add(offLineUserComboBox);
        onLineUserComboBox.setBorder(BorderFactory.createTitledBorder("在线用户"));
        offLineUserComboBox.setBorder(BorderFactory.createTitledBorder("离线用户"));
        chooseUserPanel.setBorder(BorderFactory.createTitledBorder("收件人"));
        UserPanel.add(chooseUserPanel,BorderLayout.NORTH);
        sendMssgContainPanel.setLayout(userCardLayout);//卡片结构
        mssgTextArea.setBorder(BorderFactory.createTitledBorder("编辑信息"));
        UserPanel.add(sendMssgContainPanel,BorderLayout.CENTER);
        JPanel sendButtonPanel = new JPanel(new FlowLayout());
        sendButtonPanel.add(sendMssgButton);
        UserPanel.add(sendButtonPanel,BorderLayout.SOUTH);
    }

    private void AddMessagePanel() {
        CenterPanel.add(MessagePanel, "MessagePanel");
        JScrollPane scroll = new JScrollPane();
        scroll = new JScrollPane(rcvMssgPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//垂直滚动条自动出现
        scroll.setOpaque(false);//透明
        scroll.getViewport().setOpaque(false);//透明
        MessagePanel.add(scroll,BorderLayout.CENTER);
    }

    private void SetListener() {
        setUpListener();
        setTransListener();
        setBookListener();
        setUserListener();
        setMessageListener();
    }
    private void setUpListener(){

        TransButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDscrp();
                cardLayout.show(CenterPanel, "TransPanel");
            }
        });
        BookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardContainPanel.removeAll();
                wordCardComboBox.removeAllItems();

                for(int i = 0; i< collectwords.size(); i++) {
                    cardContainPanel.add(collectwords.get(i).getCardPanel());
                    wordCardComboBox.addItem(collectwords.get(i).getWord());
                }


                cardLayout.show(CenterPanel, "BookPanel");
            }
        });

        UserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CenterPanel, "UserPanel");
                sendMssgContainPanel.removeAll();
                sendMssgContainPanel.add(mssgTextArea,"mssgText");
                userCardLayout.show(sendMssgContainPanel,"mssgText");
                ArrayList<String> on = Client2Server.getOnlineUser();
                ArrayList<String> off = Client2Server.getOfflineUser();
                for(int i=0;i<on.size();i++){
                    onLineUserComboBox.addItem(on.get(i));
                }
                for(int i=0;i<on.size();i++){
                    offLineUserComboBox.addItem(off.get(i));
                }
            }
        });



        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame register_frame = new RegisterFrame();
            }
        });

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame login_frame = new LoginFrame();
            }
        });

        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<String> list = new ArrayList<String>();
                list.add(IDButton.getText());
                Message message = new Message(Message.SendLogoutMessage,list);
                Message receiveId = Send.SendMessage(message);
                if(receiveId.getId()==Message.LogoutSuccess){
                    JOptionPane.showMessageDialog(null,"登出成功！","提示信息",JOptionPane.PLAIN_MESSAGE);
                    dict_frame.LoginButton.setVisible(true);
                    dict_frame.RegisterButton.setVisible(true);
                    dict_frame.IDButton.setVisible(false);
                    dict_frame.LogoutButton.setVisible(false);

                }
                else{
                    JOptionPane.showMessageDialog(null,"登出失败！","提示信息",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

    }
    private void setTransListener(){
        SearchWordTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                curWord=SearchWordTextField.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                curWord=SearchWordTextField.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                curWord=SearchWordTextField.getText();
            }
        });
        SearchWordTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER ) {
                    try {
                        searchWord();
                    } catch (TimeoutException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        SearchWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchWord();
                    historyWord.add(SearchWordTextField.getText());
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }
            }
        });
        LastWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (historyIt.hasNext()) {
                    SearchWordTextField.setText(historyIt.next());
                }
            }
        });
        NextWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(historyIt.hasPrevious()){
                    SearchWordTextField.setText(historyIt.previous());
                }
            }
        });
        icibaCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkBoxSetVisual(e,"金山");
            }
        });
        youdaoCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkBoxSetVisual(e,"有道");
            }
        });
        bingCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkBoxSetVisual(e, "必应");
            }
        });

        for(int i = 0; i<DscrpLikeButton.length;i++){
            DscrpLikeButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index=-1;
                    for(int i = 0;i<DscrpLikeButton.length;i++){
                        if(DscrpLikeButton[i].equals(e.getSource()))
                            index=i;
                    }
                    if(index!=-1) {
                         changeLikeState(index);
                    }
                    Arrays.sort(DscrpList);
                    updateDscrp();
                }
            });
            DscrpCollectButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index=-1;
                    for(int i = 0;i<DscrpCollectButton.length;i++){
                        if(DscrpCollectButton[i].equals(e.getSource()))
                            index=i;
                    }
                    if(index!=-1) {
                        changeCollectState(index);
                    }
                }
            });
            DscrpShareButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index=-1;
                    for(int i = 0;i<DscrpShareButton.length;i++){
                        if(DscrpShareButton[i].equals(e.getSource()))
                            index=i;
                    }
                    if(index!=-1&&DscrpList[index].isFound()) {
                        WordCard tmpCard = new WordCard(DscrpList[index]);
                        shareWordCard(tmpCard);
                    }
                }
            });
        }

    }
    private void setBookListener(){
        preWordCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordCardLayout.previous(cardContainPanel);
            }
        });


        nextWordCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordCardLayout.next(cardContainPanel);
            }
        });

        delWordCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//删除卡片
                collectwords=Client2Server.getUserColList(getUserName());
                int index = -1;
                for(int i = 0; i< collectwords.size(); i++){
                    if(collectwords.get(i).getCardPanel().isVisible())//仅当前卡片可视 找出
                        index = i;
                }
                if(index!=-1){
                    wordCardLayout.next(cardContainPanel);
                    cardContainPanel.remove(collectwords.get(index).getCardPanel());//删除当前卡片
                    collectwords.remove(index);//删除收藏夹单词
                    wordCardComboBox.removeItemAt(index);
                    Client2Server.delUserColeList(getUserName(),collectwords.get(index).getWord());
                }
            }
        });

        shareWordCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = -1;
                for(int i = 0; i< collectwords.size(); i++){
                    if(collectwords.get(i).getCardPanel().isVisible())//仅当前卡片可视 找出
                        index = i;
                }
                if(index!=-1){
                    shareWordCard(collectwords.get(index));
                }

            }
        });

        wordCardComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                wordCardLayout.first(cardContainPanel);
                for(int i =0;i<index;i++)
                    wordCardLayout.next(cardContainPanel);
            }
        });
    }
    private void setUserListener(){
        sendMssgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = onLineUserComboBox.getSelectedIndex();
                String targetUser = (String)onLineUserComboBox.getSelectedItem();//收件人
                if(curWord!=null) {//传单词卡
                    if(Client2Server.sendCard(getUserName(),targetUser,curShare))
                        JOptionPane.showMessageDialog(null,"发送成功！","提示信息",JOptionPane.PLAIN_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "发送失败", "警告信息", JOptionPane.WARNING_MESSAGE);

                }else{//传信息
                    String textMssg=mssgTextArea.getText();
                    if(Client2Server.sendCard(getUserName(),targetUser,textMssg))
                        JOptionPane.showMessageDialog(null,"发送成功！","提示信息",JOptionPane.PLAIN_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "发送失败", "警告信息", JOptionPane.WARNING_MESSAGE);

                }
            }
        });
    }
    private void setMessageListener(){
        MessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CenterPanel, "MessagePanel");
                ArrayList<String> reciveList = Client2Server.recieveMessage(getUserName());
                for(int i=0;i<reciveList.size();){
                    JTextArea tmpMssgPanel = new JTextArea();
                    tmpMssgPanel.setEditable(false);
                    tmpMssgPanel.setBorder(BorderFactory.createTitledBorder(reciveList.get(i)));
                    i++;
                    tmpMssgPanel.setText(reciveList.get(i));
                    i++;
                }
            }
        });

    }
    private String getUserName(){
        if(IDButton.isVisible()){
            return IDButton.getText();
        }
        else return null;
    }

    void changeLikeState(int i){

        if(Client2Server.isLiked(getUserName(),DscrpList[i].getWord(),DscrpList[i].getSrcWeb())) {
            if(Client2Server.setLikedNum(getUserName(),DscrpList[i].getWord(),DscrpList[i].getSrcWeb(),DscrpList[i].getLike()-1)) {
                DscrpList[i].setLike(DscrpList[i].getLike() - 1);
                Image image;
                ImageIcon DisLike = new ImageIcon("icon/DisLike.png");
                image = DisLike.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                DisLike = new ImageIcon(image);
                DscrpLikeButton[i].setIcon(DisLike);
            }
        }else{
            if(Client2Server.setLikedNum(getUserName(),DscrpList[i].getWord(),DscrpList[i].getSrcWeb(),DscrpList[i].getLike()+1)){
                DscrpList[i].setLike(DscrpList[i].getLike() + 1);
                Image image;
                ImageIcon Like = new ImageIcon("icon/Like.png");
                image = Like.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                Like = new ImageIcon(image);
                DscrpLikeButton[i].setIcon(Like);
            }
        }


    }

    void changeCollectState(int i){
        WordCard curCard = new WordCard(DscrpList[i]);
        collectwords=Client2Server.getUserColList(getUserName());
        if(collectwords.contains(curCard)) {//已经收藏单词卡
            if(Client2Server.delUserColeList(getUserName(),curCard.getWord())) {
                Image image;
                ImageIcon NotCol = new ImageIcon("icon/NotCollect.png");//单击取消收藏
                image = NotCol.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                NotCol = new ImageIcon(image);
                DscrpCollectButton[i].setIcon(NotCol);
                collectwords.remove(new WordCard(DscrpList[i]));//从收藏夹删除
            }

        }else{//还没收藏单词
            if(Client2Server.AddUserColeList(getUserName(),curCard)) {
                Image image;
                ImageIcon Col = new ImageIcon("icon/Collect.png");//单击收藏
                image = Col.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                Col = new ImageIcon(image);
                DscrpCollectButton[i].setIcon(Col);
                collectwords.add(new WordCard(DscrpList[i]));//添加进入收藏夹
            }
        }
    }


    void checkBoxSetVisual(ItemEvent e, String srcType) {
        for (int i = 0; i < DscrpPanel.length; i++)
            if (DscrpPanel[i] != null && DscrpList[i] != null && DscrpList[i].getSrcWeb() == srcType) {
                boolean visualFlag = e.getStateChange() == ItemEvent.SELECTED;
                DscrpList[i].setVisual(visualFlag);
            }
        boolean flag = true;
        for (int i=0;i<DscrpList.length;i++)
            if(DscrpList[i]==null)
                flag = false;
        if(flag)
            Arrays.sort(DscrpList);
        updateDscrp();
    }
    void searchWord() throws TimeoutException {
        if(SearchWordTextField.getText().length()<=0) {
            DscrpList[0] = null;
            DscrpList[1] = null;
            DscrpList[2] = null;
        }else {
            curWord = SearchWordTextField.getText();
            try {
                DscrpList[0] = new WordInfo(curWord, "金山");
                DscrpList[1] = new WordInfo(curWord, "有道");
                DscrpList[2] = new WordInfo(curWord, "必应");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            for (int i = 0; i < DscrpList.length; i++) {
                if (DscrpList[i].getSrcWeb() == "金山") {
                    //DscrpPanel[i].setVisible(icibaCheckBox.isSelected());
                    DscrpList[i].setVisual(icibaCheckBox.isSelected());
                } else if (DscrpList[i].getSrcWeb() == "有道") {
                    //DscrpPanel[i].setVisible(youdaoCheckBox.isSelected());
                    DscrpList[i].setVisual(youdaoCheckBox.isSelected());
                } else if (DscrpList[i].getSrcWeb() == "必应") {
                    //DscrpPanel[i].setVisible(bingCheckBox.isSelected());
                    DscrpList[i].setVisual(bingCheckBox.isSelected());
                }
            }

            boolean flag = true;
            for (int i = 0; i < DscrpList.length; i++)
                if (DscrpList[i] == null)
                    flag = false;
            if (flag)
                Arrays.sort(DscrpList);
        }
        updateDscrp();

    }
    void updateDscrp(){
        for(int i=0;i<DscrpList.length;i++) {
            if (DscrpList[i] != null) {
                DscrpTextArea[i].setText("来自"+DscrpList[i].getSrcWeb()+"\n");
                if (DscrpList[i].isFound()) {
                    DscrpTextArea[i].append(DscrpList[i].getWord() + "\n");
                    DscrpTextArea[i].append("英 " + DscrpList[i].getPronUK() + "   美 " + DscrpList[i].getPronUS() + "\n");
                    java.util.List<String> dscrps = DscrpList[i].getDscrp();
                    DscrpTextArea[i].append("释义：\n");
                    for (String d : dscrps) {
                        DscrpTextArea[i].append(d + "\n");
                    }

                } else {
                    java.util.List<String> sggsts = DscrpList[i].getSggst();
                    if (sggsts.size() > 0) {
                        DscrpTextArea[i].append("是否要查找：\n");
                        for (String s : sggsts) {
                            DscrpTextArea[i].append(s + "\n");
                        }
                    } else DscrpTextArea[i].append("未查找到结果\n");
                }

                DscrpLikeButton[i].setText(String.valueOf(Client2Server.likedNum(getUserName(),DscrpList[i].getWord(),DscrpList[i].getSrcWeb())));
                DscrpPanel[i].setVisible(DscrpList[i].isVisual());

            } else {
                DscrpPanel[i].setVisible(false);
            }
            if(Client2Server.isLiked(getUserName(),DscrpList[i].getWord(),DscrpList[i].getSrcWeb())) {
                Image image;
                ImageIcon NotCol = new ImageIcon("icon/Collect.png");
                image = NotCol.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                NotCol = new ImageIcon(image);
                DscrpCollectButton[i].setIcon(NotCol);

            }else{
                Image image;
                ImageIcon Col = new ImageIcon("icon/NotCollect.png");
                image= Col.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                Col = new ImageIcon(image);
                DscrpCollectButton[i].setIcon(Col);
            }

        }
    }

    void shareWordCard(WordCard word){
        if(word!=null) {
            sendMssgContainPanel.removeAll();
            sendMssgContainPanel.add(word.getCardPanel(), "wordCard");
            userCardLayout.show(sendMssgContainPanel, "wordCard");
            cardLayout.show(CenterPanel, "UserPanel");//转到发送信息页面
        }
    }
}
