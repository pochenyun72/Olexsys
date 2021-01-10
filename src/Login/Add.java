package Login;

import Login.TimeDialogTea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add{


	public Add(String id){
		JFrame f = new JFrame("在线考试系统——添加试题");
		f.setSize(610, 540);    //设置窗口大小
		f.setResizable(false);

		/**
		 * logo
		 */
		Image icon = Toolkit.getDefaultToolkit().getImage("src/img/logo.png");
		f.setIconImage(icon);

		//设置窗口在屏幕里居中显示
		int windowWidth = f.getWidth(); // 获得窗口宽
		int windowHeight = f.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		f.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示

		//f.setExtendedState(f.MAXIMIZED_BOTH); //全屏显示
		f.setLayout(null);  //设置总体布局器





//面板p1
		JPanel p = new JPanel();
		// 设置面板p1位置、大小
		p.setBounds(50, 10, 500, 60);
		// 设置面板背景颜色
		//color的构造函数public color （int red ,int green,int blue,int alpha）
		//其中：前三个分量RGB颜色模式中的参数，第四个alpha分量指透明的程度。当alpha分量为255时，表示完全不透明，正常显示；当alpha分量为0时，表示完全透明，前三个分量不起作用，而介于0——255之间的值可以制造出颜色不同的层次效果。
		p.setBackground(new Color(245,255,250,255));

		// 这一句可以没有，因为JPanel默认就是采用的FlowLayout
		p.setLayout(new FlowLayout());  //设置面板布局器

		//题目类型下拉框出现的条目
		JLabel TName = new JLabel("题目类型：");
		String ChoicePanel = "选择题";
		String AnswerPanel = "简答题";
		String Topics[] = { ChoicePanel, AnswerPanel };
		JComboBox<String> ca = new JComboBox<>(Topics);
		p.add(TName);
		p.add(ca);


		//正确选项下拉框出现的条目
		JLabel CName = new JLabel("正确选项：");
		String OptionA = "A";
		String OptionB = "B";
		String OptionC = "C";
		String OptionD = "D";
		String OptionNull = "详见参考答案";
		String Options[] = new String[] { OptionA , OptionB , OptionC , OptionD , OptionNull };
		JComboBox<String> cc = new JComboBox<>(Options);
		p.add(CName);
		p.add(cc);

//选择题输入的面板p2
		JPanel p1 = new JPanel();
		// 设置面板p2位置、大小
		p1.setBounds(50, 70, 500, 450);
		// 设置面板背景颜色
		p1.setBackground(new Color(245,255,250,255));
		//设置面板布局
		p1.setLayout(new FlowLayout());  //设置面板布局器

		/****题目输入****/
		JLabel T = new JLabel("选择题：在下面的输入框中输入题目，形如--DNS服务器和DHCP服务器的作用是（）\n");
		//设置文本域
		JTextArea Tq = new JTextArea();
		Tq.setPreferredSize(new Dimension(500, 100));
		//设置自动换行
		Tq.setLineWrap(true);
		p1.add(T);
		p1.add(Tq);

		/****选项输入****/
		JLabel A1 = new JLabel("A:");
		// 输入框
		JTextField a = new JTextField("");
		a.setText("请输入选项A的内容");
		a.setPreferredSize(new Dimension(465, 30));
		p1.add(A1);
		p1.add(a);
		//添加鼠标事件，使点击文本框后默认文字消失
		a.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1){
					a.setText("");
				}
			}
		});


		JLabel B1 = new JLabel("B:");
		// 输入框
		JTextField b = new JTextField("");
		b.setText("请输入选项B的内容");
		b.setPreferredSize(new Dimension(465, 30));
		p1.add(B1);
		p1.add(b);
		//添加鼠标事件，使点击文本框后默认文字消失
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1){
					b.setText("");
				}
			}
		});


		JLabel C1 = new JLabel("C:");
		// 输入框
		JTextField c = new JTextField("");
		c.setText("请输入选项C的内容");
		c.setPreferredSize(new Dimension(465, 30));
		p1.add(C1);
		p1.add(c);
		//添加鼠标事件，使点击文本框后默认文字消失
		c.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1){
					c.setText("");
				}
			}
		});


		JLabel D1 = new JLabel("D:");
		// 输入框
		JTextField d = new JTextField("");
		d.setText("请输入选项D的内容");
		d.setPreferredSize(new Dimension(465, 30));
		p1.add(D1);
		p1.add(d);
		//添加鼠标事件，使点击文本框后默认文字消失
		d.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1){
					d.setText("");
				}
			}
		});



		/****提交按钮****/
		// 按钮组件
		JButton Submit = new JButton("立即添加");
		// 把按钮加入到主窗体中
		p1.add(Submit);


		//添加事件监听器
		Submit.addActionListener(new ActionListener(){



			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//跳出弹窗
				JOptionPane.showMessageDialog(null, "试题添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);

				//声明Connection对象
				Connection con;
				//驱动程序名
				String driver = "com.mysql.jdbc.Driver";
				//URL指向要访问的数据库名mydata
				String url = "jdbc:mysql://localhost:3306/olexsys";
				//MySQL配置时的用户名
				String user = "root";
				//MySQL配置时的密码
				String password = "123456";
				//遍历查询结果
				try {
					//加载驱动程序
					Class.forName(driver);
					//1.getConnection()方法，连接MySQL数据库！！
					con = DriverManager.getConnection(url,user,password);
					if (!con.isClosed()) {
						System.out.println("数据库连接成功!");
					}

					int qid = 1;
					String qtxt = Tq.getText();
					String A = a.getText();
					String B = b.getText();
					String C = c.getText();
					String D = d.getText();
					String Correctans = cc.getSelectedItem().toString();

					PreparedStatement psql;
					//预处理添加数据
					psql = con.prepareStatement("insert into multiplechoice_db (qtxt,A,B,C,D,Correction) " + "values(?,?,?,?,?,?)");
					psql.setString(1, qtxt);
					psql.setString(2, A);
					psql.setString(3, B);
					psql.setString(4, C);
					psql.setString(5, D);
					psql.setString(6, Correctans);
					psql.executeUpdate();

				} catch(ClassNotFoundException e1) {
					//数据库驱动类异常处理
					System.out.println("数据库连接失败！");
					e1.printStackTrace();
				} catch(SQLException e1) {
					//数据库连接失败异常处理
					e1.printStackTrace();
				}catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}


			}
		});

		/****返回管理界面按钮****/
		// 按钮组件
		JButton return0 = new JButton("返回");
		// 把按钮加入到主窗体中
		p1.add(return0);
		//添加事件监听器,使点击“返回”按钮可返回教师管理界面
		//还没编写！还没编写！还没编写！
		return0.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TimeDialogTea x = new TimeDialogTea();
				int result = x.showDialog(f, "返回成功", 3, id);// TimerTest是程序主窗口类，弹出的对话框10秒后消失
				f.dispose();

			}

		});



