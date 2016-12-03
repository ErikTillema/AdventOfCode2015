import java.util.*;
import java.io.*;

class Puzzle {
	
	static HashSet<Pos> seen;
	
	public static void main(String[] args) throws Exception {
		seen = new HashSet<Pos>();
		int n = 2;
		int[] x = new int[n];
		int[] y = new int[n];
		seen.add(new Pos(0,0));

		String input;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			input = reader.readLine();
		}
		
		char[] c = input.toCharArray();
		int santaIndex = 0;
		for(int i=0; i<c.length; i++) {
			if(c[i] == '>') {
				x[santaIndex]++;
			} else if(c[i] == '<') {
				x[santaIndex]--;
			} else if(c[i] == '^') {
				y[santaIndex]++;
			} else if(c[i] == 'v') {
				y[santaIndex]--;
			}
			Pos pos = new Pos(x[santaIndex] , y[santaIndex]);
			if(!seen.contains(pos)) seen.add(pos);
			santaIndex = (santaIndex+1)%n;
		}
		int result = seen.size();
		System.out.println(""+result);
	}
	
}

class Pos { //implements Comparable<Pos> 
	public int x, y;
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		Pos other = (Pos)o;
		return this.x == other.x && this.y == other.y;
	}
	
	public int hashCode() {
		int result = 31;
		result = 37*result + (new Integer(x)).hashCode();
		result = 37*result + (new Integer(y)).hashCode();
		return result;
	}
	
}