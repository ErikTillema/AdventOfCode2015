import java.util.*;
import java.io.*;

class Puzzle {

	public static void main(String[] args) throws Exception {
		int n = 9;
		Reindeer[] reindeers = new Reindeer[n];
		
		int i = 0;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				reindeers[i++] = getReindeer(line);
			}
		}
		
		int seconds = 2503;
		for(int j=0; j<seconds; j++) {
			for(Reindeer reindeer : reindeers) {
				reindeer.advanceInTime();
			}
			
			Arrays.sort(reindeers);
			for(int k=0; k<n; k++) {
				if(reindeers[k].dist == reindeers[0].dist) {
					reindeers[k].score++;
				} else {
					break;
				}
			}
		}
		
		int best = Integer.MIN_VALUE;
		for(Reindeer reindeer : reindeers) {
			int trial = reindeer.score;
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

class Reindeer implements Comparable<Reindeer> {
	int v, flySeconds, restSeconds;
	int dist, flownSeconds, restedSeconds;
	boolean resting;
	int score;
	
	void advanceInTime() {
		if(resting) {
			restedSeconds++;
			if(restedSeconds == restSeconds) {
				restedSeconds = 0;
				resting = false;
			}
		} else {
			flownSeconds++;
			dist += v;
			if(flownSeconds == flySeconds) {
				flownSeconds = 0;
				resting = true;
			}
		}
	}
	
	public int compareTo(Reindeer other) {
		return -1*(this.dist - other.dist);
	}
	
}