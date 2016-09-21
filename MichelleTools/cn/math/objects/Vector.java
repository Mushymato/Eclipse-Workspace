package cn.math.objects;

public class Vector {
	public double mag;
	public double rad;
	public double[] xy = new double[2];

	// Constructor-Start

	/**
	 * Initializes a new Vector object with magnitude and direction
	 * 
	 * @param magnitude
	 * @param radian
	 */
	public Vector(double magnitude, double radian) {
		this.mag = magnitude;
		this.rad = radian;
		xy[0] = Math.cos(rad) * magnitude;
		xy[1] = Math.sin(rad) * magnitude;
	}

	/**
	 * Initializes a new vector object based a point in the cartesian plane
	 * 
	 * @param x
	 * @param y
	 */
	public Vector(double[] xy) {
		this.xy = xy;
		this.rad = Math.atan(xy[1] / xy[0]);
		this.mag = Math.sqrt((xy[0] * xy[0]) + (xy[1] * xy[1]));
	}

	// Constructor-End
	/**
	 * Adds this vector with another, multiplied by a scalar Returns a new
	 * vector object
	 * 
	 * @param scalar
	 * @param b
	 * @return answer
	 */
	public Vector add(double scalar, Vector b) {
		double[] xy = { this.xy[0] + scalar * b.xy[0], this.xy[1] + scalar * b.xy[1] };
		return new Vector(xy);
	}

	/**
	 * multiply this Vector with a scalar, returns a new Vector object.
	 * 
	 * @param scalar
	 * @return answer
	 */
	public Vector multiply(double scalar) {
		Vector ans = this;
		ans.mag *= 2;
		ans.xy[0] *= 2;
		ans.xy[1] *= 2;
		return ans;
	}
	/**
	 * Display a vector in the format of:
	 * Magnitude: mag
	 * Direction: rad*(180/pi)
	 * (xy[0] ,xy[1])
	 */
	public void print(){
		System.out.println("Magnitude: "+mag);
		System.out.println("Direction: "+rad*(180/Math.PI));
		System.out.println("("+xy[0]+" ,"+xy[1]+")");
	}

	public static void main(String[] args) {
		double[] sides = {3,3};
		Vector a = new Vector(sides);
		double[] woos = {3,3};
		Vector b = new Vector(woos);
		Vector c = a.add(1, b);
		c.print();
	}
}