//简答题输入的面板p3
		JPanel p2 = new JPanel();
		// 设置面板p3位置、大小
		p2.setBounds(50, 70, 500, 450);
		// 设置面板背景颜色
		p2.setBackground(new Color(245,255,250,255));
		//设置面板布局
		p2.setLayout(new FlowLayout());  //设置面板布局器

		/****简答题题目输入****/
		JLabel T2 = new JLabel("简答题：在下面的输入框中输入题目，形如--DNS服务器和DHCP服务器的作用是？\n");
		//设置文本域
		JTextArea Tq2 = new JTextArea();
		Tq2.setPreferredSize(new Dimension(500, 100));
		//设置自动换行
		Tq2.setLineWrap(true);
		p2.add(T2);
		p2.add(Tq2);

		/****简答题参考答案输入****/
		JLabel ANS2 = new JLabel("简答题参考答案：在下面的输入框中输入简答题参考答案 \n");
		//设置文本域
		JTextArea ans2 = new JTextArea();
		ans2.setPreferredSize(new Dimension(500, 100));
		//设置自动换行
		ans2.setLineWrap(true);
		p2.add(ANS2);
		p2.add(ans2);

		/****提交按钮****/
		// 按钮组件
		JButton Submit2 = new JButton("立即添加");
		// 把按钮加入到主窗体中
		p2.add(Submit2);


		//添加事件监听器
		Submit2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//跳出弹窗
				JOptionPane.showMessageDialog(null, "试题添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);

				//声明Connection对象
				Connection con;
				//驱动程序名
				String driver = "com.mysql.jdbc.Driver";
				//URL指向要访问的数据库名mydata
				String url = "jdbc:mysql://localhost:3306/olexsys";
				//MySQL配置时的用户名
				String user = "root";
				//MySQL配置时的密码
				String password = "123456";
				//遍历查询结果
				try {
					//加载驱动程序
					Class.forName(driver);
					//1.getConnection()方法，连接MySQL数据库！！
					con = DriverManager.getConnection(url,user,password);
					if (!con.isClosed()) {
						System.out.println("数据库连接成功!");
					}


					int qid = 1;
					String qtxt = Tq2.getText();
					String Correctans = ans2.getText();

					PreparedStatement psql;
					//预处理添加数据
					psql = con.prepareStatement("insert into shortanswer_db (qtxt,qans) " + "values(?,?)");
					psql.setString(1, qtxt);
					psql.setString(2, Correctans);
					psql.executeUpdate();

				} catch(ClassNotFoundException e1) {
					//数据库驱动类异常处理
					System.out.println("数据库连接失败！");
					e1.printStackTrace();
				} catch(SQLException e1) {
					//数据库连接失败异常处理
					e1.printStackTrace();
				}catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}


			}

		});

		/****返回管理界面按钮****/
		// 按钮组件
		JButton return2 = new JButton("返回");
		// 把按钮加入到主窗体中
		p2.add(return2);
		//添加事件监听器,使点击“返回”按钮可返回教师管理界面
		//还没编写！还没编写！还没编写！
		/**///写了
		return2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				TimeDialogTea x = new TimeDialogTea();
				int result = x.showDialog(f, "返回成功", 3, id);// TimerTest是程序主窗口类，弹出的对话框10秒后消失
				f.dispose();
			}

		});


//设置面板cards
		JPanel cards; // a panel that uses CardLayout
		cards = new JPanel(new CardLayout());
		// 设置面板cards位置、大小，与要嵌入的p2、p3同位置、同大小
		cards.setBounds(50, 70, 500, 450);
		// 设置面板背景颜色
		cards.setBackground(new Color(245,255,250,255));
		//在面板p2、p3完成选择条件时嵌入面板cards
		cards.add(p1, ChoicePanel);
		cards.add(p2, AnswerPanel);


		//把面板加入总窗口
		f.add(p);
		f.add(cards);

		//设置事件监听接口，获取题目类型以显示相应的输入面板
		ca.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) evt.getItem());
			}
		});



		// 关闭窗体的时候，退出程序
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 让窗体变得可见
		f.setVisible(true);
	}
}

