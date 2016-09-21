package calculus;

public class ExpressionProc {

	/**
	 * Organizes polynomials from a (string) expression into a string array
	 * 
	 * @param expr
	 *            string expression in form of "ax^b+cx^d..."
	 * @return array of polynomials
	 */
	private String[] getPoly(String expr) {
		expr = expr.replace("-", "+-");
		String poly[] = expr.split("\\+");
		return poly;
	}

	/**
	 * Checks if the String[] from getPoly is correct
	 * 
	 * @param poly
	 * @return
	 */
	public boolean validForm(String[] poly) {
		boolean valid;
		int i = 0;
		do {
			// +-[number]x^[number]
			if (poly[i].matches("-?[1-9][0-9]*x\\^[1-9][0-9]*")) {
				valid = true;
				// +-x^[number]
			} else if (poly[i].matches("-?x\\^[1-9][0-9]*")) {
				valid = true;
				// +-[number]x
			} else if (poly[i].matches("-?[1-9][0-9]*x")) {
				valid = true;
				// +-x
			} else if (poly[i].matches("-?x")) {
				valid = true;
				// +-[number]
			} else if (poly[i].matches("-?[1-9][0-9]*")) {
				valid = true;
			} else {
				valid = false;
			}
			i++;
		} while (valid && i < poly.length);
		return valid;
	}

	/**
	 * Returns the exponent
	 * 
	 * @param poly
	 * @return expo
	 */
	private int getExpo(String poly) {
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
	 * Returns the coefficient
	 * 
	 * @param poly
	 * @return coef
	 */
	private double getCoef(String poly) {
		double coef = 0;
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
	 * 
	 * Finds the order of the expression or the maximum exponent
	 * 
	 * @param poly
	 * @return max highest exponent
	 */
	private int getMaxExpo(String[] poly) {
		int max = 0;
		for (int i = 0; i < poly.length; i++) {
			if (poly[i].contains("^")) {
				int tmp = getExpo(poly[i]);
				if (tmp > max) {
					max = tmp;
				}
			}
		}
		// catch for special situation
		if (max == 0) {
			for (int i = 0; i < poly.length; i++) {
				if (poly[i].indexOf("x") != -1) {
					max = 1;
				}
			}
		}

		return max;
	}

	/**
	 * Stores exponents and coefficients into a single array
	 * 
	 * @param expr
	 *            string expression
	 * @return values of expression
	 */
	public double[] getValues(String expr) {
		String[] poly = getPoly(expr);
		int max = getMaxExpo(poly);
		double[] values = new double[0];
		values = new double[max + 1];

		for (int i = 0; i < poly.length; i++) {
			values[getExpo(poly[i])] = values[getExpo(poly[i])] + getCoef(poly[i]);
		}

		return values;
	}

	/**
	 * Returns an expression from computed values
	 * 
	 * @param value
	 * @return expression
	 */

	public String getExpression(double[] value) {
		StringBuilder builder = new StringBuilder("");
		for (int i = value.length - 1; i >= 0; i--) {
			// skip 0 coef terms
			if (value[i] == 0) {
				continue;
			}
			// concat the next coef
			if (value[i] != 1) {
				builder = builder.append(value[i]);
			}
			// skips
			if (i == 0) {
				continue;
			}
			builder = builder.append("x");
			if (i == 1) {
				continue;
			}
			builder = builder.append("^").append(i);
			if (i == 1) {
				continue;
			}
			builder = builder.append("+");
		}
		String expr = builder.toString();
		expr.replace("+-", "-");
		return expr;
	}

}
