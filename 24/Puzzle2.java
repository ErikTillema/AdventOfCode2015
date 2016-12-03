import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	private ArrayList<Integer> w;
	private int n, target;
	private long minProduct;
	private boolean[] taken;
	
	public void solve() throws Exception {
		//read program
		w = new ArrayList<Integer>();
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				w.add(Integer.parseInt(line));
			}
		}
		n = w.size();
		int sum = 0;
		for(Integer ww : w) {
			sum += ww;
		}
		if((sum % 4) != 0) throw new Exception("error");
		target = sum / 4;
		
		int toTake = 1;
		while(true) {
			minProduct = Long.MAX_VALUE;
			taken = new boolean[n];
			boolean possible = isPossible(toTake, 0, 0, 1);
			if(possible) break;
			toTake++;
		}
		System.out.println("toTake = " + toTake + ", minProduct = " + minProduct + "");
	}
	
	private boolean isPossible(int toTake, int done, int sum, long product) throws Exception {
		if(done == toTake) {
			if(product < 0) throw new Exception("overflow");
			boolean ok = (sum == target) && (product < minProduct) && isDivisable();
			if(ok) {
				minProduct = Math.min(minProduct, product);
				return true;
			} else {
				return false;
			}
		} else {
			boolean result = false;
			// take another item
			for(int i=0; i<n; i++) {
				if(!taken[i]) {
					taken[i] = true;
					result |= isPossible(toTake, done+1, sum + w.get(i), product * w.get(i) );
					taken[i] = false;
				}
			}
			return result;
		}
	}
	
	private boolean isDivisable() {
		boolean[][][] possible = new boolean[n+1][target+1][target+1];
		possible[0][0][0] = true;
		for(int i=0; i<n; i++) {
			for(int sx=0; sx<=target; sx++) {
				for(int sz=0; sz<=target; sz++) {
					if(possible[i][sx][sz]) {
						// don't add to group (so add to last group)
						possible[i+1][sx][sz] = true;
						
						// add to group x
						int newsx = sx + w.get(i);
						if(!taken[i] && newsx <= target) {
							possible[i+1][newsx][sz] = true;
						}
						
						// add to group z
						int newsz = sz + w.get(i);
						if(!taken[i] && newsz <= target) {
							possible[i+1][sx][newsz] = true;
						}
					}
				}
			}
		}
		return possible[n][target][target];
	}

}