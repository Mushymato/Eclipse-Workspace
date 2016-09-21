package cn.math.scalar;

import java.util.*;

public class Subspace {
	private Set<Vector> spanSet = new HashSet<Vector>();

	public Set<Vector> getSpanSet() {
		return spanSet;
	}

	private int n;

	/**
	 * Initialize new zero subspace in R^n,
	 * 
	 * @param n
	 */
	public Subspace(int n) {
		spanSet.add(new Vector(n));
		this.n = n;
	}

	/**
	 * Initialize a new subspace from a collection of Vectors, vectors with a
	 * different length than the first are not included in the spanning set
	 * 
	 * @param spanSet
	 */
	public Subspace(Collection<Vector> spanSet) {
		Iterator<Vector> it = spanSet.iterator();

		Vector v = it.next();
		int n = v.rows;

		while (it.hasNext()) {
			v = it.next();
			if (n == v.rows) {
				spanSet.add(v);
			}
		}
	}

	private boolean linIndep(Set<Vector> ref, Vector v) {
		Matrix mat = new Matrix();
	}

}
