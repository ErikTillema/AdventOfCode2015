import java.util.*;
import java.io.*;

class Puzzle { 
	
	public static void main(String[] args) throws Exception {
		String input;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			input = reader.readLine();
		}
		
		char[] c = input.toCharArray();
		int pos = 0;
		int floor = 0;
		while(floor != -1) {
			if(c[pos] == '(') floor++;
			else floor--;
			pos++;
		}
		System.out.println(""+pos);
	}
	
}