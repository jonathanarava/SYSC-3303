Jonathan Arava (101007533)

Github files:
https://github.com/jonathanarava/SYSC-3303

SYSC3303:Assignment 1
How To Run:

1) Unzip Assignment folder and place into workspace.
2) Run the Server Class
3) Run the Intermediate Class
4) Run the Client Class 


Files:

Client.java:
	- This class uses the various method shows in the class diamgram, that can be run that executes 11 requests 
	(5 read, 5 write, one invalid)
	- Both the Intermediate_host and the Server must be running BEFORE for this to work
	
Server.java:
	- The main method runs infinitly and waits for packet to be received from the Intermediate_host class
	- This must be running before the Intermediate class sends its requests

IntermediateHost.java:
	- runs forever and keeps listening if packets are being sent from server or client ports.
	- This must be running before the client sends packets
	- Server must be running before the Intermediate_host tries to forward a request from client
	