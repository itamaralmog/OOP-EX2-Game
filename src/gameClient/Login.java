package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Login extends JFrame implements Runnable, ActionListener {
    private JButton login;
    private JTextArea id, level;
    private JLabel lId,lLevel,mBox;
    private Ex2.SharedLevelBuffer sharedLevelBuffer;
    private Lock lock;
    public Condition condition;
    private Ex2.loginData loginData;

//    public static void main(String[] a) {
//        Ex2.SharedLevelBuffer sharedLevelBuffer=new Ex2.SharedLevelBuffer();
//        Ex2.loginData loginData = new Ex2.loginData();
//        Login login = new Login(1000,700,sharedLevelBuffer,loginData);
//        login.setVisible(true);
//        login.setResizable(true);
//    }

    public Login(double X,double Y, Ex2.SharedLevelBuffer sharedLevelBuffer,Ex2.loginData loginData){
      super();
        this.loginData = loginData;
        this.sharedLevelBuffer = sharedLevelBuffer;
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, (int)(X), (int)(Y));
        Font f =  new Font("TimesRoman",Font.PLAIN,22);
        //Label for id
        lId = new JLabel();
        lId.setFont(f);
        lId.setText("ID:");
        lId.setBounds(40,50,40,24);
        //label for level
        lLevel = new JLabel();
        lLevel.setFont(f);
        lLevel.setText("LEVEL:");
        lLevel.setBounds(40,80,100,24);
        // text area for id
        id = new JTextArea();
        id.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        id.setFont(f);
        id.setBounds(130,50,130,30);
        // text area for level
        level = new JTextArea();
        level.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        level.setFont(f);
        level.setBounds(130,80,130,30);
        //button for login
        login = new JButton("login");
        login.setFont(f);
        login.setBounds(130,110,130,30);
        login.addActionListener(this);
        // label for massage
        mBox = new JLabel();
        mBox.setBounds(130,140,250,30);
        mBox.setFont(f);
        mBox.setForeground(Color.blue);

        add(id);
        add(level);
        add(lId);
        add(lLevel);
        add(login);
        add(mBox);
    }
    @Override
    public void actionPerformed(ActionEvent w) {
        if(w.getSource() == this.login)
        {
            String id = this.id.getText();
            String level = this.level.getText();
            int l;
            // if there no id
            if (id.equals(""))
            {
                this.mBox.setText("need id to login");
                return;
            }else
            {
                this.mBox.setText("");
            }
            //if the id is not integer
            try {
                l= Integer.parseInt(level);
            }
            catch (NumberFormatException exception)
            {
                this.mBox.setFont(new Font("TimesRoman",Font.PLAIN,20));
                this.mBox.setText("The level should be number");
                return;
            }
            this.mBox.setFont(new Font("TimesRoman",Font.PLAIN,24));
            this.mBox.setText("");
            // if the id is not long
            try {
                this.sharedLevelBuffer.setId(Long.parseLong(id));
            } catch (NumberFormatException numberFormatException) {
                this.mBox.setFont(new Font("TimesRoman",Font.PLAIN,20));
                this.mBox.setText("The id is not long");
                return;
            }
            this.sharedLevelBuffer.setLevel(l);
            loginData.done=true;
        }

    }

    @Override
    public void run() {

        setVisible(true);
        this.lock.lock();
        try {
            this.condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            this.lock.unlock();
            setVisible(false);
        }
    }
}
