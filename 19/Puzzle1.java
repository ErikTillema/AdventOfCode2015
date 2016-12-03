import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws IOException {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	List<Transformation> transformations = new LinkedList<Transformation>();
	HashSet<String> results = new HashSet<String>();
	
	public void solve() throws IOException {
		String input = null;
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				if(line.contains(" => ")) {
					Scanner scanner = new Scanner(line).useDelimiter(" => ");
					Transformation transformation = new Transformation();
					transformation.from = scanner.next();
					transformation.to = scanner.next();
					System.out.println("" + transformation.from + " " + transformation.to);
					transformations.add(transformation);
				} else if(line.length() > 0) {
					input = line;
				}
			}
		}
		
		char[] c = input.toCharArray();
		int n = c.length;
		for(int i=0; i<n; i++) {
			for(int l=1; l<=2; l++) {
				String s = "" + c[i] + ((l > 1 && i+l-1 < n) ? "" + c[i+1] : "");
				for(Transformation transformation : transformations) {
					if(s.equals(transformation.from)) {
						String newresult = input.substring(0, i) + transformation.to + input.substring(i+l);
						if(!results.contains(newresult)) {
							results.add(newresult);
							if(results.size() == 1) System.out.println(newresult);
						}
					} else {
						//	System.out.println("already in results");
					}
				}
			}
		}
		
		System.out.println(results.size() + "");
	}
	
}

class Transformation {
	public String from, to;
}