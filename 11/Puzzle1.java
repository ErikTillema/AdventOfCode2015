import java.util.*;
import java.io.*;

class Puzzle {

	public static void main(String[] args) throws Exception {
		String start;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			start = reader.readLine();
		}
		
		String result = getNextPassword(start);
		System.out.println(""+result);
	}
	
	static String getNextPassword(String s) {
		String start = s;
		while(!isValid(start)) start = start.substring(0, start.length() - 1);
		
		if(start.length() < 8) {
			start = start + (char)(s.charAt(start.length()) + 1);
		}
		
		while(start.length() < 8) start = start + "a";
		
		String result = start;
		while(!isGood(result)) result = getNext(result);
		return result;
	}
	
	static String getNext(String s) {
		char[] c = s.toCharArray();
		int pos = c.length - 1;
		while(c[pos] == 'z') pos--;
		c[pos]++;
		for(int i=pos+1; i<c.length; i++) c[i] = 'a';
		return new String(c);
	}
	
	static boolean isGood(String s) {
		if(!isValid(s)) return false;
		
		char[] c = s.toCharArray();

		int longestStraight = 1;
		int straight = 1;
		for(int i=1; i<c.length; i++) {
			if(c[i] == c[i-1]+1) {
				straight++;
				if(straight > longestStraight) longestStraight = straight;
			} else {
				straight = 1;
			}
		}
		if(longestStraight < 3) return false;
		
		int pairs = 0;
		boolean counts = true;
		for(int i=1; i<c.length; i++) {
			if(c[i] == c[i-1]) {
				if(counts) {
					pairs++;
					counts = false;
				} else {
					counts = true;
				}
			} else {
				counts = true;
			}
		}
		return pairs > 1;
	}
	
	static boolean isValid(String s) {
		return !s.contains("i") && !s.contains("l") && !s.contains("o");
	}
	
}

