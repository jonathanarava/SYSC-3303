import java.util.List;

public class Chef implements Runnable{
	private String name;	// name of chef
	private String ingredient1; // chef's ingredient
	private List<String> table;	// table of ingredients that all chefs have access to 
	
	
	// constuctor for Class Chef. Takes the name of the chef, his/ her ingedient and the table as a parameter
	public Chef(String name, String ingredient1, List<String> table) {
		this.name = name;
		this.ingredient1 = ingredient1;
		this.table = table;
	}
	
	/*
	 * buildSandwich() method is a synchronized Method that waits for the Agent to put an ingredient on the table,
	 * and then makes the rest of the sandwich by using the remaining ingredients from the appropriate chef 
	 */
	public synchronized void makeSandwich() {
		
		for(String ingredient: table) {
			while(ingredient.equals(ingredient1) || table.size()==0) {
				try {
					wait();	// wait till agent notifies that he/she has put ingredients on the table if this chef arrives here 
							// or wait till another chef notifies of a sandwich being built
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		} // end for
		
		table.clear(); 						// Clear table so that table is empty at the start of the next iteration
		Kitchen. ();    // indicates another sandwich has been made 
		System.out.printf("A Sandwich has been made by %s. In total, %d sandwiches has been made\n", name, Kitchen.get_threadCount());
		notifyAll();	// Then this chef will notify the Agent that the Table contents have been changed or ingredients were taken. 
		
	}
	
	public void run() {
		while(Kitchen.get_threadCount() < 20) {
			makeSandwich();
		}
	} // end run()
	
	
}
