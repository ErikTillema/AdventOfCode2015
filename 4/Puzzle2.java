import java.util.*;
import java.io.*;
import java.security.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		String secret;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			secret = reader.readLine();
		}
		
		int result = 1;
		while(!isCorrect(secret+result)) result++;
		System.out.println(""+result);
	}
	
	static boolean isCorrect(String s) throws Exception {
		//byte[] bytesOfMessage = s.getBytes("UTF-8");
		byte[] bytesOfMessage = s.getBytes();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		// 1 hexadecimal = 16 options so 4 bits, so 2 hexadecimals = 8 bits = 1 byte.
		// 6 hexadecimal zeroes = 24 bits zero. So 3 bytes.
		return thedigest[0] == 0 && thedigest[1] == 0 && thedigest[2] == 0;
	}
	
}
