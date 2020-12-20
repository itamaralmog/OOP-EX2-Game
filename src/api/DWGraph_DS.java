package api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph, Serializable {
    private HashMap<Integer,node_data> DWnodes;
    private HashMap<Integer,HashMap<Integer,edge_data>> nei;
    private int edges;
    private int MC;

    // constructor without arguments
    public DWGraph_DS(){
        this.DWnodes=new HashMap<>();
        this.nei=new HashMap<>();
        this.edges=0;
        this.MC=0;
    }
    //Copy constructor
    public DWGraph_DS(DWGraph_DS g){
        if(g==null) {
            DWnodes=new HashMap<>();
            nei=new HashMap<>();
            edges=0;
            MC=0;
        }

        else {
            //copy the nodes
            for (node_data i:g.DWnodes.values()){
                NodeData n= new NodeData(i.getKey(),i.getTag(),i.getWeight(),i.getInfo());
                DWnodes.put(n.getKey(),n);
            }
            // copy the edges
            for (node_data i:g.DWnodes.values()){
                node_data n =new NodeData(i.getKey(),i.getTag(),i.getWeight(),i.getInfo());
                if(g.nei.containsKey(i.getKey())){
                    HashMap<Integer,edge_data> neiN=new HashMap<>();
                    for (edge_data j:g.nei.get(i.getKey()).values()){
                        edge_data e = new EdgeData(g.DWnodes.get(j.getSrc()),g.DWnodes.get(j.getDest()),j.getWeight(),j.getTag(), j.getInfo());
                        int key=j.getDest();
                        neiN.put(key,e);
                    }
                    nei.put(n.getKey(),neiN);
                }
            }
            edges = g.edges;
            MC = g.MC;
        }
    }

    @Override
    public node_data getNode(int key) {
        return this.DWnodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return nei.get(src).get(dest);
    }

    @Override
    public void addNode(node_data n) {
        //Function that add node
        if(this.DWnodes.containsKey(n.getKey())){
            node_data nD=new NodeData();//
            this.DWnodes.put(nD.getKey(),nD);

        }
        this.DWnodes.put(n.getKey(),n);
        ++MC;
    }

    //Function that connect between nodes with a edge
    @Override
    public void connect(int src, int dest, double w) {
        if((!DWnodes.containsKey(src))||(!DWnodes.containsKey(dest))) return;
        edge_data e= new EdgeData(DWnodes.get(src),DWnodes.get(dest),w);
        if(nei.get(src)==null){
            HashMap<Integer,edge_data> n = new HashMap<>();
            n.put(dest,e);
            nei.put(src,n);
            return;
        }
        if((nei.get(src).get(dest)!=null)){
            if(nei.get(src).get(dest).getWeight()!=w) {
                nei.get(src).remove(dest);
                nei.get(src).put(dest, e);
            }
        }
        else{
            nei.get(src).put(dest,e);
        }
        return;

    }
    //returns all the nodes
    @Override
    public Collection<node_data> getV() {
        return DWnodes.values();
    }
    //returns all the neighbors of specific node
    @Override
    public Collection<edge_data> getE(int node_id) {
        return nei.get(node_id).values();
    }

    //remove node from the graph
    @Override
    public node_data removeNode(int key) {
        if(DWnodes.get(key)==null) return null;
        for(int k: nei.keySet()){
            if(nei.get(k).get(key)!=null)
                nei.get(k).remove(key) ;
        }
        nei.remove(key);
        node_data nD=DWnodes.get(key);
        DWnodes.remove(key);
        return nD;
    }
    // remove edge
    @Override
    public edge_data removeEdge(int src, int dest) {
        if((!DWnodes.containsKey(src))||(!DWnodes.containsKey(dest)))
            return null;
        if(!nei.get(src).containsKey(dest)) return null;
        edge_data e= nei.get(src).get(dest);
        nei.get(src).remove(dest);
        return e;
    }

    @Override
    public int nodeSize() {
        return DWnodes.size();
    }

    @Override
    public int edgeSize() {
        return edges;
    }

    @Override
    public int getMC() {
        return MC;
    }

    // Function that equals objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return edges == that.edges &&
                MC == that.MC &&
                Objects.equals(DWnodes, that.DWnodes) &&
                Objects.equals(nei, that.nei);
    }

    @Override
    public String toString() {
        String s="";
        for(node_data i : DWnodes.values()){
            s+=i.toString()+"  ";
            if(nei.get(i.getKey())!=null) {
                for (edge_data j : nei.get(i.getKey()).values()) {
                    s += j.toString() + "";
                }
            }
            s+="\n";
        }
        return s;
    }
}
