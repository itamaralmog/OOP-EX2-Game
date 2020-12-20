package test;


import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import api.node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DWGraphAlgo_Test {
    @Test
    void save_load() {
        DWGraph_DS g1 = new DWGraph_DS();
        DWGraph_DS g2 = new DWGraph_DS();
        for (int i = 0; i < 11; ++i) {
            node_data n = new NodeData();
            g1.addNode(n);
            g2.addNode(n);
        }
        for (int i = 0; i < 11; ++i) {
            g1.connect(i, i + 1, i * i + i);
            g2.connect(i, i + 1, i * i + i);
        }
        for (int i = 10; i > -1; --i) {
            g1.connect(i, i - 2, i * i + i - i * 2);
            g2.connect(i, i - 2, i * i + i - i * 2);
        }
        DWGraph_Algo algo = new DWGraph_Algo();
        algo.init(g1);
        algo.save("g1saves");
        algo.load("g1saves");
        assertTrue(g1.equals(g2));
        DWGraph_DS g3 = (DWGraph_DS)algo.getGraph();
        assertTrue(g3.equals(g2));
    }
}
