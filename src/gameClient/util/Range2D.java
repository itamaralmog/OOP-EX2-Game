package gameClient.util;
import api.geo_location;

/**
 * This class represents a 2D Range, composed from two 1D Ranges.
 */
public class Range2D {
	private Range yRange;
	private Range xRange;

	//constructor
	public Range2D(Range x, Range y) {
		xRange = new Range(x);
		yRange = new Range(y);
	}
	//constructor
	public Range2D(Range2D w) {
		xRange = new Range(w.xRange);
		yRange = new Range(w.yRange);
	}
	//return the point in portion to some surface
	public Point3D getPortion(geo_location p) {
		double x = xRange.getPortion(p.x());
		double y = yRange.getPortion(p.y());
		return new Point3D(x,y,0);
	}
	//return the point in portion from some surface
	public Point3D fromPortion(geo_location p) {
		double x = xRange.fromPortion(p.x());
		double y = yRange.fromPortion(p.y());
		return new Point3D(x,y,0);
	}	
}
