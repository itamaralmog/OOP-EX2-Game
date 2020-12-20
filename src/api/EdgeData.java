package api;

import java.io.Serializable;
import java.util.Objects;

public class EdgeData implements edge_data, Serializable {
    private node_data src;
    private node_data dest;
    private double w;
    private int tag;
    private String info;
    //constructor
    public EdgeData(node_data src,node_data dest,double w){
        this(src,dest,w,0,"");
    }
    //constructor
    public EdgeData(node_data src,node_data dest,double w,int tag,String info){
        this.src=src;
        this.dest=dest;
        this.w=w;
        this.tag=tag;
        this.info=info;
    }
    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    @Override
    public int getDest() {
        return this.dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() { return this.tag; }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    //Function that equals objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeData edgeData = (EdgeData) o;
        return ((src.getKey()==edgeData.getSrc())&&
                (dest.getKey()==edgeData.getDest())&&
                (w==edgeData.getWeight())&&
                (tag==edgeData.getTag())&&
                (info==edgeData.getInfo()));
    }

    @Override
    public String toString() {
        return "[Edge("+"dest=" + dest+",w="+w+")]";
    }
}
