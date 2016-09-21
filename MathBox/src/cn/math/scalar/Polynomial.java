package cn.math.scalar;

import java.util.*;
import java.util.Map.Entry;

import cn.math.PrettyMath;

/**
 * Class for handling a series of polynomials.
 * 
 * @author miche_000
 * 
 */
public class Polynomial {
	/**
	 * Array of 5 regex expressions defining the acceptable forms of a monomial
	 */
	private static String[] polyDef = new String[] {
			// Case "ax^b": get coef, get expo
			"[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?x\\^[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?",
			// Case "x^b": coef = 1, get expo
			"[-+]?x\\^[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?",
			// Case "ax" : get coef, expo = 1
			"[-+]?[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?x",
			// Case "a" : get coef, expo = 0
			"[-+]?[(]?[-+]?[0-9]*\\.?[0-9]*[)]?",
			// Case "x" : coef = 1, expo = 1
			"[-+]?x", };

	protected String[] getPolyDef() {
		return polyDef;
	}

	/**
	 * Map<Double,Double> which stores the exponent and the coefficient of a
	 * chain of polynomials
	 */
	TreeMap<Double, Double> values = new TreeMap<>();
	/**
	 * multiple gets stored here when factoring
	 */
	public double multiple = 1;
	/**
	 * Remainder when this polynomial is the result of a division
	 */
	public Rational r = null;

	/**
	 * Wraps java.util.Map.put(Double key, Double value) In attempts to put new
	 * value in same key, adds the values instead of replace
	 * 
	 * @param expo
	 *            key
	 * @param coef
	 *            value
	 */
	public boolean put(double expo, double coef) {
		if (coef == 0) {
			return false;
		} else {
			if (values.get(expo) == null) {
				values.put((double) expo, (double) coef);
			} else {
				values.put((double) expo, (coef + values.get(expo)));
			}
			return true;
		}
	}

	/**
	 * Wraps java.util.Map.put(Double key, Double value) In attempts to put new
	 * value in same key, adds the values instead of replace
	 * 
	 * @param item
	 *            single entry consisting of a expo and a coef
	 */
	public boolean put(Map.Entry<Double, Double> item) {
		if (item.getValue() == 0) {
			return false;
		} else {
			if (values.get(item.getKey()) == null) {
				values.put(item.getKey(), item.getValue());
			} else {
				values.put(item.getKey(), (item.getValue() + values.get(item.getKey())));
			}
			return true;
		}
	}

