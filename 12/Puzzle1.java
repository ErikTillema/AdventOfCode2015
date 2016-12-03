import java.util.*;
import java.io.*;

class Puzzle {

	public static void main(String[] args) throws Exception {
		int result = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				result += sum(line);
			}
		}
		
		System.out.println(""+result);
	}
	
	static int sum(String s) {
		int result = 0;
		Scanner scanner = new Scanner(s).useDelimiter("[,|\\]|\\[|:|\\{|\\}|\"]"); //  [ ] { } , : \" 
		while(scanner.hasNext()) {
			String token = scanner.next();
			//System.out.println(token);
			try {
				int value = Integer.parseInt(token);
				result += value;
			} catch(Exception e) {}
		}
		return result;
	}
	
}

