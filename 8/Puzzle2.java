import java.util.*;
import java.io.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		int result = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				result += getEncodedLength(line) - line.length();
			}
		}
		
		System.out.println(""+result);
	}
	
	private static int getEncodedLength(String s) {
		char[] c = s.toCharArray();
		int cnt = 0;
		for(int i=0; i< c.length; i++) {
			if(c[i] == '\\' || c[i] == '\"') cnt++;
		}
		return 2 + c.length + cnt;
	}
	
	
}