package api;

import java.io.Serializable;
import java.util.Comparator;
// Class for the priority queue
public class NodeCompareForQueue implements Comparator<NodeTag>, Serializable {
    @Override
    public int compare(NodeTag o1, NodeTag o2) {
        if(o1.getTag()<o2.getTag()) return -1;
        if(o1.getTag()>o2.getTag()) return 1;
        return 0;
    }
}
