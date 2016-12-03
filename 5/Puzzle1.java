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
		return hasThreeVowels(c) && hasDoubleChar(c) && !contains(s);
	}
	
	private static boolean contains(String s) {
		return s.contains("ab") || s.contains("cd") || s.contains("pq") || s.contains("xy");
	}
	
	private static boolean hasDoubleChar(char[] c) {
		char lastChar = 0;
		for(int i=0; i<c.length; i++) {
			if(lastChar != 0 && lastChar == c[i]) return true;
			lastChar = c[i];
		}
		return false;
	}
	
	private static boolean hasThreeVowels(char[] c) {
		int vowels = 0;
		for(int i=0; i<c.length; i++) {
			if(c[i] == 'a' || c[i] == 'e' || c[i] == 'i' || c[i] == 'o' || c[i] == 'u' )
				vowels++;
		}
		return vowels >= 3;
	}
		
}