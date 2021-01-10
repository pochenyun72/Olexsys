package Login;

import com.MyExamination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeDialogStu {
    private String message = null;
    private int secends = 0;
    private JLabel label = new JLabel();
    private JButton confirm;
    private JDialog dialog = null;
    int result = -5;
    public int  showDialog(JFrame father, String message, int sec, String id) {

        final boolean[] isPoshed = {false};
        this.message = message;
        secends = sec;
        label.setText(message);
        label.setBounds(80,6,200,20);
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        confirm = new JButton("进入");
        confirm.setBounds(100,40,60,20);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = 0;
                isPoshed[0] = true;
                MyExamination student = new MyExamination(id);
                TimeDialogStu.this.dialog.dispose();
                father.dispose();
            }
        });

        dialog = new JDialog(father, true);
        dialog.setTitle("提示: 将在"+secends+"秒后自动跳转入主界面");
        dialog.setLayout(null);
        dialog.add(label);
        dialog.add(confirm);
        s.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                TimeDialogStu.this.secends--;
                if(TimeDialogStu.this.secends == 0)
                {
                    try {
                        if (!isPoshed[0])
                        {
                            MyExamination student = new MyExamination(id);
                            father.dispose();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TimeDialogStu.this.dialog.dispose();
                }else {
                    dialog.setTitle("提示: 本窗口将在"+secends+"秒后自动关闭");
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
        dialog.pack();
        dialog.setSize(new Dimension(270,100));
        dialog.setLocationRelativeTo(father);
        dialog.setResizable(false); //大小不可改变
        dialog.setVisible(true);
        return result;
    }
}