import java.util.*;
import java.io.*;

class Puzzle {
	
	static int targetVolume = 150;
	static int n;
	static ArrayList<Integer> volume, count;
	static long[][] combinations; // to make [totalVolume][highestContainerIndexUsed]
	
	public static void main(String[] args) throws Exception {
		ArrayList<Integer> volumeTemp = new ArrayList<Integer>();
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				volumeTemp.add( Integer.parseInt(line) );
			}
		}
		Collections.sort(volumeTemp);
		//for(int i=0; i<volumeTemp.size(); i++) {
			//System.out.print(volumeTemp.get(i) + ", ");
		//}
		
		n = 0;
		volume = new ArrayList<Integer>();
		count = new ArrayList<Integer>();
		for(int i=0; i<volumeTemp.size(); i++) {
			if(i > 0 && volumeTemp.get(i-1).equals( volumeTemp.get(i))) {
				count.set(n-1, count.get(n-1) + 1);
			} else {
				volume.add(volumeTemp.get(i));
				count.add(1);
				n++;
			}
		}
		
		combinations = new long[targetVolume+1][n+1];
		combinations[0][0] = 1;
		for(int i=1; i<=n; i++) {
			for(int v=0; v<=targetVolume; v++) {
				for(int containers = 0; containers <= count.get(i-1); containers++ ) {
					int volumeWithoutTheseContainers = v - containers * volume.get(i-1);
					if(volumeWithoutTheseContainers < 0) break;
					long combinationsTheseContainers = choose( count.get(i-1), containers );
					long combinationsWithoutTheseContainers = combinations[volumeWithoutTheseContainers][i-1];
					combinations[v][i] += combinationsTheseContainers * combinationsWithoutTheseContainers;
				}
			}
		}
		
		long result = combinations[targetVolume][n];
		System.out.println(""+result);
	}
	
	static long choose(int n, int m) {
		if(m > n/2) return choose(n, n-m);
		long t = 1;
		for(int i=1; i<=m; i++) {
			//mogelijk tussentijdse overflow!:
			//t = (t * (n-m+i))/i; 
			//geen tussentijdse overflow maar trager:
			t = ( t / gcd(t,i) ) * ( (n-m+i) / (i/gcd(t,i)) );
		}
		return t;
	}

	/* werkt voor alle gehele a en b zolang niet allebei nul */
	static long gcd(long a, long b) {
		a = Math.abs(a); b = Math.abs(b);
		if(a < b) return gcd(b,a);
		if(b == 0) return a;
		return gcd(a%b, b);
	}

}