package gameClient;

import javax.swing.*;

import api.game_service;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MyFrame1 extends JFrame {
    private myPanel panel;
    private game_service game;
    private Arena ar;
    private gameClient.util.Range2Range _w2f;

    //constructor
    public MyFrame1(String a,game_service game){
        super(a);
        this.game=game;
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1000, 700);
        setResizable(true);
        setVisible(true);
        panel = new myPanel(this.game);
        add(panel);
        // Make it resizeable
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setSize(getWidth()-10,getHeight()-50);
                panel.updatePanel();
            }
        });
    }

    public void update(Arena ar) {
        this.ar = ar;
        updateFrame();
    }
    private void updateFrame() {
        panel.update(ar);
    }
}
