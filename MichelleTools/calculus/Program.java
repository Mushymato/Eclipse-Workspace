package calculus;

public class Program {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			String[] polyDef = new String[] {
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
			long start = System.currentTimeMillis();
			for (int j = 0; j < 50000; j++) {
				
			}
			System.out.println("Time: " + (System.currentTimeMillis() - start) + " millisec");
			System.gc();
		}
		//clean up
		//System.gc();
	}
}
