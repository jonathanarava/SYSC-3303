Jonathan Arava (101007533)

Github files:
https://github.com/jonathanarava/SYSC-3303

SYSC3303:Assignment 2


Setup/ Run:
1) Unzip "Assignment 2" folder and place into workspace.
2) Run the "Kitchen Class"
 


Files:

Agent.java:
	- randomly selects two ingredient to place on a table(list)
	- agent has unlimited quantity of ingredients
	- notifyAll() method used
	
Chef.java:
	- the appropriate chef who has the remaining ingredients to complete the dish will work (make sandwiches)
          by adding the remaining ingredient to the table
	- then incrementing the number sandwiches made
	- finally clearing the table for the next iteration
	- limits the number of iterations to 20 (as per assignment specification)
	- notifyAll() method used

Kitchen.java:
	- initiates and creates 3 chef and 1 agent threads

UML Diagram:
	- UML class diagram for Assignment 2 (hand written)
	