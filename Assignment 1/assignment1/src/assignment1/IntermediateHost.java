package assignment1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class IntermediateHost {

	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, receiveSocket;
	int portNumber1 = 2345;
	int portNumber2 = 5043;
	
	public IntermediateHost() {
		try {
		      // Construct a datagram socket and bind it to any available 
		      // port on the local host machine. This socket will be used to
		      // send UDP Datagram packets.
		      sendSocket = new DatagramSocket(portNumber2);

		      // Construct a datagram socket and bind it to port 5000 
		      // on the local host machine. This socket will be used to
		      // receive UDP Datagram packets.
		      receiveSocket = new DatagramSocket(portNumber1);
		      
		      // to test socket timeout (2 seconds)
		      //receiveSocket.setSoTimeout(2345);
		   } catch (SocketException se) {
		      se.printStackTrace();
		      System.exit(1);
		   } 
	}
	
	public void forward() {
		
	}
	
	public static void main( String args[] )
	{
	   SnipServer c = new SnipServer();
	   for(;;) {
		   c.receiveAndEcho();
		   
	   }
	   
	}
	
	
	
	
	
}
