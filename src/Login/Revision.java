package Login;

import background.BackgroundPanel;
import com.MyVFlowLayout.MyVFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Revision {
    public Revision(String id) throws SQLException {
        JdbcUtils.getMultiplechoice();
        JFrame f=new JFrame("在线考试系统——批阅试题");
        f.setLayout(new BorderLayout());
        f.setResizable(false);
        f.setSize(583, 669);

        /**
         * logo
         */
        Image icon = Toolkit.getDefaultToolkit().getImage("src/img/logo.png");
        f.setIconImage(icon);

        Image image = new ImageIcon("src/img/Revision1.jpg").getImage();
        JPanel allPanel = new BackgroundPanel(image);
        allPanel.setLayout(new BorderLayout());
        f.add(allPanel, BorderLayout.CENTER);

        /**
         * 文字样式
         */
        Font font = new Font("宋体", Font.BOLD, 16);

        int windowWidth = f.getWidth(); // 获得窗口宽
        int windowHeight = f.getHeight(); // 获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width; // 获取屏幕的宽
        int screenHeight = screenSize.height; // 获取屏幕的高
        f.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示

        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel(new MyVFlowLayout());
        JPanel p31 = new JPanel();
        JPanel p32 = new JPanel();
        JButton sav=new JButton("保存");
        JButton exi=new JButton("结束阅卷");
        exi.setEnabled(false);
        JLabel ans=new JLabel("标准答案");
        ans.setFont(font);
        JLabel ans2=new JLabel("标准答案");
        ans2.setFont(font);
        JLabel label4=new JLabel("分数：");
        label4.setFont(font);
        JLabel label5=new JLabel("分数：");
        label5.setFont(font);
        JTextField txtfield1=new JTextField(4);
        JTextField txtfield2=new JTextField(4);
        int qid8= JdbcUtils.getQid2("1");
        int qid9= JdbcUtils.getQid3("1");
        String str3= JdbcUtils.getStaAns(9);
        String str4= JdbcUtils.getStuAns(9);
        String str5= JdbcUtils.getStaAns(10);
        String str6= JdbcUtils.getStuAns(10);
        JTextArea txtarea4=new JTextArea(str3,7,40);
        txtarea4.setLineWrap(true);//可换行
        txtarea4.setEditable(false);//不可编辑
        JTextArea txtarea3=new JTextArea(str4,7,50);
        txtarea3.setLineWrap(true);//可换行
        txtarea3.setEditable(false);//不可编辑
        JTextArea txtarea6=new JTextArea(str5,7,40);
        txtarea6.setLineWrap(true);//可换行
        txtarea6.setEditable(false);//不可编辑
        JTextArea txtarea5=new JTextArea(str6,7,50);
        txtarea5.setLineWrap(true);//可换行
        txtarea5.setEditable(false);//不可编辑

        final boolean[] isSaved = {false};
        sav.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((txtfield1.getText().length() != 0) || (txtfield2.getText().length() != 0))
                {
                    JdbcUtils.addScores(9,txtfield1.getText());
                    JdbcUtils.addScores(10,txtfield2.getText());
                    if (!isSaved[0])
                    {
                        exi.setEnabled(true);
                    }
                }
                else //if ((txtfield1.getText() == "") || (txtfield2.getText() == ""))
                {
                    JOptionPane.showMessageDialog(f,"请填写分数","提示",1);
                    isSaved[0] = false;
                }
            }
        });

        /**
         * 透明化
         */
        p1.setBackground(null);
        p1.setOpaque(false);
        p2.setBackground(null);
        p2.setOpaque(false);
        p3.setBackground(null);
        p3.setOpaque(false);
        p31.setBackground(null);
        p31.setOpaque(false);
        p32.setBackground(null);
        p32.setOpaque(false);

        p2.add(sav);
        p2.add(exi);

        p3.add(txtarea3);
        p31.add(ans);
        p31.add(txtarea4);
        p31.add(label4);
        p31.add(txtfield1);
        p3.add(p31);

        p3.add(txtarea5);
        p32.add(ans2);
        p32.add(txtarea6);
        p32.add(label5);
        p32.add(txtfield2);
        p3.add(p32);

        allPanel.add(p1,BorderLayout.NORTH);
        allPanel.add(p3,BorderLayout.CENTER);
        allPanel.add(p2,BorderLayout.SOUTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        exi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JdbcUtils.Jief();
                new Teacher(id);
                f.setVisible(false);
            }
        });
    }

}