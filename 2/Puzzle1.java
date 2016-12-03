import java.util.*;
import java.io.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		int result = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				result += getPaper(line);
			}
		}
		
		System.out.println(""+result);
	}
	
	private static int getPaper(String s) {
		String[] parts = s.split("x");
		return getPaper( Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]) );
	}
	
	private static int getPaper(int l, int w, int h) {
		int a1 = l*w;
		int a2 = l*h;
		int a3 = w*h;
		int mina = Math.min(a1, Math.min(a2, a3));
		return 2*a1 + 2*a2 + 2*a3 + mina;
	}
	
}