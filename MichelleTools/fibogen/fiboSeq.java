package fibogen;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class fiboSeq {

	public BigInteger n1;
	public BigInteger n2;

	static double phi = 1.6180339887498948482045868343656;

	public fiboSeq() {
		this.n1 = BigInteger.ZERO;
		this.n2 = BigInteger.ONE;
	}

	public fiboSeq(String n1, String n2) {
		this.n1 = new BigInteger(n1);
		this.n2 = new BigInteger(n2);
	}

	public BigInteger fiboNum(long idx) {
		BigInteger prev2 = n1.add(BigInteger.ZERO);
		BigInteger prev1 = n2.add(BigInteger.ZERO);
		BigInteger next1 = BigInteger.ZERO;

		for (int i = 1; i < idx; i++) {
			next1 = prev1.add(prev2);
			prev2 = prev1.add(BigInteger.ZERO);
			prev1 = next1.add(BigInteger.ZERO);
		}

		return next1;
	}

	public Map<Long, BigInteger> fiboMap(long start, long end) {
		BigInteger prev2 = n1;
		BigInteger prev1 = n2;
		BigInteger next1 = BigInteger.ZERO;

		for (long i = 0; i < start; i++) {
			next1 = prev1.add(prev2);
			prev2 = prev1.add(BigInteger.ZERO);
			prev1 = next1.add(BigInteger.ZERO);
		}

		HashMap<Long, BigInteger> fiboMap = new HashMap<>();
		fiboMap.put(start, prev2);
		fiboMap.put(start + 1, prev1);

		for (long i = start; i <= end - 2; i++) {
			next1 = prev1.add(prev2);
			prev2 = prev1.add(BigInteger.ZERO);
			prev1 = next1.add(BigInteger.ZERO);

			fiboMap.put(i + 2, next1);
		}

		return fiboMap;
	}

	public Map<Long, BigInteger> pisaPeriod(Map<Long, BigInteger> fiboMap, String period) {
		BigInteger prd = new BigInteger(period);
		HashMap<Long, BigInteger> pisaMap = new HashMap<>();
		for (Map.Entry<Long, BigInteger> fiboNum : fiboMap.entrySet()) {
			pisaMap.put(fiboNum.getKey(), fiboNum.getValue().mod(prd));
		}
		return pisaMap;
	}

}
