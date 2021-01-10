package com;
import Login.Login;
import background.BackgroundPanel;
import com.dao.ExamDao;
import com.dao.impl.ExamDaolmpl;
import com.entity.Exam;
import com.MyVFlowLayout.MyVFlowLayout;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class MyExamination
{
    public MyExamination(String id)
    {
        new Examination(id);
    }
}

//**计时器类**//
class TimeLimit
{
    private JLabel label = new JLabel();
    private long end = 0;
    public TimeLimit() { } //构造函数
    public JLabel showLimit(int examlength, Font font)//倒计时标签
    {
        end = System.currentTimeMillis() + examlength * 1000 * 60;
        java.util.Timer timer=new Timer();
        timer.schedule(
                new TimerTask()
                {
                    //获取剩余的倒计时长
                    public void run()
                    {
                        long sub = end - System.currentTimeMillis();
                        if(sub < 0)
                        {
                            return;
                        }
                        updateTimer(sub, font);
                    }

                },0,1000);
        return label;
    }
    public void updateTimer(long sub, Font font) //计时
    {
        int h = (int)(sub / 1000 / 60 / 60);
        int m = (int)(sub / 1000 / 60 % 60);
        int s = (int)(sub / 1000 % 60);
        String str = h + ":" + m + ":" + s;
        //将String类型转换成Date类型的格式
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date=new Date();
        label.setFont(font);
        try
        {
            date=sdf.parse(str);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //将Date类型的数设置成想要显示的时间格式,并写入JLable中
        label.setText("还剩下" + sdf.format(date) + "  ");
    }
    public void clear(boolean b)
    {
        if (b)
            this.end = 0;
    }
}

//**考试类**//
class Examination
{
    private JFrame examFrame = new JFrame("学生考试界面"); //创建学生考试界面Frame窗口
    /**
     * 占位
     */
    /*试卷id*/
    private String paperId = "1";
    /*学生ID*/
    private String studentId;
    /*考试时间*/
    private int examTime = 30;/**临时*/
    /*是否完成考试*/
    private boolean isFinished = false;
    public String getPaperId()
    {
        return paperId;
    }
    public String getStudentId()
    {
        return studentId;
    }
    public int getExamTime()
    {
        return examTime;
    }
    public boolean getIsFinished()//是否完成
    {
        return isFinished;
    }
    public void setIsFinished(boolean isFinished)
    {
        this.isFinished = isFinished;
    }
    public static void showCustomDialog(Frame owner, Component parentComponent, String title, String text, String yes, String no)
    {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, title, true);
        // 设置对话框的宽高
        dialog.setSize(250, 125);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        // 创建一个标签显示消息内容
        JLabel messageLabel = new JLabel(text);

