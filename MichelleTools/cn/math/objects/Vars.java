package cn.math.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Map of variables with name and value
 * @author miche_000
 *
 */
public class Vars {
	public Map<String, Object> list = new HashMap<>();

	public void printList() {
		System.out.printf("%1$8s %2$8s \n", "Name", "Value");
		Iterator<Map.Entry<String, Object>> read = list.entrySet().iterator();
		while (read.hasNext()) {
			Map.Entry<String, Object> a = read.next();
			System.out.printf("%1$8s %2$8s \n", a.getKey(), a.getValue());
		}
	}

	public static void main(String[] args) {
		Vars a = new Vars();
		Matrix b = null;
		try {
			b = new Matrix(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		a.list.put("a", "Pancakes");
		a.list.put("I_3", b);
		a.list.put("boo", 3);
		a.printList();
	}

}
