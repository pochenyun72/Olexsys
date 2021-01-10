package Login;

import background.BackgroundPanel;
import com.MyVFlowLayout.MyVFlowLayout;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.JOptionPane;



public class Login {
    public Login()
    {
        try
        {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch(Exception e)
        {
            //TODO exception
        }
        //数据库连接
        Connection conn =null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/olexsys?characterEncoding=UTF-8";
            String user = "root";
            String password = "123456";
            conn =DriverManager.getConnection(url,user,password);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }

        //主页面布局
        JFrame f1 = new JFrame("欢迎登陆在线考试系统!");
        f1.setSize(500,320);
        f1.setLocation(580,240);
        f1.setResizable(false); //大小不可改变
        f1.setLayout(new BorderLayout());

        //大字
        JLabel bigLabel = new JLabel("在线考试系统");
        Font bigLabelFont = new Font("行书", Font.BOLD+ Font.ITALIC, 50);
        bigLabel.setFont(bigLabelFont);

        //模块1
        /**
         * 字体
         */
        Font textFont = new Font("正楷", Font.BOLD, 18);

        JPanel p1 = new JPanel();
        JLabel id = new JLabel("您的身份："); //身份选择提示
        id.setFont(textFont);

        String[] ids = new String[]{"学生", "教师"};//下拉框内容
        JComboBox ch = new JComboBox(ids);        //下拉框
        p1.add(id);
        p1.add(ch);
        p1.setBounds(10,20,300,50);
        /**
         * Jpanel透明化
         */
        p1.setBackground(null);
        p1.setOpaque(false);

        //模块2
        JPanel p2 =  new JPanel();
        JLabel acc = new JLabel("账号：");  //账号标签
        acc.setFont(textFont);
        JTextField accnum = new JTextField(35);  //账号输入框

        p2.add(acc);
        p2.add(accnum);
        p2.setBounds(10,80,300,50);
        /**
         * Jpanel透明化
         */
        p2.setBackground(null);
        p2.setOpaque(false);

        JPanel p3 = new JPanel();
        JLabel pass = new JLabel("密码：");   //密码标签
        pass.setFont(textFont);
        JPasswordField password = new JPasswordField(35); //输入密码
        p3.add(pass);
        p3.add(password);
        p3.setBounds(10,140,300,50);
        /**
         * Jpanel透明化
         */
        p3.setBackground(null);
        p3.setOpaque(false);

        Font btnFont = new Font("微软雅黑", Font.PLAIN, 20);
        JButton loginbtn = new JButton("登录");
        loginbtn.setFont(btnFont);
        loginbtn.setBounds(80,200,200,30);
        loginbtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        loginbtn.setPreferredSize(new Dimension(110, 35));

        //按钮事件
        Connection finalConn = conn;
        loginbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(ch.getSelectedItem().toString() == "学生"){

                    try{
                        String id = accnum.getText();
                        String ps = String.valueOf(password.getPassword());
                        System.out.println(id);
                        System.out.println(ps);
                        String sql1 = "select * from studentinfo";
                        PreparedStatement stmt = finalConn.prepareStatement(sql1);
                        ResultSet rs =stmt.executeQuery();
                        boolean flag = false;
                        while(rs.next()){
                            String s1 = rs.getString("account");
                            String s2 = rs.getString("password");
//                            System.out.println(s1);
//                            System.out.println(s2);
                            //判断查询结果
                            if(s1.equals(id) && s2.equals(ps))
                            {
                                flag = true;
                                System.out.println("查询到，登录成功");
                            }else{
                                System.out.println("查询失败");
                            }
                        }
                        if(flag)
                        {
                            TimeDialogStu d = new TimeDialogStu();
                            int result = d.showDialog(f1, "恭喜您，登陆成功", 3, id);// TimerTest是程序主窗口类，弹出的对话框10秒后消失
                        }
                        else{
                            System.out.println("查询失败");
                            JOptionPane.showMessageDialog(null,"账号或者密码错误","请重新输入",JOptionPane.INFORMATION_MESSAGE);
                        }//依据查询结果进行操作

                    }catch(SQLException ex ){
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }//在学生中查找
                else{
                    String id = accnum.getText();
                    String ps = String.valueOf(password.getPassword());
                    String sql2 = "select * from teacherinfo ";
                    try{
                        PreparedStatement stmt = finalConn.prepareStatement(sql2);
                        ResultSet rs =stmt.executeQuery();
                        boolean flag = false;
                        //System.out.println("查询");
                        while(rs.next()){
                            if(rs.getString(1 ).equals(id) &&rs.getString(2).equals(ps))
                            {
                                flag = true;
                                System.out.println("查询到，登录成功");
                            }
                        }
                        if(flag){
                            TimeDialogTea d = new TimeDialogTea();
                            int result = d.showDialog(f1, "恭喜您，登陆成功", 3, id);// TimerTest是程序主窗口类，弹出的对话框10秒后消失
                        }
                        else{
                            System.out.println("查询失败");
                            JOptionPane.showMessageDialog(null,"账号或者密码错误","请重新输入",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }catch(SQLException ex ){
                        ex.printStackTrace();
                    }

                }//在教师中查找
            }
        });

        /**
         * logo
         */
        Image icon = Toolkit.getDefaultToolkit().getImage("src/img/logo.png");
        f1.setIconImage(icon);
        /**
         * 背景图片
         */
        Image image = new ImageIcon("src/img/loginBackground.jpg").getImage();
        JPanel backgroundpanel = new BackgroundPanel(image);
        backgroundpanel.setLayout(new MyVFlowLayout(0, 1, 0, 10, 15, 15, false, false));


        backgroundpanel.add(bigLabel);
        backgroundpanel.add(p1);
        backgroundpanel.add(p2);
        backgroundpanel.add(p3);
        backgroundpanel.add(loginbtn);
        f1.add(backgroundpanel, BorderLayout.CENTER);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Login();
    }
}
