package cn.math.scalar;

public class Vector extends Matrix {
	/**
	 * Initialize a new vector of n length
	 * @param n
	 */
	public Vector(int n) {
		rows = n;
		cols = 1;
		matrix = new double[rows][cols];
	}
	/**
	 * Initialize a new vector with a double array
	 * @param values
	 */
	public Vector(double[] values) {
		rows = values.length;
		cols = 1;
		matrix = new double[rows][cols];
		for (int i = 0; i < values.length; i++) {
			matrix[i][0] = values[i];
		}
	}

	/**
	 * Returns the dot product of two vectors
	 * 
	 * @param v
	 * @return
	 */
	public double multiply(Vector v) {
		if (this.rows == v.rows) {
			double ans = 0;
			for (int i = 0; i < rows; i++) {
				ans = ans + matrix[i][0] * v.matrix[i][0];
			}
			return ans;
		} else {
			System.err.println("Dot product undefined for vectors of different dimentions.");
			return 0;
		}
	}

	/**
	 * Returns the scalar length of the vector from the origin
	 * 
	 * @return ans
	 */
	public double length() {
		double ans = 0;
		for (int i = 0; i < rows; i++) {
			ans = ans + Math.pow(matrix[i][0], 2);
		}

		return Math.sqrt(ans);
	}

	/**
	 * Returns the scalar length from this vector to the parameter vector
	 * 
	 * @param v
	 * @return ans
	 */
	public double length(Vector v) {
		double ans = 0;
		for (int i = 0; i < rows; i++) {
			ans = ans + (Math.pow(matrix[i][0] - v.matrix[i][0], 2));
		}
		return Math.sqrt(ans);
	}

	public static void main(String[] args) {
		double[] values = { 0, 5, 2 };
		Vector v = new Vector(values);
		Vector w = new Vector(values);
		System.out.println(v.equals(w));
	}

}
