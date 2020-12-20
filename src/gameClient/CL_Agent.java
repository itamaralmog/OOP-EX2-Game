package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.ArrayList;

public class CL_Agent {
		private int id;//
		private geo_location pos;//
		private double speed;//
		private edge_data currEdge;//
		private node_data currNode;//
		private directed_weighted_graph graph;//
		private double value;//
//		private game_service game_service;
//		private ArrayList<CL_Pokemon> pokemonArr;

    // constructor
	public CL_Agent(directed_weighted_graph g, int start_node) {
			graph = g;
			setValue(0);
			this.currNode = graph.getNode(start_node);
			pos = currNode.getLocation();
			id = -1;
			setSpeed(0);
	}
		//Update the agent from the game
		public void update(String json) {
			JSONObject line;
			try {
				// "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
				line = new JSONObject(json);
				JSONObject agent = line.getJSONObject("Agent");
				int id = agent.getInt("id");
				if(id==this.getID() || this.getID() == -1) {
					if(this.getID() == -1) {
						this.id = id;}
					double speed = agent.getDouble("speed");
					String p = agent.getString("pos");
					Point3D pp= new Point3D(p);
					int src = agent.getInt("src");
					int dest = agent.getInt("dest");
					double value = agent.getDouble("value");
					this.pos = pp;
					this.setCurrNode(src);
					this.setSpeed(speed);
					this.setNextNode(dest);
					this.setValue(value);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		//@Override
		public int getSrcNode() {return this.currNode.getKey();}
		public String toJSON() {
			int d = this.getNextNode();
			String ans = "{\"Agent\":{"
					+ "\"id\":"+this.id +","
					+ "\"value\":"+this.value +","
					+ "\"src\":"+this.currNode.getKey()+","
					+ "\"dest\":"+d+","
					+ "\"speed\":"+this.getSpeed()+","
					+ "\"pos\":\""+ pos.toString()+"\""
					+ "}"
					+ "}";
			return ans;
		}
		private void setValue(double v) { value = v;}


		public boolean setNextNode(int dest) {
			boolean ans = false;
			int src = this.currNode.getKey();
			this.currEdge = graph.getEdge(src, dest);
			if(currEdge !=null) {
				ans=true;
			}
			else {
				currEdge = null;}
			return ans;
		}
		public void setCurrNode(int src) {
			this.currNode = graph.getNode(src);
		}
		public String toString() {
			return toJSON();
		}
		public int getID() { return this.id; }
	
		public geo_location getLocation() { return pos; }
		
		public double getValue() { return this.value; }
		public int getNextNode() {
			int ans;
			if(this.currEdge ==null) {
				ans = -1;}
			else {
				ans = this.currEdge.getDest();
			}
			return ans;
		}
		public double getSpeed() {
			return this.speed;
		}

		public void setSpeed(double v) {
			this.speed = v;
		}

}
