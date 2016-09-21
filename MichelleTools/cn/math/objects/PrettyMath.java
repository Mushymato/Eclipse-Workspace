package cn.math.objects;

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
			return String.valueOf(decimal);
		}
		for (int i = 2; i < 1000; i++) {
			double a = approx(decimal * i);
			int b = (int) a;
			if (a - b == 0) {
				frac = String.valueOf((int) (decimal * i)) + "/" + String.valueOf(i);
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

	public double trying() {
		this.b = b + 3;
		return b;
	}

	public static void main(String[] args) {
		PrettyMath derp = new PrettyMath();
		System.out.println(derp.trying());
		System.out.println(derp.b);
		
	}
}
