package test;

import api.NodeData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeData_Test {
    @Test
    void getLocation(){
        NodeData n = new NodeData();
        NodeData.Geo p = new NodeData.Geo(1,1,1);
        n.setLocation(p);
        assertEquals(n.getLocation(),p);
    }


}
