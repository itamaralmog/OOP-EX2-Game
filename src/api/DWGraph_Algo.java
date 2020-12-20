package api;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private  DWGraph_DS g;

    public DWGraph_Algo(){ this.g=new DWGraph_DS(); }

    @Override
    public void init(directed_weighted_graph g) { this.g=(DWGraph_DS) g; }

    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(this.g);
    }

    //To chek if graph is connected
    @Override
    public boolean isConnected() {
        if(g.nodeSize()==0||g.nodeSize()==1) return true;
        Iterator<node_data> iter=g.getV().iterator();
        node_data src=iter.next();
        while (iter.hasNext()){
            node_data v=iter.next();
            v.setTag(0);
        }
        int count = 0;
        LinkedList<node_data> nodeQ = new LinkedList<>();
        nodeQ.add(src);
        src.setTag(0);
        while(!nodeQ.isEmpty()){
            node_data t = nodeQ.poll();
            //Need to chek with all the nodes
            for(edge_data e : g.getE(t.getKey())){
                if(g.getNode(e.getDest()).getTag()==0){
                    g.getNode(e.getDest()).setTag(1);
                    nodeQ.add(g.getNode(e.getDest()));
                    ++count;
                }
            }
        }
        if(count!=g.nodeSize()) return false;
        count=0;
        DWGraph_DS b = new DWGraph_DS();
        //It is directed graph so need to chek the function also on the reverse graph
        for(node_data n: g.getV()){
            node_data a=new NodeData(n.getKey(),n.getTag(),n.getWeight(),n.getInfo());
            b.addNode(a);
        }
        for (node_data n: b.getV()){
            for(edge_data e: g.getE(n.getKey())){
                b.connect(e.getDest(),e.getSrc(),e.getWeight());
            }
        }
        nodeQ.add(src);
        while(!nodeQ.isEmpty()){
            node_data t = nodeQ.poll();
            //Need to chek with all the nodes
            for(edge_data e : b.getE(t.getKey())){
                if(b.getNode(e.getDest()).getTag()==1){
                    b.getNode(e.getDest()).setTag(0);
                    nodeQ.add(b.getNode(e.getDest()));
                    ++count;
                }
            }
        }
        return count==g.nodeSize();
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        //That function is similar to the above function but with priority queue
        if(g.getNode(src)==null||g.getNode(dest)==null) return -1;
        if(g.getV().size()==0||g.getV().size()==1) return 0;
        if(src==dest) return 0;
        Iterator<node_data> upTag=g.getV().iterator();
        HashMap<Integer,NodeTag> tagMap=new HashMap<>();
        while (upTag.hasNext()){
            node_data n = upTag.next();
            NodeTag nodeTag=new NodeTag(n.getKey(),Double.MAX_VALUE,g.getE(n.getKey()));
            tagMap.put(nodeTag.getKey(),nodeTag);
        }
        NodeTag destN = tagMap.get(dest);
        PriorityQueue<NodeTag> pQ=new PriorityQueue<>(g.getV().size(),new NodeCompareForQueue());
        HashSet<NodeTag> vis= new HashSet<>();
        NodeTag srcN = tagMap.get(src);
        srcN.setTag(0);
        pQ.add(srcN);
        while (!pQ.isEmpty()){
            // To poll a node by priority of weight
            NodeTag cur= pQ.poll();
            if(!vis.contains(cur)){
                vis.add(cur);
                // After we found the destination node we do not need to continue
                if(cur==destN) return destN.getTag();
                //Need to chek with all the nodes
                for(edge_data e : cur.getE()){
                    if(!vis.contains(tagMap.get(e.getDest()))){
                       if(cur.getTag()+e.getWeight()<tagMap.get(e.getDest()).getTag()){
                           tagMap.get(e.getDest()).setTag(cur.getTag()+e.getWeight());
                           pQ.add(tagMap.get(e.getDest()));
                       }
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        // That function very similar the function above but with parent hashmap;
        if(g.getNode(src)==null||g.getNode(dest)==null) return null;
        if(g.getV().size()==0) return null ;
        if(src==dest) {
            ArrayList<node_data> l=new ArrayList<>();
            l.add(g.getNode(src));
        }
        Iterator<node_data> upTag=g.getV().iterator();
        HashMap<Integer,NodeTag> tagMap=new HashMap<>();
        while (upTag.hasNext()){
            node_data n = upTag.next();
            NodeTag nodeTag=new NodeTag(n.getKey(),Double.MAX_VALUE,g.getE(n.getKey()));
            tagMap.put(nodeTag.getKey(),nodeTag);
        }
        HashMap<NodeTag,NodeTag> parent=new HashMap<>();
        NodeTag destN = tagMap.get(dest);
        PriorityQueue<NodeTag> pQ=new PriorityQueue<>(g.getV().size(),new NodeCompareForQueue());
        HashSet<NodeTag> vis= new HashSet<>();
        NodeTag srcN = tagMap.get(src);
        srcN.setTag(0);
        pQ.add(srcN);
        while (!pQ.isEmpty()){
            NodeTag cur= pQ.poll();
            if(!vis.contains(cur)){
                vis.add(cur);
                if(cur==destN) break;
                for(edge_data e : cur.getE()){
                    if(!vis.contains(tagMap.get(e.getDest()))){
                        if(cur.getTag()+e.getWeight()<tagMap.get(e.getDest()).getTag()){
                            tagMap.get(e.getDest()).setTag(cur.getTag()+e.getWeight());
                            if(e.getDest()==dest){parent.remove(tagMap.get(e.getDest()));}
                            parent.put(tagMap.get(e.getDest()),cur);
                            pQ.add(tagMap.get(e.getDest()));
                        }
                    }
                }
            }
        }
        //build the list of nodes
        if(!parent.containsKey(destN)) return null;
        ArrayList<node_data> s= new ArrayList<>();
        s.add(g.getNode(destN.getKey()));
        node_data n =g.getNode(destN.getKey());
        NodeTag nT= tagMap.get(dest);
        while(n.getKey()!=src){
            nT = parent.get(nT);
            n =g.getNode(nT.getKey());
            s.add(n);
        }
        Collections.reverse(s);
        return s;
    }

    public boolean save(String file) {
        //Function that convert graph to json object
        JSONArray s1 = new JSONArray();
        JSONArray s2 = new JSONArray();
        try {
            for (node_data i : g.getV()) {
                for (edge_data j : g.getE(i.getKey())) {
                    JSONObject n = new JSONObject();
                    n.put("src", j.getSrc());
                    n.put("W", j.getWeight());
                    n.put("dest", j.getDest());
                    s1.put(n);
                }
                JSONObject n = new JSONObject();
                if(i.getLocation()!=null) {
                    n.put("pos", i.getLocation().toString());
                }
                else
                    n.put("pos","0.0,0.0,0.0");
                n.put("id",i.getKey());
                s2.put(n);
            }
            JSONObject graph = new JSONObject();
            graph.put("Edges",s1);
            graph.put("Nodes",s2);
            ObjectOutputStream objOutPut = new ObjectOutputStream(new FileOutputStream(file));
            objOutPut.writeObject(graph);
            objOutPut.close();
            return true;
        }
        catch (IOException | JSONException e ) {
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        //function that load json object that represent graph and convert that to graph
        DWGraph_DS graph_ds= new DWGraph_DS();
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            JSONObject graph=new JSONObject(objectInput.readObject().toString());
            JSONArray s1=graph.getJSONArray("Edges");
            JSONArray s2=graph.getJSONArray("Nodes");
            for (int i =0;i<s2.length();i++){
                JSONObject item = (JSONObject) s2.get(i);
                String pos=item.getString("pos");
                int id=item.getInt("id");
                String[] posArr= pos.split(",");
                double [] posDouble = new double[posArr.length];
                for(int j=0; j < posArr.length;j++){
                    try {
                        posDouble[j] = Double.parseDouble(posArr[j]);
                    }
                    catch(IllegalArgumentException e) {
                        System.err.println("ERR: got wrong format string");
                        throw(e);
                    }
                }
                NodeData.Geo loc=new NodeData.Geo(posDouble[0],posDouble[1],posDouble[2]);
                NodeData n = new NodeData(id,0,0,"");
                n.setLocation(loc);
                graph_ds.addNode(n);
            }
            for(int i = 0 ; i < s1.length() ;++i){
                JSONObject item = (JSONObject) s1.get(i);
                int src = item.getInt("src");
                double w = item.getDouble("w");
                int dest = item.getInt("dest");
                graph_ds.connect(src,dest,w);
            }
            g=graph_ds;
            objectInput.close();
            return true;
        }
        catch (IOException | ClassNotFoundException | JSONException e) {
            return false;
        }
    }
}
