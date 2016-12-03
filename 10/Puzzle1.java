import java.util.*;
import java.io.*;

class Puzzle {
	

	public static void main(String[] args) throws Exception {
		String start;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			start = reader.readLine();
		}
		
		String result = start;
		for(int i=0; i<50; i++) {
			result = lookAndSay(result);
			//System.out.println(result);
		}
		
		System.out.println(""+result.length());
	}
	
	static String lookAndSay(String s) {
		char[] cc = s.toCharArray();
		int[] c = new int[cc.length];
		for(int i=0; i<cc.length; i++) c[i] = (cc[i] - '0');
		
		StringBuilder result = new StringBuilder();
		int val = -1;
		int cnt = 0;
		int pos = 0;
		while(pos < c.length) {
			if(c[pos] == val) {
				// same
				cnt++;
			} else {
				// change
				if(val != -1)
					result.append(""+cnt+val);
				
				val = c[pos];
				cnt = 1;
			}
			pos++;
		}
		result.append(""+cnt+val);
		
		return result.toString();
	}
	
}

