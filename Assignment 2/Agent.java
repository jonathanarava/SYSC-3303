import java.util.List;
import java.util.Random;

public class Agent implements Runnable{
	
	private String name;	// agent is named
	
	// Agent's ingredient (unlimited quantity)
	public String ingredient1;
	public String ingredient2;
	public String ingredient3;
	
	// A List named table of type String that has the ingredent put by the agent
	public List<String> table;
	
	
	// Constructor for class Agent. Takes in agent's name 
	public Agent(String name, String ingredient1, String ingredient2, String ingredient3, List<String> table) {

		this.name = name;
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.ingredient3 = ingredient3;
		this.table = table;
	}
	
	// Method that retuns a random integer between 2 numbers provided as parameters
	private int RandomNumberGenerator(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("Invalid input");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	// fillTable() method puts the Agent's unlimited ingredient on the table
	public synchronized void fillTable() {
		//Table must be empty since the Agent is the first to put ingredient
		while(table.size() != 0) {	// if Agent put the ingredient on table
			try {
				System.out.println("The table is full of Ingredients");
				wait();  // waits for the chefs to use the ingredient provided by the agent 
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		int ingredientToPut = RandomNumberGenerator(1,3); // Agent randomly puts an ingredient on the table
		
		if(ingredientToPut == 1) {
			System.out.printf("%s decided to put %s on the table\n", name, ingredient1);
			table.add(ingredient1);
		}
		else if(ingredientToPut == 2) {
			System.out.printf("%s decided to put %s on the table\n", name, ingredient2);
			table.add(ingredient2);	
		}
		else if(ingredientToPut == 3){
			System.out.printf("%s decided to put %s on the table\n", name, ingredient3);
			table.add(ingredient3);
		}
		System.out.printf("%s picked %s and %s to put on the table\n", name, table.get(0), table.get(1));
		notifyAll();
	}
	
	public void run() {
		for(;;) {
			fillTable();
		} // end for
	} // end run()
	
	
}
