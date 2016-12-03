import java.util.*;
import java.io.*;

class Puzzle {
	
	public static void main(String[] args) throws Exception {
		int result = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				result += getRibbon(line);
			}
		}
		
		System.out.println(""+result);
	}
	
	private static int getRibbon(String s) {
		String[] parts = s.split("x");
		int[] dims = new int[3];
		for(int i=0; i<3; i++) {
			dims[i] = Integer.parseInt(parts[i]);
		}
		return getRibbon(dims);
	}
	
	private static int getRibbon(int[] dims) {
		int vol = dims[0]*dims[1]*dims[2];
		Arrays.sort(dims);
		int around = 2*(dims[0]+dims[1]);
		return around + vol;
	}
	
}