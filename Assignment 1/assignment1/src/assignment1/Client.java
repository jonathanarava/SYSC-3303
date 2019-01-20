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
	
	for(int i=0;i<11; i++) {
		
		if (i == 10){
			System.out.println("Client: Sending Invalid request packet:");
			String in = "Invalid";
			byte[] invalid = in.getBytes();
			sendAndReceive(invalid);
			//sendReceiveSocket.close();
			//receiveSocket.close();
		}
		
		else {
			switch(j) {
				case 0:
					// read
					System.out.println("Client: Sending read request packet:");
			        j = 1;
			        // starts with 01
			        byte[] sendReadPacket = makePacket(true);
			        sendAndReceive(sendReadPacket);
			        System.out.println();
			        
			        
			        break;
					
				case 1:
					//write
					System.out.println("Client: Sending write request packet:"); 
			        j=0;
			        // starts with 02
			        byte[] sendWritePacket=makePacket(false);
			        sendAndReceive(sendWritePacket);
			        System.out.println();
			        break;
			}
		
		}
		
	}
}

public void sendAndReceive(byte msg[]) {
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
	   //System.out.println(new String(sendPacket.getData(),0,len)); // or could print "s"
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
	   // to 100 bytes long (the length of the byte array).

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
	  // System.out.println(Arrays.toString(data));
	   System.out.println("-------------------------------------------");
	   // We're finished, so close the socket.
	   //sendReceiveSocket.close();
}


public byte[] makePacket(boolean read) {
	ByteArrayOutputStream data = new ByteArrayOutputStream();
	data.write(0);
	if (read == true) {
		data.write(1);
	}
	if (read == false) {
		data.write(2);
	}
	String filename = "test.txt";
	try {
		data.write(filename.getBytes());
	} catch (IOException e) {
		e.printStackTrace();
	}
	data.write(0);
	String mode = "ocTEt";
	try {
		data.write(mode.getBytes());
	} catch (IOException e) {
		e.printStackTrace();
	}
	data.write(0);
	return data.toByteArray();
	
}
	
	

public static void main(String args[]){
   Client s= new Client();
   
}
}

