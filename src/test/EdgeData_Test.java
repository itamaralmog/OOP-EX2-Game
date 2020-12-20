package test;

import api.EdgeData;
import api.NodeData;
import api.edge_data;
import api.node_data;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class EdgeData_Test {
    @Test
    void getSrc_Dest_Equals(){
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();
        edge_data e1 = new EdgeData(n1,n2,3.453422,0,"");
        edge_data e2 = new EdgeData(n1,n2,3.453422,0,"");
        assertEquals(n1.getKey(),e1.getSrc());
        assertEquals(n2.getKey(),e1.getDest());
        assertEquals(3.453422,e1.getWeight());
        assertEquals(e1,e2);
        assertTrue(e1.equals(e2));
    }
}
