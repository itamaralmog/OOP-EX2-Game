package api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class NodeData implements node_data, Serializable{
    private int key;
    static int numberKey=0;
    private int tag;
    private String info;
    private geo_location loc;
    private double w;

    public NodeData() // Constructor without arguments
    {
        this(numberKey,0,0,"");
    }
    public NodeData(int key,int tag,double w,String info) // Constructor
    {
        this.key = key;
        this.tag=tag;
        this.info=info;
        this.w=w;
        ++numberKey;
    }
    @Override
    public int getKey() {
        return key;
    }

    @Override
    public geo_location getLocation() {
        return this.loc;
    }

    @Override
    public void setLocation(geo_location p) {
        this.loc=new Geo(p.x(), p.y(), p.z());
    }

    @Override
    public double getWeight() {
        return w;
    }

    @Override
    public void setWeight(double w) {
        this.w=w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "["  + key + ']';
    }

    @Override
    public boolean equals(Object o) {
        //function that equals objects.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData =(NodeData) o;
        return ((key==nodeData.getKey())&&
                (tag==nodeData.getTag())&&
                (info==nodeData.getInfo())&&
                (w==nodeData.getWeight()));
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    static  public class Geo implements geo_location,Serializable{
        private double x,y,z;
        public Geo(){//constructor without arguments
            x=0;
            y=0;
            z=0;
        }
        public Geo(double x,double y, double z){ //constructor with arguments
            this.x=x;
            this.y=y;
            this.z=z;
        }
        public Geo(geo_location p){
            this(p.x(), p.y(), p.z());
        }
        @Override
        public String toString() {
            return x +"," + y +"," + z;
        }

        @Override
        public double x() {
            return this.x;
        }

        @Override
        public double y() {
            return this.y;
        }

        @Override
        public double z() {
            return this.z;
        }

        @Override
        public double distance(geo_location g) {
            // Function that chek the distance between the locations
            double x = this.x() - g.x();
            double y = this.y() - g.y();
            double z = this.z() - g.z();
            double dis = (x*x)+(y*y)+(z*z);
            dis=Math.sqrt(dis);
            return dis;
        }

        @Override
        public boolean equals(Object o) {
            // Function that equals the locations
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Geo geo = (Geo) o;
            return ((x==geo.x)&&(y==geo.y)&&(z==geo.z));
        }
    }
}
