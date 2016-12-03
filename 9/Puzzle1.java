import java.util.*;
import java.io.*;

class Puzzle {
	
	static HashMap<String, Integer> cityIndex;
	static int n;
	static int[][] dist;
	static int bestDist = Integer.MAX_VALUE;
	static int[] orderIndex;
	static boolean[] taken;
	
	public static void main(String[] args) throws Exception {
		n = 8;
		cityIndex = new HashMap<String, Integer>();
		dist = new int[n][n];
		
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				Scanner scanner = new Scanner(line);
				int c1 = getCityIndex(scanner.next()).intValue();
				scanner.next();
				int c2 = getCityIndex(scanner.next()).intValue();
				scanner.next();
				int d = scanner.nextInt();
				System.out.println("c1 = " + c1 + ", c2 = " + c2 + ", d = "+d);
				dist[c1][c2] = dist[c2][c1] = d;
			}
		}
		
		orderIndex = new int[n];
		taken = new boolean[n];
		solve(0);
		
		System.out.println(""+bestDist);
	}
	
	static void solve(int done) {
		if(done == n) {
			int tryDist = 0;
			for(int i=1; i<n; i++) {
				int c1 = orderIndex[i-1];
				int c2 = orderIndex[i];
				tryDist += dist[c1][c2];
			}
			if(tryDist < bestDist) bestDist = tryDist;
		} else {
			for(int i=0; i<n; i++) {
				if(!taken[i]) {
					//place done at i
					taken[i] = true;
					orderIndex[done] = i;
					solve(done+1);
					
					// free again
					taken[i] = false;
				}
			}
		}
	}
	
	static Integer getCityIndex(String s) {
		if(!cityIndex.containsKey(s)) {
			int newIndex = cityIndex.size();
			cityIndex.put(s, new Integer(newIndex));
		}
		return cityIndex.get(s);
	}
	
}

