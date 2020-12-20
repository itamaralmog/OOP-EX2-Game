package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 *
 */
public class Arena {
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private directed_weighted_graph graph;
	private List<CL_Agent> agents;
	private List<CL_Pokemon> pokemons;
	private List<String> info;
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	/**
	 * constructor
	 */
	public Arena() { info = new ArrayList<>(); }
	public void setPokemons(List<CL_Pokemon> f) {this.pokemons = f;}
	public void setAgents(List<CL_Agent> f) {this.agents = f;}
	public void setGraph(directed_weighted_graph g) {this.graph =g;}//init();}
	public void setGraph(String g){this.graph = jsonToGraph(g);}
	public List<CL_Agent> getAgents() {return agents;}
	public List<CL_Pokemon> getPokemons() {return pokemons;}
	public directed_weighted_graph getGraph() {return graph;}
	public List<String> getInfo() {return info;}

	//Get agent from the json
	public static List<CL_Agent> getAgents(String aa, directed_weighted_graph gg) {
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
		if(aa!=null)
		try {
			JSONObject ttt = new JSONObject(aa);
			JSONArray ags = ttt.getJSONArray("Agents");
			for(int i=0;i<ags.length();i++) {
				CL_Agent c = new CL_Agent(gg,0);
				c.update(ags.get(i).toString());
				ans.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ans;
	}

	//Get the pokemons from the json
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try {
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons");
			for(int i=0;i<ags.length();i++) {
				JSONObject pp = ags.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				//double s = 0;//pk.getDouble("speed");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
				ans.add(f);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans;
	}

	//To chek the edge the pokemon is on and update that
	public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g) {
		//	oop_edge_data ans = null;
		Iterator<node_data> itr = g.getV().iterator();
		while(itr.hasNext()) {
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while(iter.hasNext()) {
				edge_data e = iter.next();
				//Function that chek if the pokemon is on the edge
				boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g);
				if(f) {fr.set_edge(e);}
			}
		}
	}
	//The third function
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-EPS2) {ans = true;}
		return ans;
	}
	//The second function
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}
	//The first function
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;}
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);
	}
	// return the range the game take place
	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}
	// return the range the game take place and with more data that help to know the portion.
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

	//To convert json to graph
	public directed_weighted_graph jsonToGraph(String j){
		dw_graph_algorithms ans=new DWGraph_Algo();
		try { //output to a file for the algo
			ObjectOutputStream objOutPut = new ObjectOutputStream(new FileOutputStream("Game.json"));
			objOutPut.writeObject(j);
			objOutPut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ans.load("Game.json");
		return ans.getGraph();
	}

	// To read something from the graph json
	public int gameAgent(String game){
		JSONObject gameJ;
		JSONObject gameO;
		try {
			gameJ = new JSONObject(game);
			gameO= gameJ.getJSONObject("GameServer");
			int numAgent = gameO.getInt("agents");
			return numAgent;
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}
	// To read something from the graph json
	public static int gameLevel(String game){
		JSONObject gameJ;
		JSONObject gameO;
		try {
			gameJ = new JSONObject(game);
			gameO= gameJ.getJSONObject("GameServer");
			int level = gameO.getInt("game_level");
			return level;
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}
	// To read something from the graph json
	public static int grade(String game){
		JSONObject gameJ;
		JSONObject gameO;
		try {
			gameJ = new JSONObject(game);
			gameO= gameJ.getJSONObject("GameServer");
			int grade = gameO.getInt("grade");
			return grade;
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}
	// To read something from the graph json
	public static int moves(String game){
		JSONObject gameJ;
		JSONObject gameO;
		try {
			gameJ = new JSONObject(game);
			gameO= gameJ.getJSONObject("GameServer");
			int moves = gameO.getInt("moves");
			return moves;
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}
	// To read something from the graph json
	public static boolean isLoggedIn(String game){
		JSONObject gameJ;
		JSONObject gameO;
		try {
			gameJ = new JSONObject(game);
			gameO= gameJ.getJSONObject("GameServer");
			boolean isLoggedIn = gameO.getBoolean("is_logged_in");
			return isLoggedIn;
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
}
