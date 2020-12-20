package gameClient.util;
/**
 * This class represents a simple 1D range of shape [min,max]
 * @author boaz_benmoshe
 *
 */
public class Range {
	private double min, max;
	public Range(double min, double max) {
		setMin(min);
		setMax(max);
	}
	public Range(Range x) {
		this(x.min, x.max);
	}

	// Return true if d found in the range
	public boolean isIn(double d) {
		boolean inSide = false;
		if(d>=this.getMin() && d<=this.getMax()) {inSide=true;}
		return inSide;
	}
	public String toString() {
		String ans = "["+this.getMin()+","+this.getMax()+"]";
		if(this.isEmpty()) {ans = "Empty Range";}
		return ans;
	}


	public boolean isEmpty() {
		return this.getMin()>this.getMax();
	}
	public double getMax() {
		return max;
	}

	//The length of line on one range
	public double get_length() {
		return max - min;
	}
	
	private void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	private void setMin(double min) {
		this.min = min;
	}

	// return a number that represent into the portion
	public double getPortion(double d) {
		double d1 = d- min;
		double ans = d1/get_length();
		return ans;
	}
	// return a number that represent from portion
	public double fromPortion(double p) {
		return min +p* get_length();
	}
}
