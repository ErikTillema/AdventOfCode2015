import java.util.*;
import java.io.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		int result = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				if(isNice(line)) result++;
			}
		}
		
		System.out.println(""+result);
	}
	
	private static boolean isNice(String s) {
		char[] c = s.toCharArray();
		int[] ints = new int[c.length];
		for(int i=0; i<c.length; i++) {
			ints[i] = (int)(c[i] - 'a');
		}
		return hasPairTwice(ints) && hasABA(ints);
	}
	
	private static boolean hasPairTwice(int[] c) {
		int[][] pairSeen = new int[26][26];
		
		char lastChar = 0;
		for(int i=1; i<c.length; i++) {
			if(pairSeen[c[i-1]][c[i]] != 0 && pairSeen[c[i-1]][c[i]] < i-1 )
				return true;
			if(pairSeen[c[i-1]][c[i]] == 0)
				pairSeen[c[i-1]][c[i]] = i;
		}
		return false;
	}
	
	private static boolean hasABA(int[] c) {
		for(int i=2; i<c.length; i++) {
			if(c[i] == c[i-2]) return true;
		}
		return false;
	}
		
}