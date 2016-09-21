package cn.math.scalar;

public class Rational {
	private Polynomial numerator;
	private Polynomial denominator;

	public Polynomial getNumerator() {
		return numerator;
	}

	public Polynomial getDenominator() {
		return denominator;
	}

	/**
	 * New Remainder object with diviend Polynomial and divisor Polynomial
	 * 
	 * @param diviend
	 * @param divisor
	 */
	public Rational(Polynomial numerator, Polynomial denominator) {
		this.numerator = new Polynomial(numerator);
		this.numerator.factor();
		this.denominator = new Polynomial(denominator);
		this.denominator.factor();
	}

	/**
	 * Void constructor
	 */
	public Rational() {
	}

	/**
	 * Returns the rational expression
	 */
	public String toString() {
		return String.format("(%s)/(%s)", numerator.toString(), denominator.toString());

	}

	public Object reduce() {
		Polynomial ans = this.numerator.divide(this.denominator);
		if (ans.r == null) {
			return ans;
		} else {
			double x = Math.min(this.numerator.values.firstKey(), this.denominator.values.firstKey());
			if (x == 0) {
				return null;
			}
			double cf = Polynomial.gcd(numerator.multiple, denominator.multiple);
			numerator = this.numerator.divide(cf, x);
			denominator = this.denominator.divide(cf, x);
			return this;
		}
	}

	/**
	 * Add a rational expression to this rational expression
	 * 
	 * @param b
	 * @return ans
	 */
	public Rational add(Rational b) {
		Rational ans = new Rational();
		if (this.denominator.equals(b.denominator)) {
			ans.numerator = this.getNumerator().add(b.numerator);
			ans.denominator = this.getDenominator();
		} else {
			ans.denominator = this.getDenominator().multiply(b.denominator);
			ans.numerator = this.getNumerator().multiply(b.getDenominator())
					.add(b.getNumerator().multiply(this.getDenominator()));
		}
		return ans;
	}
	
	/**
	 * Add a rational expression to this rational expression
	 * 
	 * @param scalar
	 * @param b
	 * @return ans
	 */
	public Rational add(double scalar, Rational b) {
		Rational ans = new Rational();
		if (this.denominator.equals(b.denominator)) {
			ans.numerator = this.getNumerator().add(scalar, b.numerator);
			ans.denominator = this.getDenominator();
		} else {
			ans.denominator = this.getDenominator().multiply(b.denominator);
			ans.numerator = this.getNumerator().multiply(b.getDenominator()).add(scalar,
					b.getNumerator().multiply(this.getDenominator()));
		}

		return ans;
	}

	/**
	 * Multiply rational expression by a polynomial
	 * 
	 * @param b
	 * @return ans
	 */
	public Rational multiply(Polynomial b) {
		Rational ans = new Rational();
		ans.numerator = this.getNumerator().multiply(b);
		ans.denominator = this.getDenominator();
		return ans;
	}

	/**
	 * Multiply rational expression by a denominator
	 * 
	 * @param b
	 * @return ans
	 */
	public Rational multiply(Rational b) {
		Rational ans = new Rational();
		ans.numerator = this.getNumerator().multiply(b.numerator);
		ans.denominator = this.getDenominator().multiply(b.denominator);
		return ans;
	}

	/**
	 * Divide rational expression by a polynomial Accomplished by multiplying by
	 * the reciprocal
	 * 
	 * @param b
	 * @return ans
	 */
	public Rational divide(Polynomial b) {
		Rational ans = new Rational();
		ans.numerator = this.getNumerator();
		ans.denominator = this.getDenominator().multiply(b);
		return ans;
	}

	/**
	 * Divide rational expression by a rational number Accomplished by
	 * multiplying by the reciprocal
	 * 
	 * @param b
	 * @return ans
	 */
	public Rational divide(Rational b) {
		Rational ans = new Rational();
		ans.numerator = this.getNumerator().multiply(b.denominator);
		ans.denominator = this.getDenominator().multiply(b.numerator);
		return ans;
	}

}
