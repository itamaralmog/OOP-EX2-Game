package api;

import java.util.Collection;
import java.util.HashMap;
// Class for the tag because someone thing to make it integer!!!!
public class NodeTag {
    private HashMap<Integer,edge_data> neighbors;
    private double tag;
    private int key;
    public NodeTag(int key, double tag, Collection<edge_data> nei){
        this.key = key;
        this.tag=tag;
        HashMap<Integer,edge_data> n=new HashMap<>();
        for(edge_data i: nei){
            n.put(i.getDest(),i);
        }
        this.neighbors=n;
    }
    public int getKey() {
        return key;
    }
    public double getTag() {
        return tag;
    }
    public void setTag(double t){
        tag=t;
    }
    public Collection<edge_data> getE(){
        return this.neighbors.values();
    }
    public edge_data getNeighbor(int dest){
        return neighbors.get(key);
    }

}
