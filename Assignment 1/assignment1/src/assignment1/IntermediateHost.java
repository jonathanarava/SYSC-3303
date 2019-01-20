package assignment1;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class IntermediateHost {
	
	public byte[] clientPacket;
	public byte[] serverPacket;
	DatagramPacket sendPacket, receivePacket, sendPacket1, receivePacket1; 
	DatagramSocket sendReceieveSocketClient, sendReceieveSocketServer ;
	int portNumber1 =23, portNumber2 =69;
	
	public IntermediateHost(){
		try {
			// Construct a datagram socket and bind it to any available 
			// port on the local host machine. This socket will be used to
			// send and receive UDP Datagram packets.
			sendReceieveSocketClient = new DatagramSocket(portNumber1);
			sendReceieveSocketServer = new DatagramSocket();
			
			} catch (SocketException se) {   // Can't create the socket.
				se.printStackTrace();
				System.exit(1);
			}
	}
	
	public void sendAndReceiveClient(){
		
	      // Construct a DatagramPacket for receiving packets up 
	      // to 100 bytes long (the length of the byte array).

	      byte data[] = new byte[17];
	      receivePacket = new DatagramPacket(data, data.length);

	      
	      try {
	    	  // Block until a datagram is received via sendReceiveSocket.  
	    	  sendReceieveSocketClient.receive(receivePacket);
	    	  
	      	  System.out.println("Intermediate Host: receiving packet from client\n" + "Containing (as Bytes): " + Arrays.toString(data));
	      	  
	          System.out.println("Intermediate Host: Packet received:");
	          System.out.println("From host: " + receivePacket.getAddress());
	          System.out.println("Host port: " + receivePacket.getPort());
	          int len = receivePacket.getLength();
	          System.out.println("Length: " + len);
	          System.out.print("Containing:\n" + "In string:\n" );
	          String received = new String(data,0,len);   
		      System.out.println(received + "\n" + "In byte array:\n" + Arrays.toString(data));
	        
	      } catch(IOException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }


	      // Form a String from the byte array.
	      
	      try {
	         sendPacket = new DatagramPacket(data, data.length,
	                                         InetAddress.getLocalHost(), portNumber2);
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }
	      
	      //

	      try {
	    	  sendReceieveSocketServer.send(sendPacket);
	      } catch (IOException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }
	      System.out.println("Intermediate Host: Packet sent to server\n");

	   }
	
	public void sendReceieveServer(){
		
    // Construct a DatagramPacket for receiving packets up 
    // to 100 bytes long (the length of the byte array).

    byte data[] = new byte[4];
    receivePacket1 = new DatagramPacket(data, data.length);

    try {
       // Block until a datagram is received via sendReceiveSocket.  
    	sendReceieveSocketServer.receive(receivePacket1);
    	System.out.println("Intermediate Host: receiving packet from server\n" + "Containing (as Bytes): " + Arrays.toString(data));
  	  
    	System.out.println("Intermediate Host: Packet received:");
    	System.out.println("From host: " + receivePacket1.getAddress());
    	System.out.println("Host port: " + receivePacket1.getPort());
    	int len = receivePacket1.getLength();
    	System.out.println("Length: " + len);
    	String received = new String(data,0,len);
    	System.out.print("Containing:\n" + "In string:\n" + received + "\nIn byte array:" + Arrays.toString(data) );
  
    } catch(IOException e) {
       e.printStackTrace();
       System.exit(1);
    }
    
    
    try {
        sendPacket1 = new DatagramPacket(data, data.length,
                                        InetAddress.getLocalHost(), receivePacket.getPort());
     } catch (UnknownHostException e) {
        e.printStackTrace();
        System.exit(1);
     }

     System.out.println("Intermediate Host: sending packet to client:\n" +"To host: " + sendPacket1.getAddress());
     System.out.println("Destination host port: " + receivePacket.getPort());
     int len = sendPacket1.getLength();
     System.out.println("Length: " + len);
     System.out.print("Containing: ");
     System.out.println(new String(sendPacket1.getData(),0,len)); // or could print "s"

     // Send the datagram packet to the server via the send/receive socket. 

     try {
   	  sendReceieveSocketServer.send(sendPacket1);
     } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
     }

     System.out.println("Intermediate Host: Packet sent.\n");
     System.out.println("----------------------------------------------------");

	}
    
	public static void main(String args[]){
		IntermediateHost c = new IntermediateHost();
	    for(;;) { 
	    	c.sendAndReceiveClient();
	    	c.sendReceieveServer();
	    }
	}

	
}

