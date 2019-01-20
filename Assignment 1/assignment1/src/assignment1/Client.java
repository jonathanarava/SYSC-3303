package assignment1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;


public class Client {
	
/*Client class sends alternating read and write requests to Server through Intermediate Host
As per specification - 11 iterations of alternating read and write requests with the 11th iteration being an invalid request*/

// Variable Declaration
DatagramPacket sendPacket, receivePacket;
DatagramSocket sendReceiveSocket;
int portNumber1 = 23;

public Client() {
	int j = 0;
	
	try {
	    // Construct a datagram socket and bind it to any available 
	    // port on the local host machine. This socket will be used to
	    // send and receive UDP Datagram packets.
	    sendReceiveSocket = new DatagramSocket();
	 } catch (SocketException se) {   // Can't create the socket.
	    se.printStackTrace();
	    System.exit(1);
	 }
	
	/*As per specification the client will send and receive 11 times*/
	for(int i=0;i<11; i++) {
		//As per specification - for the 11th try an invalid entry will be sent
		if (i == 10){
			System.out.println("Client: Sending Invalid request packet:");
			String in = "Invalid";
			byte[] invalid = in.getBytes();
			sendAndReceive(invalid);
		}
		//As per specification- alternate between read (0 1) and write (0 2) requests
		else {
			switch(j) {
				// i is inizialized at 0 to be a write request on the first iteration
				case 0:
					// read request
					System.out.println("Client: Sending read request packet:");
			        j = 1; //sets j to 1 to go through case 1 (write request) on the next iteration 
			        byte[] sendReadPacket = makeBArrayforPacket(true); // creates byte array with 01 as the staring bytes
			        sendAndReceive(sendReadPacket); 
			        System.out.println();
			        break;
					
				case 1:
					//write request
					System.out.println("Client: Sending write request packet:"); 
			        j=0; //sets j to 0 to go through case 0 (write request) on the next iteration
			        byte[] sendWritePacket=makeBArrayforPacket(false); // byte array with 02 as the staring bytes
			        sendAndReceive(sendWritePacket);
			        System.out.println();
			        break;
			}
		
		}
		
	}
}

public void sendAndReceive(byte msg[]) {
	
	/*Method sendAndReceive(byte msg[]) takes a byte array as a parameter and makes it into 
	packet which it sends to a server and then waits to receive an acknowledgement packet from the server*/
	
	try {
	      sendPacket = new DatagramPacket(msg, msg.length,
	                                      InetAddress.getLocalHost(), portNumber1);
	   } catch (UnknownHostException e) {
	      e.printStackTrace();
	      System.exit(1);
	   }

	   //System.out.println("Client: Sending packet:");
	   System.out.println("To host: " + sendPacket.getAddress());
	   System.out.println("Destination host port: " + sendPacket.getPort());
	   int len = sendPacket.getLength();
	   System.out.println("Length: " + len);
	   System.out.println("Containing: ");
	   System.out.println("In string: " + new String(sendPacket.getData(),0,len));
	   System.out.println("In byte array: ");
	   System.out.println(Arrays.toString(msg));
	   
	   
	   // Send the datagram packet to the server via the send/receive socket. 
	   try {
	      sendReceiveSocket.send(sendPacket);
	   } catch (IOException e) {
	      e.printStackTrace();
	      System.exit(1);
	   }

	   System.out.println("Client: Packet sent.\n");

	   // Construct a DatagramPacket for receiving packets up 
	   // to 4 bytes long (the length of the byte array).

	   byte data[] = new byte[4];
	   
	   receivePacket = new DatagramPacket(data, data.length);

	   try {
	      // Block until a datagram is received via sendReceiveSocket.  
	      sendReceiveSocket.receive(receivePacket);
	   } catch(IOException e) {
	      e.printStackTrace();
	      System.exit(1);
	   }

	   // Process the received datagram.
	   System.out.println("Client: Packet received:");
	   System.out.println("From host: " + receivePacket.getAddress());
	   System.out.println("Host port: " + receivePacket.getPort());
	   len = receivePacket.getLength();
	   System.out.println("Length: " + len);
	   
	   // Form a String from the byte array.
	   String received = new String(data,0,len);
	   System.out.print("Received packet containing: \n" + "In string:\n" + received + "\nIn byte array: \n" + Arrays.toString(data) + "\n");
	   
	   // To show end of a cycle (Client   >   Intermediate Host   >   Server   >   Intermediate Host   >    Client)
	   System.out.println("-------------------------------------------"); 
	   
}


public byte[] makeBArrayforPacket(boolean read) {
	/*Method makeBArrayforPacket(boolean read) takes in a boolean read and returns a byte array according to the value of the boolean entered
	 * which is later made into a packet in the sendAndReceive(byte msg[]) method
	 * read = true meaning that it is a read request
	 * read = false meaning it is a write request*/
	ByteArrayOutputStream data = new ByteArrayOutputStream();
	data.write(0); //As per specification - first byte 0
	if (read == true) {
		data.write(1); //As per specification - second byte 1 if read request
	}
	if (read == false) {
		data.write(2); //As per specification - second byte 2 if write request
	}
	String filename = "test.txt";
	try {
		data.write(filename.getBytes());
	} catch (IOException e) {
		e.printStackTrace();
	}
	data.write(0); //As per specification -  byte 2 between the filename and mode is 0
	String mode = "ocTEt";
	try {
		data.write(mode.getBytes());
	} catch (IOException e) {
		e.printStackTrace();
	}
	data.write(0); //As per specification - last byte is 0
	return data.toByteArray();
	
}
	
	
public static void main(String args[]){
   Client s= new Client();
   
}
}

