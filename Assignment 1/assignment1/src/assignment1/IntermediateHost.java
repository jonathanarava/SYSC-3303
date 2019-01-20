package assignment1;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class IntermediateHost {
	
	public byte[] clientPacket;
	public byte[] serverPacket;
	DatagramPacket sendPacket, receivePacket; 
	DatagramSocket sendReceieveSocketClient, sendReceieveSocketServer ;
	
	public IntermediateHost(){
		try {
			// Construct a datagram socket and bind it to any available 
			// port on the local host machine. This socket will be used to
			// send and receive UDP Datagram packets.
			sendReceieveSocketClient = new DatagramSocket(23);
			sendReceieveSocketServer = new DatagramSocket();
			
			} catch (SocketException se) {   // Can't create the socket.
				se.printStackTrace();
				System.exit(1);
			}
	}
	
	public void sendAndReceiveClient()
	   {
		
	    // Construct a DatagramPacket for receiving packets up 
	      // to 100 bytes long (the length of the byte array).

	      byte data[] = new byte[20];
	      receivePacket = new DatagramPacket(data, data.length);

	      
	      try {
	    	  // Block until a datagram is received via sendReceiveSocket.  
	    	  sendReceieveSocketClient.receive(receivePacket);
	    	  
	      	  System.out.println("Intermediate Host: sending packet to server\n" + "Containing (as Bytes): " + Arrays.toString(data));
	      	  
	          System.out.println("Client: Packet received:");
	          System.out.println("From host: " + receivePacket.getAddress());
	          System.out.println("Host port: " + receivePacket.getPort());
	          int len = receivePacket.getLength();
	          System.out.println("Length: " + len);
	          System.out.print("Containing (as String): ");
	          String received = new String(data,0,len);   
		      System.out.println(received + "\n\n");
	        
	      } catch(IOException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }


	      // Form a String from the byte array.
	      
	      try {
	         sendPacket = new DatagramPacket(data, data.length,
	                                         InetAddress.getLocalHost(), 69);
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }
	      
	      /////////////

	      try {
	    	  sendReceieveSocketServer.send(sendPacket);
	      } catch (IOException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }

	      //System.out.println(" \n Client: Packet sent.\n");

	  

	      // We're finished, so close the socket.
	      //sendReceieveSocketServer.close();
	   }
	
	public void sendReceieveServer(){
		
	  // Construct a DatagramPacket for receiving packets up 
    // to 100 bytes long (the length of the byte array).

    byte data[] = new byte[4];
    receivePacket = new DatagramPacket(data, data.length);

    try {
       // Block until a datagram is received via sendReceiveSocket.  
    	sendReceieveSocketServer.receive(receivePacket);
    	System.out.println("Intermediate Host: receiving packet from server\n" + "Containing (as Bytes): " + Arrays.toString(data));
  	  
    	System.out.println("Client: Packet received:");
    	System.out.println("From host: " + receivePacket.getAddress());
    	System.out.println("Host port: " + receivePacket.getPort());
    	int len = receivePacket.getLength();
    	System.out.println("Length: " + len);
    	System.out.print("Containing: ");
      
    	String received = new String(data,0,len);   
    	System.out.println(received);
    } catch(IOException e) {
       e.printStackTrace();
       System.exit(1);
    }
    
    
    try {
        sendPacket = new DatagramPacket(data, data.length,
                                        InetAddress.getLocalHost(), receivePacket.getPort());
     } catch (UnknownHostException e) {
        e.printStackTrace();
        System.exit(1);
     }

     System.out.println("Intermediate Host: Sending packet to client:");
     System.out.println("Intermediate Host: Sending packet to client:\n" +"To host: " + sendPacket.getAddress());
     System.out.println("Destination host port: " + sendPacket.getPort());
     int len = sendPacket.getLength();
     System.out.println("Length: " + len);
     System.out.print("Containing: ");
     System.out.println(new String(sendPacket.getData(),0,len)); // or could print "s"

     // Send the datagram packet to the server via the send/receive socket. 

     try {
   	  sendReceieveSocketServer.send(sendPacket);
     } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
     }

     System.out.println("Client: Packet sent.\n");

 
	}

  
	
	   

    
    
	public static void main(String args[]){
		IntermediateHost c = new IntermediateHost();
	    for(;;) { 
		c.sendAndReceiveClient();
	      c.sendReceieveServer();
	    }
	}

	
}

