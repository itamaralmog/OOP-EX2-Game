package test;

import static org.junit.jupiter.api.Assertions.*;

import api.*;
import org.junit.jupiter.api.Test;

public class DWGraph_DS_Test {
    @Test
    void addNode_connect_getEdge(){
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();
        DWGraph_DS g1= new DWGraph_DS();
        g1.addNode(n1);
        g1.connect(n1.getKey(), n2.getKey(), 34);
        assertEquals(1,g1.nodeSize());
        assertNotEquals(1,g1.edgeSize());
        g1.addNode(n2);
        assertNotEquals(1,g1.nodeSize());
        g1.connect(n1.getKey(), n2.getKey(), 34);
        edge_data e = new EdgeData(n1,n2,34,0,"");
        assertEquals(g1.getEdge(n1.getKey(), n2.getKey()).getWeight(),e.getWeight());
    }
    @Test
    void equals(){
        DWGraph_DS g1= new DWGraph_DS();
        DWGraph_DS g2= new DWGraph_DS();
        for(int i = 0 ; i < 11 ; ++i){
            node_data n = new NodeData();
            g1.addNode(n);
            g2.addNode(n);
        }
        for(int i = 0 ; i < 11 ; ++i){
            g1.connect(i,i+1,i*i+i);
            g2.connect(i,i+1,i*i+i);
        }
        for(int i =10 ; i > -1 ; --i){
            g1.connect(i,i-2,i*i+i-i*2);
            g2.connect(i,i-2,i*i+i-i*2);
        }
        assertTrue(g1.equals(g2));
        g2 .removeEdge(0,1);
        assertTrue(!g1.equals(g2));
        System.out.println(g1);
    }

}
