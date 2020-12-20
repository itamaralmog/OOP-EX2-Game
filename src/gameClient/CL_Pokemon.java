package gameClient;

import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}
	public void  set_edge(edge_data _edge) {
		this._edge = _edge;
	}
	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
}
