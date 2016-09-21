package cn.math;

public class PrettyMath {
	/**
	 * Simple decimal to fraction method Treat as rough estimation
	 * 
	 * @param decimal
	 * @return
	 */
	public static String toFrac(double decimal) {
		String frac = null;
		if (decimal - (int) decimal == 0) {
			return String.format("%1$1.0f", decimal);
		}
		for (int i = 2; i < 500; i++) {
			double a = approx(decimal * i);
			int b = (int) a;
			if (a - b == 0) {
				frac = String.format("%1$1.0f/%2$s", decimal * i, String.valueOf(i));
				break;
			}
		}
		return frac;
	}

	private static double approx(double d) {
		double dif = Math.abs(d - (int) d);
		if (dif <= 0.01) {
			return (int) d;
		} else if (dif >= 0.99) {
			return (int) d + 1;
		} else {
			return d;
		}
	}

	int b = 8;

	public static void main(String[] args) {
		System.out.println(PrettyMath.toFrac(3-3));

	}
}
