import java.io.*;
import java.util.*;

class Puzzle {
	
	static boolean[][] isOn;
	
	public static void main(String[] args) throws Exception {
		isOn = new boolean[1000][1000];
		
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				doCommand(line);
			}
		}
		
		int onCount = 0;
		for(int x=0; x<1000; x++) {
			for(int y=0; y<1000; y++) {
				if(isOn[x][y]) onCount++;
			}
		}
		System.out.println(""+onCount);
	}
	
	static void doCommand(String line) {
		String[] parts = line.split(" ");
		if(parts[0].equals("turn") && parts[1].equals("on")) {
			Pos start = getPos(parts[2]);
			Pos end = getPos(parts[4]);
			for(int x = start.x; x <= end.x; x++) {
				for(int y = start.y; y <= end.y; y++) {
					isOn[x][y] = true;
				}
			}
		} else if(parts[0].equals("turn") && parts[1].equals("off")) {
			Pos start = getPos(parts[2]);
			Pos end = getPos(parts[4]);
			for(int x = start.x; x <= end.x; x++) {
				for(int y = start.y; y <= end.y; y++) {
					isOn[x][y] = false;
				}
			}
		} else {
			// toggle
			Pos start = getPos(parts[1]);
			Pos end = getPos(parts[3]);
			for(int x = start.x; x <= end.x; x++) {
				for(int y = start.y; y <= end.y; y++) {
					isOn[x][y] = !isOn[x][y];
				}
			}
		}
	}
	
	static Pos getPos(String s) {
		String[] parts = s.split(",");
		Pos result = new Pos();
		result.x = Integer.parseInt(parts[0]);
		result.y = Integer.parseInt(parts[1]);
		return result;
	}
	
}

class Pos {
	public int x,y;
}