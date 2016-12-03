import java.util.*;
import java.io.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		int result = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				result += line.length() - getRealLength(line);
			}
		}
		
		System.out.println(""+result);
	}
	
	private static int getRealLength(String s) {
		s = s.substring(1, s.length() - 1);
		char[] c = s.toCharArray();
		int result = 0;
		int pos = 0;
		while(pos < s.length()) {
			if(c[pos] == '\\') {
				if(c[pos+1] == 'x') {
					pos+=4;
					result++;
				} else {
					pos+=2;
					result++;
				}
			} else {
				pos++;
				result++;
			}
		}
		return result;
	}
	
	
}