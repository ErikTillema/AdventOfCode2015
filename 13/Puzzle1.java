import java.util.*;
import java.io.*;

class Puzzle {

	static int n;
	static HashMap<String,Integer> index = new HashMap<String,Integer>();
	static int[][] happiness; // if i sits next to j,symmetric
	static int result = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws Exception {
		n = 9;
		happiness = new int[n][n];
		
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				setHappiness(line);
			}
		}
		
		int[] personAt = new int[n];
		boolean[] seated = new boolean[n];
		personAt[0] = 0;
		seated[0] = true;
		solve(1, personAt, seated);
		
		System.out.println(""+result);
	}
	
	static void solve(int done, int[] personAt, boolean[] seated) {
		if(done == n) {
			int trial = 0;
			for(int i=1; i<n; i++) {
				trial += happiness[personAt[i]][personAt[i-1]];
			}
			trial += happiness[personAt[0]][personAt[n-1]];
			result = Math.max(trial, result);
		} else {
			for(int i=0; i<n; i++) {
				if(!seated[i]) {
					seated[i] = true;
					personAt[done] = i;
					solve(done+1, personAt, seated);
					seated[i] = false;
				}
			}
		}
	}
	
	static void setHappiness(String s) {
		String[] parts = s.split(" |\\.");
		int i = getIndex(parts[0]);
		int j = getIndex(parts[10]);
		int v = Integer.parseInt(parts[3]);
		if(parts[2].equals("lose")) v *= -1;
		System.out.println("i : " + i + ", j : " + j + ", V: " + v);
		happiness[i][j] += v;
		happiness[j][i] += v;
	}
	
	static int getIndex(String name) {
		if(!index.containsKey(name)) {
			index.put(name, index.size());
		}
		return index.get(name);
	}
	
}