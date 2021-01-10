package Login;

import background.BackgroundPanel;
import com.MyVFlowLayout.MyVFlowLayout;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Teacher {
    public boolean judge1(int ran) throws SQLException {
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/olexsys?characterEncoding=UTF-8";
            String user="root";
            String pwd="123456";
            conn=DriverManager.getConnection(url,user,pwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql1="select * from multiplechoice_db where qid=?";
        PreparedStatement ps=conn.prepareStatement(sql1);
        ResultSet rs1=null;
        ps.setString(1,String.valueOf(ran));
        rs1=ps.executeQuery();
        if(rs1!=null)return true;
        else return false;
    }
    public boolean judge2(int ran) throws SQLException {
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/olexsys?characterEncoding=UTF-8";
            String user="root";
            String pwd="123456";
            conn=DriverManager.getConnection(url,user,pwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql1="select * from shortanswer_db where qid=?";
        PreparedStatement ps=conn.prepareStatement(sql1);
        ResultSet rs1=null;
        ps.setString(1,String.valueOf(ran));
        rs1=ps.executeQuery();
        if(rs1!=null)return true;
        else return false;
    }
    public void Release() throws SQLException {
        int[] num1=new int[11];
        int[] num2=new int[11];
        int[] used1=new int[107];
        int[] used2=new int[107];
        int t=0;
        while(true){
            int maxx=10,minn=1;
            int ran=(int)(Math.random()*(maxx-minn)+minn);
            if(used1[ran]==0&&judge1(ran)){
                t++;
                num1[t]=ran;
                used1[ran]=1;
            }
            if(t==8)break;
        }
        t=0;
        while(true){
            int maxx=10,minn=1;
            int ran=(int)(Math.random()*(maxx-minn)+minn);
            if(used2[ran]==0&&judge2(ran)){
                t++;
                num2[t]=ran;
                used2[ran]=1;
            }
            if(t==2)break;
        }
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/olexsys?characterEncoding=UTF-8";
            String user="root";
            String pwd="123456";
            conn=DriverManager.getConnection(url,user,pwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql1="insert into paperreleased(qid1,qid2,qid3,qid4,qid5,qid6,qid7,qid8,qid9,qid10) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(sql1);
        for(int i=1;i<=8;i++) ps.setString(i,String.valueOf(num1[i]));
        for(int i=1;i<=2;i++) ps.setString(i+8,String.valueOf(num2[i]));
        ps.executeUpdate();
    }
    public Teacher(String id){

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
            String sql ="select * from teacherinfo";
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



        JFrame f = new JFrame("教师端");
        f.setSize(400,300);
        f.setResizable(false); //大小不可改变
        f.setLocation(580,240);
        f.setLayout(new FlowLayout());

        /**
         * logo
         */
        Image icon = Toolkit.getDefaultToolkit().getImage("src/img/logo.png");
        f.setIconImage(icon);

        Image image1 = new ImageIcon("src/img/Teacher1.png").getImage();
        JPanel p1 = new BackgroundPanel(image1);
        p1.setPreferredSize(new Dimension(f.getWidth() / 4 * 3 - 20, f.getHeight() - 30));
        p1.setLayout(new MyVFlowLayout(0, 0, 12, 12, 20, 20, true, true));

        Font infoLabelFont = new Font("行书", Font.BOLD, 24);
        Font otherLabelFont = new Font("正楷", Font.PLAIN, 16);

        JLabel infoLabel = new JLabel("教师信息");
        infoLabel.setFont(infoLabelFont);
        JLabel idLabel =new JLabel("用户名:"+id);
        JLabel sexLabel = new JLabel("性别："+ sex);
        idLabel.setFont(otherLabelFont);
        sexLabel.setFont(otherLabelFont);

        JPanel p11 = new JPanel(new MyVFlowLayout());
        /**
         * Jpanel透明化
         */
        p11.setBackground(null);
        p11.setOpaque(false);

        JPanel p12 = new JPanel();
        JButton button = new JButton("返回登陆界面");
        p12.add(button);
        button.setUI(new BEButtonUI(). setNormalColor(BEButtonUI.NormalColor.green));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeDialogLogin d = new TimeDialogLogin();
                int result = d.showDialog(f, "您将返回登陆界面", 3);// TimerTest是程序主窗口类，弹出的对话框10秒后消失

            }
        });
        /**
         * Jpanel透明化
        */
        p11.setBackground(null);
        p11.setOpaque(false);

        p1.add(infoLabel);
        p11.add(idLabel);
        p11.add(sexLabel);
        p1.add(p11);
        p1.add(p12);

        Image image2 = new ImageIcon("src/img/Teacher2.png").getImage();
        JPanel p2 = new BackgroundPanel(image2);
        p2.setPreferredSize(new Dimension(f.getWidth() / 4 - 10 , f.getHeight() - 30));
        p2.setLayout(new MyVFlowLayout(0, 1, 0, 60, 28, 28, false, false));

        JButton add = new JButton("添加题目");
        add.setUI(new BEButtonUI(). setNormalColor(BEButtonUI.NormalColor.lightBlue));
        JButton revision = new JButton("批改试卷");
        revision.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        JButton release = new JButton("发布试卷");
        release.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));

        p2.add(add);
        p2.add(revision);
        p2.add(release);


        f.add(p1);
        f.add(p2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Add(id);
                f.dispose();
            }
        });

        revision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Revision(id);
                    f.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        release.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Release();
                    JOptionPane.showMessageDialog(null,"试卷发布成功！","试卷发布成功",JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
