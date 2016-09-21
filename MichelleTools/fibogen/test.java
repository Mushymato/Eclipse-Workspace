package fibogen;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class test {

	public static void main(String[] args) {
		fiboSeq run = new fiboSeq();
		// calculate 2^16th fibo num
		long start = System.nanoTime();
		
//		Map<Long, BigInteger> fibo = run.fiboMap(0, 100);
//		Map<Long, BigInteger> pisa = run.pisaPeriod(fibo, "7");
		
		System.out.println(run.fiboRec(10));

		long end = System.nanoTime();
		
		System.out.println(end - start);
		
//		Iterator<Entry<Long, BigInteger>> it = fibo.entrySet().iterator();
//		while(it.hasNext()){
//			System.out.println(it.next());
//		}
//		it = pisa.entrySet().iterator();
//		while(it.hasNext()){
//			System.out.println(it.next());
//		}
	}
}
