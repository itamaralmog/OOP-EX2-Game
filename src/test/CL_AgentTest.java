package test;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.NodeData;
import api.game_service;
import com.google.gson.Gson;
import gameClient.Arena;
import gameClient.CL_Agent;

import static org.junit.jupiter.api.Assertions.*;

class CL_AgentTest {

    @org.junit.jupiter.api.Test
    void update()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Arena ar = new Arena();
        ar.setGraph(g.getGraph());
        DWGraph_DS graph_ds = (DWGraph_DS)ar.getGraph();
        CL_Agent agent1 = new CL_Agent(graph_ds,4);
        CL_Agent agent2 = new CL_Agent(graph_ds,5);
        agent2.update(agent1.toJSON());
        assertEquals(agent1.getSrcNode(),agent2.getSrcNode());
    }


    @org.junit.jupiter.api.Test
    void setNextNode()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Arena ar = new Arena();
        ar.setGraph(g.getGraph());
        DWGraph_DS graph_ds = (DWGraph_DS)ar.getGraph();
        CL_Agent agent1 = new CL_Agent(graph_ds,5);
        assertEquals(agent1.getNextNode(),-1);
    }

    @org.junit.jupiter.api.Test
    void setCurrNode()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Arena ar = new Arena();
        ar.setGraph(g.getGraph());
        DWGraph_DS graph_ds = (DWGraph_DS)ar.getGraph();
        CL_Agent agent1 = new CL_Agent(graph_ds,0);
        agent1.setCurrNode(5);
        assertEquals(agent1.getSrcNode(),5);
    }


    @org.junit.jupiter.api.Test
    void getValue() {
        game_service g = Game_Server_Ex2.getServer(0);
        Arena ar = new Arena();
        ar.setGraph(g.getGraph());
        DWGraph_DS graph_ds = (DWGraph_DS)ar.getGraph();
        CL_Agent agent1 = new CL_Agent(graph_ds,5);
        assertEquals(0,agent1.getValue());
    }


    @org.junit.jupiter.api.Test
    void getSpeed_setSpeed()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Arena ar = new Arena();
        ar.setGraph(g.getGraph());
        DWGraph_DS graph_ds = (DWGraph_DS)ar.getGraph();
        CL_Agent agent1 = new CL_Agent(graph_ds,5);
        agent1.setSpeed(1);
        assertEquals(agent1.getSpeed(),1);
    }

}