        // 创建一个按钮用于返回学生界面
        JButton yesBtn = new JButton(yes);
        yesBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new Login();
                // 关闭本界面
                owner.dispose();
            }
        });
        // 创建一个按钮用于关闭对话框
        JButton noBtn = new JButton(no);
        noBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dialog.dispose();
            }
        });

        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
        JPanel panel = new JPanel(new MyVFlowLayout(0, 1, 0, 10, 15, 15, false, false));
        JPanel panel1 = new JPanel();

        // 添加组件到面板
        panel.add(messageLabel);
        panel1.add(yesBtn);
        panel1.add(noBtn);
        panel.add(panel1);
        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);
    }

    public Examination(String id)
    {
        studentId = id;

        /*以下内容为Frame窗口大小设置*/
        examFrame.setBounds(300, 100, 750, 500);    //设置窗口大小和位置
        examFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); //全屏显示
        BorderLayout frameLayout = new BorderLayout(); //创建BorderLayout布局管理器
        examFrame.setLayout(frameLayout); //Frame网格包布局
        /**
         * logo
         */
        Image icon = Toolkit.getDefaultToolkit().getImage("src/img/logo.png");
        examFrame.setIconImage(icon);
        /*Frame布局*/
        //**Frame左布局*/
        Image image = new ImageIcon("src/img/examLeftBg.png").getImage();
        JPanel examPaneLeft = new BackgroundPanel(image);
        examPaneLeft.setLayout(new MyVFlowLayout(0, 1, 0, 65, 15, 15, false, false));
        examPaneLeft.setBackground(Color.gray);//测试用，确定边界
        //**Frame左内容填充*/
        JLabel examPaneLeftLabel = new JLabel("学生界面");
        Font examPaneLeftLabelFont = new Font("行书", Font.BOLD, 24);
        examPaneLeftLabel.setFont(examPaneLeftLabelFont);
        examPaneLeft.add(examPaneLeftLabel);
        //学生信息
        String sex;
        int sexnum = 1;
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/olexsys?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "123456";
            conn =DriverManager.getConnection(url,user,password);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            String sql ="select * from studentinfo";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                //no action
            }
            else{
                if(rs.getString("account") == id)
                    sexnum =Integer.parseInt(rs.getString(3));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(sexnum == 1){
            sex ="男";
        }else{
            sex = "女";
        }

        Font informationFont1 = new Font("正楷", Font.PLAIN, 20);
        Font informationFont2 = new Font("正楷", Font.PLAIN, 16);
        JPanel informationPanel = new JPanel(new MyVFlowLayout(0, 1, 0, 64, 15, 15, false, false));
        /**
         * Jpanel透明化
         */
        informationPanel.setBackground(null);
        informationPanel.setOpaque(false);
        JLabel infoLabel = new JLabel("学生信息");
        infoLabel.setFont(informationFont1);
        JLabel idLabel =new JLabel("用户名："+id);
        idLabel.setFont(informationFont2);
        JLabel sexLabel = new JLabel("性别：" +sex);
        sexLabel.setFont(informationFont2);
        informationPanel.add(infoLabel);
        informationPanel.add(idLabel );
        informationPanel.add(sexLabel);
        examPaneLeft.add(informationPanel);

        //考试与查分
        JButton test = new JButton("考试");
        JButton search = new JButton("查分");
        examPaneLeft.add(test);
        examPaneLeft.add(search);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Chaf(id);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //返回内容集合
//        /**
//         * 占位，无太大作用，相当于空格
//         */
//        JLabel tryLabel1 = new JLabel();
//        examPaneLeft.add(tryLabel1);
//        JLabel tryLabel2 = new JLabel();
//        examPaneLeft.add(tryLabel2);
//        JLabel tryLabel3 = new JLabel();
//        examPaneLeft.add(tryLabel3);
//        JLabel tryLabel4 = new JLabel();
//        examPaneLeft.add(tryLabel4);
//        JLabel tryLabel5 = new JLabel();
//        examPaneLeft.add(tryLabel5);

        JButton ReLoginButton = new JButton("退出学生界面"); //返回
        examPaneLeft.add(ReLoginButton);

        ReLoginButton.addActionListener(new ActionListener()
        {//未完成返回警告
            public void actionPerformed(ActionEvent e)
            {
                if (!getIsFinished())
                {
                    showCustomDialog(examFrame, examFrame, "警告", "您的考试未完成，确认要退出吗？", "确认", "返回");
                }
                else
                {
                    showCustomDialog(examFrame, examFrame, "提示", "恭喜完成考试，欢迎返回学生界面", "确认", "退回");
                }
            }
        });
        /**
         * 插眼 返回学生界面
         */

        //**Frame右布局*/
        JPanel examPaneRight = new JPanel(new BorderLayout());//BorderLayout布局管理器
        //***Frame右上布局*/
        JPanel examPaneRTop = new JPanel(new BorderLayout());
        examPaneRTop.setBackground(Color.lightGray);//测试用，确定边界
        //Frame右上内容填充
        //考试信息
        Font rTopFont = new Font("宋体", Font.PLAIN,16); //右上部分字体及大小
        String examName = "DND"; //暂时这么写
        /*
         *数据库连接examName
         */
        JLabel subjectMessage = new JLabel("  您正在考试的课程为：" + examName); //根据科目显示
        subjectMessage.setFont(rTopFont);
        examPaneRTop.add(subjectMessage, BorderLayout.WEST);

        //计时器
        TimeLimit timeLimit = new TimeLimit();
        /*
        *数据库连接examlength
        */
        examPaneRTop.add(timeLimit.showLimit(0, rTopFont), BorderLayout.EAST);//先加时间，再加字体

        //***Frame右下布局*/
        JPanel OutExamPaneRUnder = new JPanel(new BorderLayout());
        JPanel examPaneRUnder = new JPanel();
        //examPaneRUnder.setBackground(Color.white);//测试用，确定边界
        MyVFlowLayout myVFlowLayoutnew = new MyVFlowLayout(0, 0, 12, 12, 20, 40, true, true);//垂直布局
        myVFlowLayoutnew.setHorizontalAlignment(MyVFlowLayout.TOP);//顶格
        myVFlowLayoutnew.setHorizontalAlignment(MyVFlowLayout.MIDDLE);//中间
        myVFlowLayoutnew.setTopVerticalGap(30);//距顶端30
        myVFlowLayoutnew.setBottomVerticalGap(30);//距底端30
        examPaneRUnder.setLayout(myVFlowLayoutnew);
        JScrollPane scrollPane = new JScrollPane(examPaneRUnder); //设置进度条
        //scrollPane.setBounds(100, 100, 100, 600);
        //examPaneRUnder.setPreferredSize(new Dimension(scrollPane.getWidth() - 50, scrollPane.getHeight()*2));
        OutExamPaneRUnder.add(scrollPane);
        /**///设置panel内容///**/
        //examPaneRUnder.setBounds(0, 0 , 100, 100);
        /**
         *
         */
        /**
         * 选择考试
         */
        int tmpExamTime = this.getExamTime();
        test.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                test.setEnabled(false);
                search.setEnabled(false);

                examPaneRTop.add(timeLimit.showLimit(tmpExamTime, rTopFont), BorderLayout.EAST);//先加时间，再加字体
                ExamDao dao = new ExamDaolmpl();
                List<Exam> choiceList = dao.selectChoice();
                //System.out.println(choiceList); //Test
                JComponent[][] choices = new JComponent[choiceList.size()][7]; //选择题
                ButtonGroup[] group = new ButtonGroup[choiceList.size()];
                String[] choicesAns = new String[choiceList.size()];
                //System.out.println(choiceList.size());//Test
                for (int i = 0; i < choices.length; i++)
                {
                    choices[i][0] = new JLabel((i + 1 ) + ": " + choiceList.get(i).getqTxt());
                    choices[i][1] = new JRadioButton("A " + choiceList.get(i).getA());
                    choices[i][2] = new JRadioButton("B " + choiceList.get(i).getB());
                    choices[i][3] = new JRadioButton("C " + choiceList.get(i).getC());
                    choices[i][4] = new JRadioButton("D " + choiceList.get(i).getD());
                    choices[i][5] = new JLabel();
                    choices[i][6] = new JLabel();

                    group[i] = new ButtonGroup();
                    group[i].add((JRadioButton)choices[i][1]);
                    group[i].add((JRadioButton)choices[i][2]);
                    group[i].add((JRadioButton)choices[i][3]);
                    group[i].add((JRadioButton)choices[i][4]);

                    //传递消息
                    int finalI = i;
                    ((JRadioButton) choices[i][1]).addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            choicesAns[finalI] = "A";
                        }
                    });
                    ((JRadioButton) choices[i][2]).addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            choicesAns[finalI] = "B";
                        }
                    });
                    ((JRadioButton) choices[i][3]).addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            choicesAns[finalI] = "C";
                        }
                    });
                    ((JRadioButton) choices[i][4]).addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            choicesAns[finalI] = "D";
                        }
                    });
                }

                Font rUnderFontChoice = new Font("宋体", Font.PLAIN,17);//设置字体
                //JPanel[] panels = new JPanel[choice.size()];
                for (int i  = 0; i < choices.length; i++)
                {//加入标签 panels[i].add(choiceLabel[i][0-4]);
                    for (int j = 0; j < choices[i].length; j++)
                    {
                        //examPaneRUnder = new JPanel();
                        //panels[i].setPreferredSize(new Dimension(10, 166));
                        //examPaneRUnder.setFont(rTopFont);
                        choices[i][j].setFont(rUnderFontChoice);
                        examPaneRUnder.add(choices[i][j]);
                    }
                    //examPaneRUnder.add(panels[i]);
                }

                //简答题
                List<Exam> testList = dao.selectTest();
                JComponent[][] tests = new JComponent[testList.size()][5];
                JButton[][] testsButton = new JButton[testList.size()][3];
                String[] testAns = new String[testList.size()];//简答题答案
                for (int i = 0; i < tests.length; i++)
                {
                    tests[i][0] = new JTextArea((i + 1) + ":" + testList.get(i).getqTxt());
                    tests[i][1] = new JTextArea(new MaxLengthDocument(300)); //回答
                    tests[i][2] = new JPanel(new FlowLayout(FlowLayout.CENTER, 290, 0));
                    tests[i][3] = new JLabel();
                    tests[i][4] = new JLabel();

                    ((JTextArea)tests[i][0]).setLineWrap(true);//可换行
                    ((JTextArea)tests[i][0]).setEditable(false);//不可编辑
                    tests[i][0].setBackground(new Color(250, 250, 250));
                    ((JTextArea)tests[i][1]).setLineWrap(true);//可换行
                    ((JTextArea)tests[i][1]).setEditable(true);//可编辑
                    ((JTextArea)tests[i][1]).setRows(6);//可见行数
                    tests[i][1].setBackground(Color.white);
                    testsButton[i][0] = new JButton("清空");
                    testsButton[i][1] = new JButton("保存");
                    testsButton[i][2] = new JButton("更新");
                    tests[i][2].add(testsButton[i][0]);
                    tests[i][2].add(testsButton[i][1]);
                    tests[i][2].add(testsButton[i][2]);

                    //传递消息
                    int finalI = i;
                    ((JButton)testsButton[i][0]).addActionListener(new ActionListener()
                    {//清空
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            ((JTextArea)tests[finalI][1]).setText(null);
                            System.out.println("Clear succussfully!");
                        }
                    });
                    ((JButton)testsButton[i][1]).addActionListener(new ActionListener()
                    {//保存
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            testAns[finalI] = ((JTextArea)tests[finalI][1]).getText();
                            ((JTextArea)tests[finalI][1]).setEditable(false);//可编辑
                            tests[finalI][1].setBackground(Color.lightGray);
                            ((JButton)testsButton[finalI][0]).setEnabled(false);//不可用
                            ((JButton)testsButton[finalI][1]).setEnabled(false);//不可用
                        }
                    });
                    ((JButton)testsButton[i][2]).addActionListener(new ActionListener()
                    {//重编辑
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            ((JTextArea)tests[finalI][1]).setEditable(true);//可编辑
                            tests[finalI][1].setBackground(Color.white);
                            ((JButton)testsButton[finalI][0]).setEnabled(true);//不可用
                            ((JButton)testsButton[finalI][1]).setEnabled(true);//不可用
                        }
                    });
                }
                Font rUnderFontTest = new Font("宋体", Font.PLAIN,17);//设置字体
                for (int i  = 0; i < tests.length; i++)
                {//加入标签 panels[i].add(tests[i][0-4]);
                    for (int j = 0; j < tests[i].length; j++)
                    {
                        tests[i][j].setFont(rUnderFontTest);
                        examPaneRUnder.add(tests[i][j]);
                    }
                    //examPaneRUnder.add(panels[i]);
                }

                //提交按钮
                JButton submitAll = new JButton("提交试卷");
                Font submitAllFont = new Font("楷体", Font.BOLD, 24);
                examPaneRUnder.add(submitAll);
                //传入数据库
                String tmpPaperId = getPaperId();
                String tmpStudentId = getStudentId();
                submitAll.addActionListener(new ActionListener()
                {
                    //重编辑
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        new ExamDaolmpl().updateAns(choicesAns, testAns, tmpPaperId, tmpStudentId);

                        Component[] comptents = examPaneRUnder.getComponents();
                        for(int i = 0; i < comptents.length; i++)
                        {//所有组件不可用
                            comptents[i].setEnabled(false);
                        }
                        for (int i = 0; i < testList.size(); i++)
                        {
                            Component[] buttonComptents = tests[i][2].getComponents();
                            for(int j = 0; j < buttonComptents.length; j++)
                            {//所有组件不可用
                                buttonComptents[j].setEnabled(false);
                            }
                        }

                        timeLimit.clear(true); //考试完成，时间停止

                        setIsFinished(true); //提示返回信息

                        //返回学生界面
                        showCustomDialog(examFrame, examFrame, "提示", "恭喜完成考试，欢迎返回学生界面", "确认", "退回");
                    }
                });
            }
        });

        //***Frame右布局加入*/
        examPaneRTop.setPreferredSize(new Dimension(600, 100));
        examPaneRight.add(examPaneRTop, BorderLayout.NORTH);
        OutExamPaneRUnder.setPreferredSize(new Dimension(600, 400));
        examPaneRight.add(OutExamPaneRUnder, BorderLayout.CENTER);

        //*Frame全布局加入*/
        examPaneLeft.setPreferredSize(new Dimension(150, 500));
        examFrame.add(examPaneLeft, BorderLayout.WEST);
        examPaneRight.setPreferredSize(new Dimension(600, 500));
        examFrame.add(examPaneRight, BorderLayout.CENTER);

        /*设置窗口可见*/
        examFrame.setVisible(true);
        /*设置窗口可*/
        examFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Chaf(String id) throws SQLException {
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/olexsys?characterEncoding=UTF-8";
            String user="root";
            String pwd="123456";
            conn= DriverManager.getConnection(url,user,pwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql="select * from paperdone where stuid=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,id);
        ResultSet rs=ps.executeQuery();
        rs.next();
        if(rs.getString(23)==null)
            JOptionPane.showMessageDialog(null, "您的考试尚未进行阅卷或未进行考试");
        else{
            int score=rs.getInt(23);
            JOptionPane.showMessageDialog(null, "您本次考试的分数是："+score);
        }
    }
}

//文本域限制字数与列数
class MaxLengthDocument extends PlainDocument
{
    int maxChars = 300;
    public MaxLengthDocument (int maxChars)
    {
        this.maxChars = maxChars;

    }
    public void insertString(int offset, String s, AttributeSet a)
            throws BadLocationException
    {
        if (this.getLength() + s.length() > this.maxChars)
        {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        System.out.println(this.getLength());
        super.insertString (offset, s, a);
    }
}
