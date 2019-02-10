import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Kitchen {
	
	// Variable declaration
	public static Chef chef1;
	public static Chef chef2;
	public static Chef chef3;
	public static Agent agent;
	
	// Thread declaration
	public static Thread c1;
	public static Thread c2;
	public static Thread c3;
	public static Thread a1;
	
	public static int threadCount; //number of sandwiches made (limit of 20 sandwiches- assignment 2)
	
	public static int get_threadCount() {
		return threadCount;
	}
	
	public static void increment_threadCount() {
		threadCount++;
	}

	public static void main(String argv[]) {
		List<String> table = Collections.synchronizedList(new ArrayList<String>());
		
		chef1 = new Chef("John", "Bread", table);
		chef2 = new Chef("Doe", "Jam", table);
		chef3 = new Chef("Mary", "Penut Butter", table);
		agent = new Agent("Jane", "Bread", "Jam", "Penut Butter", table);
		
		c1 = new Thread(chef1);
		c2 = new Thread(chef2);
		c3 = new Thread(chef3);
		a1 = new Thread(agent);
		
		System.out.println("Making Sandwiches");
		c1.start();
		c2.start();
		c3.start();
		a1.start();
	}
	
	
	
}
