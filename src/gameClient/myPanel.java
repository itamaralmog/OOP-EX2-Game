package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class myPanel extends JPanel{
    private Arena arena;
    private gameClient.util.Range2Range windoe2frame;
    private game_service game;

    //constructor
    public myPanel(game_service game){
        super();
        this.game=game;
        setLayout(null);
        setBounds(0,0,1000, 700);
        setBackground(Color.white);
        setVisible(true);
        repaint();
    }
    public void update(Arena ar) {
        this.arena = ar;
        updatePanel();
    }
    public void updatePanel() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = arena.getGraph();
        windoe2frame = Arena.w2f(g,frame);
    }

    // To paint the graph and everything
    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        //	updateFrame();
        drawPokemons(g);
        drawGraph(g);
        drawAgants(g);
        drawInfo(g);
        agentData(g);
        scoreBoard(g);

    }
    //Title
    private void drawInfo(Graphics g) {
        java.util.List<String> str = arena.getInfo();
        String dt = "none";
        for(int i=0;i<str.size();i++) {
            g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
        }

    }
    //Graph
    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = arena.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while(iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.blue);
            drawNode(n,5,g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while(itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this.windoe2frame.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
    }
    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = arena.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this.windoe2frame.world2frame(s);
        geo_location d0 = this.windoe2frame.world2frame(d);
        g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }

    private void drawPokemons(Graphics g) {
        java.util.List<CL_Pokemon> fs = arena.getPokemons();
        if(fs!=null) {
            Iterator<CL_Pokemon> itr = fs.iterator();

            while(itr.hasNext()) {

                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();
                int r=10;
                g.setColor(Color.green);
                if(f.getType()<0) {g.setColor(Color.orange);}
                if(c!=null) {

                    geo_location fp = this.windoe2frame.world2frame(c);
                    g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
                    //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);

                }
            }
        }
    }
    private void drawAgants(Graphics g) {
        List<CL_Agent> rs = arena.getAgents();
        //	Iterator<OOP_Point3D> itr = rs.iterator();
        g.setColor(Color.red);
        int i=0;
        while(rs!=null && i<rs.size()) {
            geo_location c = rs.get(i).getLocation();
            int r=8;
            i++;
            if(c!=null) {

                geo_location fp = this.windoe2frame.world2frame(c);
                g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
            }
        }
    }
    // board for every agent data
    private void agentData(Graphics g ){
        int agents = arena.getAgents().size();
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 10));
        int l=0;
        for (int i = 0; i < agents; i++) {
            char[] c = ("agent "+i+" : ").toCharArray();
            g.drawChars(c,0,c.length,9,20+13*l++);
            c = ("speed: "+arena.getAgents().get(i).getSpeed()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*l++);
            c = ("score: "+arena.getAgents().get(i).getValue()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*l++);
            c = ("src: "+arena.getAgents().get(i).getSrcNode()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*l++);
            c = ("dest: "+arena.getAgents().get(i).getNextNode()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*l++);
            l++;
        }
    }
    // board for the game score and time and level
    private void scoreBoard(Graphics g){
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 10));
        char[] c = ("Score Board").toCharArray();
        g.drawChars(c,0,c.length,100,10);

        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 9));
        c = ("time til end:  "+ (int)game.timeToEnd()/1000 +":"+(int)game.timeToEnd()%1000).toCharArray();
        g.drawChars(c,0,c.length,100,20);

        c = ("grade:  "+ (int)Arena.grade(game.toString())).toCharArray();
        g.drawChars(c,0,c.length,100,30);

        c = ("moves:  "+ (int)Arena.moves(game.toString())).toCharArray();
        g.drawChars(c,0,c.length,100,40);

        c = ("logged in:  "+ Arena.isLoggedIn(game.toString())).toCharArray();
        g.drawChars(c,0,c.length,100,50);

        c = ("level:  "+ Arena.gameLevel(game.toString())).toCharArray();
        g.drawChars(c,0,c.length,100,60);


    }




}