	public boolean put(String monomial) {
		int i;
		for (i = getPolyDef().length - 1; i >= 0; i--) {
			if (monomial.matches(polyDef[i])) {
				break;
			}
		}
		double expo = 0;
		double coef = 0;
		try {
			switch (i) {
			case 0:
				expo = Double.valueOf(monomial.substring(monomial.indexOf("^") + 1));
				coef = Double.valueOf(monomial.substring(0, monomial.indexOf("x")));
				break;
			case 1:
				expo = Double.valueOf(monomial.substring(monomial.indexOf("^") + 1));
				coef = 1.0;
				break;
			case 2:
				expo = 1.0;
				coef = Double.valueOf(monomial.substring(0, monomial.indexOf("x")));
				break;
			case 3:
				expo = 0.0;
				coef = Double.valueOf(monomial);
				break;
			case 4:
				expo = 1.0;
				coef = 1.0;
				break;

			default:
				break;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return this.put(expo, coef);
	}

	/**
	 * Clone the parameter polynomial into a new polynomial
	 * 
	 * @param a
	 */
	public Polynomial(Polynomial a) {
		for (Map.Entry<Double, Double> item : a.values.entrySet()) {
			this.put(item);
		}
		this.multiple = a.multiple;
		if (a.r != null) {
			this.r = new Rational(a.r.getNumerator(), a.r.getDenominator());
		}
	}

	public Polynomial(TreeMap<Double, Double> values) {
		for (Map.Entry<Double, Double> item : values.entrySet()) {
			this.put(item);
		}
	}

	/**
	 * Declare a Polynomial with a formated String representing the expression
	 * 
	 * @param expression
	 */
	public Polynomial(String expression) {
		expression = expression.replace("-", "+-");
		expression = expression.replace("(", "");
		expression = expression.replace(")", "");
		String[] pieces = expression.split("\\+");
		for (String piece : pieces) {
			this.put(piece);
		}
	}

	/**
	 * Declare a Polynomial with an empty this.values
	 */
	public Polynomial() {
	}

	/**
	 * Assembles Map<Double,Double> values into a string from highest key to
	 * lowest key
	 * 
	 * @return expression string
	 */
	public String toString() {
		cleanUp();
		StringBuilder build = new StringBuilder();
		Iterator<Double> it = values.descendingKeySet().iterator();
		while (it.hasNext()) {
			double key = it.next();
			double val = values.get(key);

			if (val > 0 && key != values.lastKey()) {
				build.append("+");
			}
			if (!(val == 1 && key != 0)) {
				build.append(PrettyMath.toFrac(val));
			}
			if (key == 0) {
				continue;
			}
			build.append('x');
			if (key == 1) {
				continue;
			}
			build.append('^');
			build.append(String.format("(%1s)", PrettyMath.toFrac(key)));
		}
		if (multiple != 1) {
			build.insert(0, "(");
			build.insert(0, multiple);
			build.insert(build.length(), ")");
		}
		return build.toString();
	}

	private void cleanUp() {
		Iterator<Double> it = values.keySet().iterator();
		while (it.hasNext()) {
			if (values.get(it.next()) == 0) {
				it.remove();
			}
		}
	}

	public static double gcd(double a, double b) {
		while (b != 0) {
			double temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	public void factor() {
		Iterator<Map.Entry<Double, Double>> it = this.values.entrySet().iterator();
		multiple = it.next().getValue();
		while (it.hasNext()) {
			multiple = gcd(multiple, it.next().getValue());
		}
		multiple = Math.abs(multiple);
		this.divide(multiple);
	}

	/**
	 * print values as a 2 column chart, expo & coef
	 * 
	 * @deprecated: not dangerous but not pretty, just use print()
	 */
	public void printChart() {
		System.out.println("Expo\tCoef");
		for (Entry<Double, Double> value : values.entrySet()) {
			System.out.println(value.getKey() + "\t" + value.getValue());
		}
	}

	/**
	 * Prints the expression of this polynomial (and the remainder it there is
	 * one)
	 */
	public void print() {
		if (this.values.size() != 0) {
			System.out.println(this.toString());
		}
		if (r != null) {
			System.out.println("Remainder: " + r);
		}
	}

	/**
	 * Compares value of this polynomial to another polynomial
	 * 
	 * @param b
	 * @return
	 */
	public boolean equals(Polynomial b) {
		if (this.values.size() != b.values.size()) {
			return false;
		}

		Iterator<Double> it = this.values.keySet().iterator();

		while (it.hasNext()) {
			double key = it.next();
			if (!this.values.get(key).equals(b.values.get(key))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds this polynomial sequence with another
	 * 
	 * @param seq
	 * @return
	 */
	public Polynomial add(Polynomial seq) {
		Polynomial ans = new Polynomial();
		Iterator<Map.Entry<Double, Double>> it = this.values.entrySet().iterator();
		while (it.hasNext()) {
			ans.put(it.next());
		}
		it = seq.values.entrySet().iterator();
		while (it.hasNext()) {
			ans.put(it.next());
		}
		return ans;
	}

	/**
	 * Adds this polynomial sequence with another
	 * 
	 * @param scalar
	 *            number of seq
	 * @param seq
	 * @return a Polynomial
	 */
	public Polynomial add(double scalar, Polynomial seq) {
		Polynomial ans = new Polynomial();
		Iterator<Map.Entry<Double, Double>> it = this.values.entrySet().iterator();
		while (it.hasNext()) {
			ans.put(it.next());
		}
		it = seq.values.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Double, Double> temp = it.next();
			ans.put(temp.getKey(), temp.getValue() * scalar);
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
		Polynomial ans = new Polynomial();
		Iterator<Map.Entry<Double, Double>> it = this.values.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Double, Double> item = it.next();
			ans.values.put(item.getKey(), (item.getValue() * scalar));
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
		Polynomial ans = new Polynomial();

		Iterator<Map.Entry<Double, Double>> thisValues = this.values.entrySet().iterator();
		while (thisValues.hasNext()) {
			Map.Entry<Double, Double> a = thisValues.next();
			Iterator<Map.Entry<Double, Double>> seqValues = seq.values.entrySet().iterator();
			while (seqValues.hasNext()) {
				Map.Entry<Double, Double> b = seqValues.next();
				ans.put(a.getKey() + b.getKey(), a.getValue() * b.getValue());
			}
		}

		return ans;
	}

	/**
	 * Multiply a polynomial by a monomial (coef)x^(expo)
	 * 
	 * @param coef
	 * @param expo
	 * @return ans
	 */
	public Polynomial multiply(double coef, double expo) {
		Polynomial ans = new Polynomial();
		Iterator<Map.Entry<Double, Double>> thisValues = this.values.entrySet().iterator();

		while (thisValues.hasNext()) {
			Map.Entry<Double, Double> a = thisValues.next();
			ans.put(a.getKey() + expo, a.getValue() * coef);
		}

		return ans;
	}

	/**
	 * Divide this polynomial by a monomial (coef)x^(expo)
	 * 
	 * @param scalar
	 * @param expo
	 * @return ans
	 */
	public Polynomial divide(double scalar, double expo) {
		Polynomial ans = new Polynomial();
		Iterator<Map.Entry<Double, Double>> it = this.values.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Double, Double> item = it.next();
			ans.values.put(item.getKey() - expo, (item.getValue() / scalar));
		}
		return ans;
	}

	private void divide(double scalar) {
		Iterator<Double> it = this.values.keySet().iterator();
		while (it.hasNext()) {
			Double key = it.next();
			this.values.put(key, this.values.get(key) / scalar);
		}
	}

	/**
	 * Divides one polynomial by another polynomial
	 * 
	 * @param b
	 * @return
	 */
	public Polynomial divide(Polynomial b) {
		// Poly long division:
		// ````3x + 2
		// x-2)3x^2-4x+3
		// ````3x^2-6x
		// `````````2x+3
		// `````````2x-4
		// ````````````7
		// (x^2-4x+3)/(x-2) = 3x+2+7/(x-2)
		Polynomial ref = new Polynomial(this);
		Polynomial ans = new Polynomial();
		Set<Double> tmp = ref.values.descendingKeySet();
		Iterator<Double> it = tmp.iterator();
		while (it.hasNext()) {
			double key = it.next();
			double expo = key - b.values.lastKey();
			if (expo < 0) {
				ref.cleanUp();
				if (ref.values.size() > 0) {
					ans.r = new Rational(ref, b);
				}
				break;
			}
			double coef = ref.values.get(key) / b.values.lastEntry().getValue();
			ans.put(expo, coef);
			ref = ref.add(b.multiply(-coef, expo));
			tmp = ref.values.descendingKeySet();
			it = tmp.iterator();
		}

		return ans;

	}
}