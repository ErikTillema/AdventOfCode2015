import java.util.*;
import java.io.*;

class Puzzle {

	static long best;
	static int n;
	static ArrayList<Ingredient> ingredients;

	public static void main(String[] args) throws Exception {
		ingredients = new ArrayList<Ingredient>();
		
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				ingredients.add(getIngredient(line));
			}
		}
		
		n = ingredients.size();
		best = Long.MIN_VALUE;
		int[] used = new int[n];
		solve(0, used, 100);
		System.out.println(""+best);
	}
	
	static void solve(int done, int[] used, int left) {
		if(done == n-1) {
			used[done] = left;
			long trial = 1;
			int[] valsum = new int[5];
			for(int j=0; j<5; j++) {
				for(int i=0; i<n; i++) {
					valsum[j] += used[i] * ingredients.get(i).val[j];
				}
				if(valsum[j] < 0) valsum[j] = 0;
				if(j<4)
					trial *= valsum[j];
			}
			if(valsum[4] == 500)
				 best = Math.max(best, trial);
		} else {
			for(int i=0; i<=left; i++) {
				used[done] = i;
				solve(done+1, used, left - i);
			}
		}
	}
	
	static Ingredient getIngredient(String s) {
		String[] parts = s.split(" |,|:");
		Ingredient result = new Ingredient();
		result.name = parts[0];
		result.val = new int[5];
		result.val[0] = Integer.parseInt(parts[3]);
		result.val[1] = Integer.parseInt(parts[6]);
		result.val[2] = Integer.parseInt(parts[9]);
		result.val[3] = Integer.parseInt(parts[12]);
		result.val[4] = Integer.parseInt(parts[15]);
		return result;
	}
	
	
}

class Ingredient {
	int[] val;
	String name;
}