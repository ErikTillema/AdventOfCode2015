import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws IOException {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	private ArrayList<String> lines;
	private int curLine, a, b;
	
	public void solve() throws IOException {
		//read program
		lines = new ArrayList<String>();
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				lines.add(line);
			}
		}
		
		a = 1; b = 0; curLine = 0;
		while(curLine < lines.size()) {
			applyLine();
		}
		System.out.println("a = " + a + ", b = " + b + "");
	}

	private void applyLine() {
		String line = lines.get(curLine);
		String[] parts = line.split(" |,");
		if(parts[0].equals("hlf")) {
			if(parts[1].equals("a")) a /= 2;
			else b /= 2;
			curLine++;
		} else if(parts[0].equals("tpl")) {
			if(parts[1].equals("a")) a *= 3;
			else b *= 3;
			curLine++;
		} else if(parts[0].equals("inc")) {
			if(parts[1].equals("a")) a++;
			else b++;
			curLine++;
		} else if(parts[0].equals("jmp")) {
			int offset = Integer.parseInt(parts[1]);
			curLine += offset;
		} else if(parts[0].equals("jie")) {
			int offset = Integer.parseInt(parts[3]);
			if(parts[1].equals("a")) {
				if((a%2) == 0) {
					curLine += offset;
				} else {
					curLine++;
				}
			} else {
				if((b%2) == 0) {
					curLine += offset;
				} else {
					curLine++;
				}
			}
		} else if(parts[0].equals("jio")) {
			int offset = Integer.parseInt(parts[3]);
			if(parts[1].equals("a")) {
				if(a == 1) {
					curLine += offset;
				} else {
					curLine++;
				}
			} else {
				if(b == 1) {
					curLine += offset;
				} else {
					curLine++;
				}
			}
		}
	}
}