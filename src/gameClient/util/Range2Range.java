package gameClient.util;

import api.geo_location;

/**
 * This class represents a simple world 2 frame conversion (both ways).
 * @author boaz.benmoshe
 *
 */

public class Range2Range {
	private Range2D world, frame;
	//constructor
	public Range2Range(Range2D w, Range2D f) {
		world = new Range2D(w);
		frame = new Range2D(f);
	}

	//return the point in portion to some surface
	public geo_location world2frame(geo_location p) {
		Point3D d = world.getPortion(p);
		Point3D ans = frame.fromPortion(d);
		return ans;
	}

	public Range2D getFrame() {
		return frame;
	}
}
