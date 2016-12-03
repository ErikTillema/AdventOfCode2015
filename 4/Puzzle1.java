import java.util.*;
import java.io.*;
import java.security.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		String secret;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			secret = reader.readLine();
		}
		
		//isCorrect("abcdef609043");
		//return;

		int result = 1;
		while(!isCorrect(secret+result)) result++;
		System.out.println(""+result);
	}
	
	static boolean isCorrect(String s) throws Exception {
		//System.out.println(s);
		////byte[] bytesOfMessage = s.getBytes("UTF-8");
		byte[] bytesOfMessage = s.getBytes();
		//for(int i=0; i<bytesOfMessage.length; i++) {
		//	System.out.print(bytesOfMessage[i] + " ");
		//}
		//System.out.println();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		int l = thedigest.length;
		//System.out.println(l);
		//for(int i=0; i<thedigest.length; i++) {
		//	System.out.print(thedigest[i] + " ");
		//}
		//System.out.println();
		
		// 1 hexadecimal = 16 options so 4 bits, so 2 hexadecimals = 8 bits = 1 byte.
		// 5 hexadecimal zeroes = 20 bits zero. So 2 bytes + 4 bits of 3rd byte.
		return thedigest[0] == 0 && thedigest[1] == 0 && (thedigest[2] >> 4) == 0;
	}
	
}
