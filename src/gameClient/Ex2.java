package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ex2 implements Runnable{//implements Runnable
        private Login login;
        private static MyFrame1 _win;
        private static Arena _ar;
        private SharedLevelBuffer sharedLevelBuffer;
        private boolean flag=false;
        public static void main(String[] args) {
              SharedLevelBuffer s= new SharedLevelBuffer();
            if(args.length==2){
                try {
                    // java Ex2 208196600 2
                    s.setId(Long.parseLong(args[0]));
                    s.setLevel(Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Thread client = new Thread(new Ex2(s,true));
                client.start();
            }
            Thread client = new Thread(new Ex2());
            client.start();
        }
        public Ex2(SharedLevelBuffer s,boolean flag){
            this.flag=flag;
            this.sharedLevelBuffer=s;
            run();
        }
        public Ex2(){ }
        @Override
        public void run() {
            if(!flag) {
                this.sharedLevelBuffer = new SharedLevelBuffer();
                loginData loginData = new loginData();
                login = new Login(1000, 700, this.sharedLevelBuffer, loginData);
                login.setVisible(true);
                login.repaint();
                while (!loginData.done) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                login.setVisible(false);
            }
            int scenario_num =sharedLevelBuffer.level;

            game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
            game.login(sharedLevelBuffer.id);
            String g = game.getGraph();
            init(game);
            directed_weighted_graph gg= _ar.jsonToGraph(g);
            game.startGame();
            _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
            while(game.isRunning()) {
                moveAgents(game, gg);
                try {
                    _win.repaint();
                    Thread.sleep(100);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("moves="+Arena.moves(game.toString())+" "+"grade="+Arena.grade(game.toString()));
            System.exit(0);
        }
        /**
         * Moves each of the agents along the edge,
         * in case the agent is on a node the next destination (next edge) is chosen by shortestPathDist.
         * @param game
         * @param gg
         * @param
         */
        //the algorithm
        private static void moveAgents(game_service game, directed_weighted_graph gg) {
            dw_graph_algorithms algo = new DWGraph_Algo();
            DWGraph_DS graph_ds = (DWGraph_DS) gg;
                    algo.init(graph_ds);
            String lg = game.move();
            List<CL_Agent> log = Arena.getAgents(lg, gg);
            _ar.setAgents(log);
            String fs =  game.getPokemons();
            List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
            _ar.setPokemons(ffs);
            // choose the pokemon that nearest
            for(int i=0;i<log.size();i++) {
                CL_Agent ag = log.get(i);
                int id = ag.getID();
                int dest = ag.getNextNode();
                int src = ag.getSrcNode();
                double v = ag.getValue();
                int srcF=-1;
                int destF = -1;
                CL_Pokemon pl = null;
                if(dest==-1) {
                    double d =Double.MAX_VALUE;
                    for(CL_Pokemon p : ffs ){
                        Arena.updateEdge(p,gg);
                        if(d>algo.shortestPathDist(src,p.get_edge().getSrc()));
                        pl = p;
                        srcF = p.get_edge().getSrc();
                        destF = p.get_edge().getDest();
                    }
                    Iterator<CL_Pokemon> iter = ffs.iterator();
                    while (iter.hasNext()){
                        CL_Pokemon p = iter.next();
                        if(p==pl){
                            iter.remove();
                        }
                    }
                    List<node_data> listN= algo.shortestPath(src,srcF);
                    if(listN!=null) {
                        for (node_data n : listN) {
                            if (n.getKey() != src) {
                                game.chooseNextEdge(ag.getID(), n.getKey());
                            }
                        }
                    }
                    game.chooseNextEdge(ag.getID(), destF);
                    System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+destF);
                }
            }
        }
        private void init(game_service game) {
            String g = game.getGraph();
            String fs = game.getPokemons();
            directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
            //gg.init(g);
            _ar = new Arena();
            _ar.setGraph(g);
            _ar.setPokemons(Arena.json2Pokemons(game.getPokemons()));
            _win = new MyFrame1("test Ex2",game);
            _win.setSize(1000, 700);
            _win.update(_ar);

            String info = game.toString();
            JSONObject line;
            try {
                line = new JSONObject(info);
                JSONObject ttt = line.getJSONObject("GameServer");
                int rs = ttt.getInt("agents");
                System.out.println(info);
                System.out.println(game.getPokemons());
                int src_node = 0;  // arbitrary node, you should start at one of the pokemon
                ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
                for(CL_Pokemon p : cl_fs) { Arena.updateEdge(p,gg);}
                int [] arrSrc = new int [rs];
                int k = 0;
                for(CL_Pokemon p : cl_fs){
                    if(k<arrSrc.length){
                        arrSrc[k++] = p.get_edge().getSrc();
                    }
                }
                for(int a = 0;a<rs;a++) {
                    //int [] arrSrc = new int [rs];
                    //System.out.println();
                    game.addAgent(arrSrc[a]);
                }
            }
            catch (JSONException e) {e.printStackTrace();}

        }
    public static class SharedLevelBuffer
    {
        private long id;
        private int level;
        public SharedLevelBuffer()
        {
            this(-1,-1);
        }
        public SharedLevelBuffer(long id,int level)
        {
            setId(id);
            setLevel(level);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
    public  static class loginData{
            public boolean done=false;


    }
}