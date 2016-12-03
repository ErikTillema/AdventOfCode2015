import java.util.*;
import java.io.*;

class Puzzle {
	
	static HashSet<Pos> seen;
	
	public static void main(String[] args) throws Exception {
		seen = new HashSet<Pos>();
		int x = 0, y = 0;
		seen.add(new Pos(x,y));

		String input;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			input = reader.readLine();
		}
		
		char[] c = input.toCharArray();
		for(int i=0; i<c.length; i++) {
			if(c[i] == '>') {
				x++;
			} else if(c[i] == '<') {
				x--;
			} else if(c[i] == '^') {
				y++;
			} else if(c[i] == 'v') {
				y--;
			}
			Pos pos = new Pos(x,y);
			if(!seen.contains(pos)) seen.add(pos);
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