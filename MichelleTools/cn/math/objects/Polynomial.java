package cn.math.objects;

/**
 * Class for handling a series of polynomials.
 * 
 * @author miche_000
 * 
 */
public class Polynomial {

	// Sec1-RegexListForPolynomials-Start
	/**
	 * Defines the acceptable string forms of a single polynomial acceptable
	 * forms are ax^b,x^b,ax,x,a
	 */
	private static 		String[] polyDef = new String[] {
			// Case "ax^b": get coef, get expo
			"[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?x\\^[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?",
			// Case "x^b": get coef, get expo
			"[-+]?x\\^[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?",
			// Case "ax" : get coef, expo = 1
			"[-+]?[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?x",
			// Case "a" : get coef, expo = 0
			"[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?",
			// Case "x" : coef = 1, expo = 1
			"[-+]?x", };


	public static String[] getPolyDef() {
		return polyDef;
	}
	// Sec1-RegexListForPolynomials-End

	// Sec2-PolyArray-Start
	/**
	 * Replaces custom vars in expr with "x"
	 * 
	 * @param expr
	 * @param vars
	 * @return
	 */
	public static String varsToX(String expr, String vars) {
		return expr.replaceAll("x", vars);
	}

	/**
	 * Checks if poly can be parsed correctly
	 * 
	 * @param poly
	 * @return
	 */
	protected boolean validForm(String poly) {
		boolean valid = false;
		for (int j = 0; j < polyDef.length; j++) {
			valid = poly.matches(polyDef[j]);
			if (valid) {
				break;
			}
		}
		return valid;
	}

	// Sec2-PolyArray-End

	// Sec3-ValuesList-Start
	/**
	 * List<Double> that holds processed data pertaining to polynomials the
	 * index is equal to the exponent the value is the coefficient
	 */
	private double[] values;

	public double[] Values() {
		return values;
	}

	/**
	 * Returns a coefficient from a properly formated polynomial string
	 * 
	 * @param poly
	 * @return coefficient
	 * @throws Exception
	 * @throws Custom
	 *             bad string form
	 */
	protected double getCoef(String poly) throws Exception {
		double coef = 0;
		if (!validForm(poly)) {
			throw new Exception(poly + " is not in polynomial(ax^b) form.");
		}
		if (poly.indexOf('x') == -1) {
			coef = Double.valueOf(poly);
		} else if (poly.indexOf('x') == 0) {
			coef = 1;
		} else {
			coef = Double.valueOf(poly.substring(0, poly.indexOf("x")));
		}
		return coef;
	}

	/**
	 * Returns a exponent from a properly formated polynomial string
	 * 
	 * @param poly
	 * @return
	 */
	protected int getExpo(String poly) throws Exception {
		if (!validForm(poly)) {
			throw new Exception(poly + " is not in polynomial(ax^b) form.");
		}
		int expo = 0;
		if (poly.contains("^")) {
			expo = Integer.valueOf(poly.substring(poly.indexOf('^') + 1));
		} else if (poly.contains("x")) {
			expo = 1;
		} else {
			expo = 0;
		}
		return expo;
	}

	/**
	 * Find the highest degree of the function and set it as the length of
	 * values
	 */
	private void setValueLength(String[] poly) {
		int len = 0;
		for (int i = 0; i < poly.length; i++) {
			if (poly[i].contains("^")) {
				int tmp = 0;
				try {
					tmp = getExpo(poly[i]);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (tmp > len) {
					len = tmp;
				}
			}
		}
		// catch for special situation
		if (len == 0) {
			for (int i = 0; i < poly.length; i++) {
				if (poly[i].indexOf("x") != -1) {
					len = 1;
				}
			}
		}
		values = new double[len + 1];
	}

	/**
	 * Parse expression into values
	 * 
	 * @param expression
	 */
	public Polynomial(String expr) {
		expr = expr.replace("-", "+-");
		String[] poly = expr.split("\\+");
		setValueLength(poly);
		for (int i = 0; i < poly.length; i++) {
			try {
				values[getExpo(poly[i])] = values[getExpo(poly[i])] + getCoef(poly[i]);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Constructor for just declaring double[] values
	 * 
	 * @param values
	 */
	public Polynomial(double[] values) {
		this.values = values;
	}

	/**
	 * Constructor for just declaring the length of double[] values
	 * 
	 * @param len
	 */
	protected Polynomial(int len) {
		this.values = new double[len];
	}
	// Sec3-ValuesList-End

	// Sec4-PublicPrintMethod-Start
	/**
	 * print values as a 2 column chart, expo & coef
	 */
	public void printValues() {
		System.out.println("Expo\tCoef");
		for (int i = 0; i < values.length; i++) {
			System.out.println(i + "\t" + values[i]);
		}
	}

	public String toExpression() {
		StringBuilder build = new StringBuilder();
		for (int i = values.length - 1; i >= 0; i--) {
			if (values[i] == 0) {
				continue;
			}
			if (values[i] > 0 && i != values.length - 1) {
				build.append("+");
			}
			build.append(values[i]);
			if (i > 0) {
				build.append("x");
				if (i > 1) {
					build.append("^");
					build.append(i);
				}
			}
		}
		return build.toString();
	}
	// Sec4-PublicPrintMethod-End

	// Sec5-PolynomialOperations-Start
	/**
	 * Adds this polynomial sequence with another
	 * 
	 * @param seq
	 * @return
	 */
	public Polynomial add(Polynomial seq) {
		Polynomial ans = new Polynomial(Math.max(this.values.length, seq.values.length));
		for (int i = 0; i < ans.values.length; i++) {
			ans.values[i] = this.values[i] + seq.values[i];
		}
		return ans;
	}

	/**
	 * Adds this polynomial sequence with another
	 * 
	 * @param scalar
	 *            a multiple of the seq
	 * @param seq
	 * @return a Polynomial
	 */
	public Polynomial add(double scalar, Polynomial seq) {
		Polynomial ans = new Polynomial(Math.max(this.values.length, seq.values.length));
		for (int i = 0; i < ans.values.length; i++) {
			ans.values[i] = this.values[i] + scalar * seq.values[i];
		}
		return ans;
	}

	/**
	 * Multiply this polynomial sequence with a scalar
	 * 
	 * @param scalar
	 * @return
	 */
	public Polynomial multiply(double scalar) {
		Polynomial ans = this;
		for (int i = 0; i < this.values.length; i++) {
			ans.values[i] *= scalar;
		}
		return ans;
	}

	/**
	 * Multiply this polynomial sequence with another
	 * 
	 * @param seq
	 * @return answer Polynomial object
	 */
	public Polynomial multiply(Polynomial seq) {
		Polynomial ans = new Polynomial(this.values.length + seq.values.length - 1);
		ans.values = new double[this.values.length + seq.values.length - 1];
		for (int i = 0; i < this.values.length; i++) {
			for (int j = 0; j < seq.values.length; j++) {
				ans.values[i + j] += this.values[i] * seq.values[j];
			}
		}
		return ans;
	}
	// Sec5-PolynomialOperations-End

	public static void main(String[] args) {
		Polynomial run = new Polynomial("3x^8-2x+x-2");
		run.printValues();
	}
}