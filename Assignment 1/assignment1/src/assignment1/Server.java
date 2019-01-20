package assignment1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Server {
	
/*Server waits to recieve packets from the Client through the IntermediatHost and then replys
accordingly depending on whether it is a write or read request. Printing statments where 
neccessary to show user of the progess of the packet transaction*/
	
	// Variable Declaration
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, receiveSocket;
	int portNumber2 = 69;
	boolean read;
	
	public Server(){
		 try {
		      // Construct a datagram socket and bind it to any available 
		      // port on the local host machine. This socket will be used to
		      // send UDP Datagram packets.
		      sendSocket = new DatagramSocket();

		      // Construct a datagram socket and bind it to port 5000 
		      // on the local host machine. This socket will be used to
		      // receive UDP Datagram packets.
		      receiveSocket = new DatagramSocket(portNumber2);
		      
		      // to test socket timeout (2 seconds)
		      //receiveSocket.setSoTimeout(2345);
		   } catch (SocketException se) {
		      se.printStackTrace();
		      System.exit(1);
		   } 
		}

	public void receiveAndEcho()
	{
	   // Construct a DatagramPacket for receiving packets up 
	   // to 100 bytes long (the length of the byte array).

	   byte data[] = new byte[17];
	   byte responseData[] = new byte[4];
	   receivePacket = new DatagramPacket(data, data.length);
	   System.out.println("");
	   System.out.println("Server: Waiting for Packet.\n");

	   // Block until a datagram packet is received from receiveSocket.
	   try {        
	      System.out.println("Waiting...\n"); // so we know we're waiting
	      receiveSocket.receive(receivePacket);
	   } catch (IOException e) {
		  
	      System.out.print("IO Exception: likely:");
	      System.out.println("Receive Socket Timed Out.\n" + e);
	      e.printStackTrace();
	      System.exit(1);
	   }

	   // Process the received datagram.
	   System.out.println("Server: Packet received:");
	   System.out.println("From host: " + receivePacket.getAddress());
	   System.out.println("Host port: " + receivePacket.getPort());
	   int len = receivePacket.getLength();
	   System.out.println("Length: " + len);
	   
	   // Form a String from the byte array.
	   String received = new String(data,0,len);
	   
	   checkPacketValidity(data);
	   System.out.print("Containing: \n" );
	   System.out.println("In string: " + received + "\n" + "In byte array: \n" + Arrays.toString(data) + "\n" );
	   
	   // Slow things down (wait 1.5 seconds)
	   try {
	       Thread.sleep(1500);
	   } catch (InterruptedException e ) {
	       e.printStackTrace();
	       System.exit(1);
	   }

	   // Create a new datagram packet containing the string received from the client.

	   // Construct a datagram packet that is to be sent to a specified port 
	   // on a specified host.
	   // The arguments are:
	   //  data - the packet data (a byte array). This is the packet data
	   //         that was received from the client.
	   //  receivePacket.getLength() - the length of the packet data.
	   //    Since we are echoing the received packet, this is the length 
	   //    of the received packet's data. 
	   //    This value is <= data.length (the length of the byte array).
	   //  receivePacket.getAddress() - the Internet address of the 
	   //     destination host. Since we want to send a packet back to the 
	   //     client, we extract the address of the machine where the
	   //     client is running from the datagram that was sent to us by 
	   //     the client.
	   //  receivePacket.getPort() - the destination port number on the 
	   //     destination host where the client is running. The client
	   //     sends and receives datagrams through the same socket/port,
	   //     so we extract the port that the client used to send us the
	   //     datagram, and use that as the destination port for the echoed
	   //     packet.

	   responseData = makeResponsePacket(read);
	   
	   sendPacket = new DatagramPacket(responseData, responseData.length,
	                            receivePacket.getAddress(), receivePacket.getPort());

	   System.out.println( "Server: Sending packet:");
	   System.out.println("To host: " + sendPacket.getAddress());
	   System.out.println("Destination host port: " + sendPacket.getPort());
	   len = sendPacket.getLength();
	   System.out.println("Length: " + len);
	   System.out.print("Containing: ");
	   System.out.println(Arrays.toString(responseData));
	   // or (as we should be sending back the same thing)
	       
       
  	   // Send the datagram packet to the client via the send socket. 
	   try {
	      sendSocket.send(sendPacket);
	   } catch (IOException e) {
	      e.printStackTrace();
	      System.exit(1);
	   }

	   System.out.println("Server: packet sent");
	   // To show end of a cycle
	   System.out.println("-----------------------------------------------");
	   
	  
	}
	
	public boolean checkPacketValidity(byte data[]) {
		// checks the validity of the packet received as per specifications
		if(data[0] != 0) { 
			// if contents of packet does not start with 0 then packet is invalid
			serverClose(true); // quit server if packet is invalid
			return false;
		}
		if(data[1] != 1 || data[1] != 2) {
			// if the second byte of packet does is not 1 or 2 then packet is invalid
			if (data[1] == 1) {	
				//If contents of packet in byte 1 is 1, then it is a read request and a 
				//specific response will be generated using makeResponsePacket method
				read = true; 
				return true;
			}
			if (data[1] == 2) {
				//If contents of packet in byte 1 is 2, then it is a write request and a 
				//specific response will be generated using makeResponsePacket method
				read = false;
				return true;
			}
			serverClose(true); // quit server if packet is invalid
			return false;
		}
		if(data[10] != 0 | data[16] != 0) {
			// As per specification - byte 10 and byte 16 should be 0
			serverClose(true); // quit server if packet is invalid
			
			return false;
		}
		return true; //program continues if packet is valid
	}
	
	public byte[] makeResponsePacket(boolean read) {
		/*makeResponsePacket method generates a response depending on the packet sent
		whether it is a read or write*/
		
		ByteArrayOutputStream responseData = new ByteArrayOutputStream();
		responseData.write(0);
		if (read == true) {
			//As per specification
			responseData.write(3);
			responseData.write(0);
			responseData.write(1);
		}
		if (read == false) {
			//As per specification
			responseData.write(4);
			responseData.write(0);
			responseData.write(0);
		}
		return responseData.toByteArray();
		
	}
	
	public void serverClose(boolean close){
		//serverClose method prints out an error messsage, then closes the servers and the utilized Sockets
		if(close == true) {	
		System.out.println("\nServer: Invalid Packet\n*Server Haulted*");
		sendSocket.close();
		receiveSocket.close();
		System.exit(1);
		}
	}
		
				

public static void main( String args[] )
{
   Server c = new Server();
   for(;;) { // infinite loop is used for the Server to be always listening for the Intermediate Host
	   c.receiveAndEcho();
	   
   }
   
}
}

