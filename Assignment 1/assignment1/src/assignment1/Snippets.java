package assignment1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Snippets {

public Snippets() {
int j = 0;
for(int i=0;i<11; i++) {
	
	if (i == 10){
		System.out.println("Invalid");
	}
	
	else {
		switch(j) {
			case 0:
				// read
		        System.out.println("read"); 
		        j = 1;
		        // starts with 01
		        byte[] sendReadPacket=makePacket(true);
		        System.out.println(Arrays.toString(sendReadPacket));
		        

		        break;
				
			case 1:
				//write
		        System.out.println("write"); 
		        j=0;
		        // starts with 02
		        byte[] sendWritePacket=makePacket(false);
		        System.out.println(Arrays.toString(sendWritePacket));
		        break;
		}
	
	}
	
}
}

public String stringLength(byte [] sendPacket) {
	int len = sendPacket.getLength();
	return (new String(sendReadPacket.getData(),0,len)); 
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
   Snippets s= new Snippets();
   
}
}

