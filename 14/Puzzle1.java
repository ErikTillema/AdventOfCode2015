import java.util.*;
import java.io.*;

class Puzzle {

	public static void main(String[] args) throws Exception {
		List<Reindeer> reindeers = new LinkedList<Reindeer>();
		
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				reindeers.add(getReindeer(line));
			}
		}
		
		int best = Integer.MIN_VALUE;
		for(Reindeer reindeer : reindeers) {
			int trial = reindeer.getDist(2503);
			best = Math.max(best, trial);
		}
		
		System.out.println(""+best);
	}
	
	static Reindeer getReindeer(String s) {
		String[] parts = s.split(" ");
		Reindeer result = new Reindeer();
		result.v = Integer.parseInt(parts[3]);
		result.flySeconds = Integer.parseInt(parts[6]);
		result.restSeconds = Integer.parseInt(parts[13]);
		System.out.println("v = " + result.v + ", flySeconds = " + result.flySeconds + ", restSeconds = " + result.restSeconds);
		return result;
	}
	
	
}

class Reindeer {
	int v, flySeconds, restSeconds;
	int getDist(int seconds) {
		int roundSeconds = flySeconds + restSeconds;
		int roundDist = v * flySeconds;
		int rounds = seconds / roundSeconds;
		int result = rounds * roundDist;
		int leftSeconds = seconds % roundSeconds;
		result += Math.min(leftSeconds, flySeconds) * v;
		return result;
	}
}