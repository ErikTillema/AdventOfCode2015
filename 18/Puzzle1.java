import java.util.*;
import java.io.*;

class Puzzle {
	
	static int h = 100, w = 100;
	static int n = 100;
	static boolean[][][] isOn;
	
	public static void main(String[] args) throws Exception {
		isOn = new boolean[w][h][2];
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			String line;
			for(int y=0; (line = reader.readLine()) != null; y++) {
				char[] c = line.toCharArray();
				for(int x=0; x<w; x++) {
					isOn[x][y][0] = (c[x] == '#');
				}
			}
		}
		isOn[0][0][0] = isOn[0][h-1][0] = isOn[w-1][0][0] = isOn[w-1][h-1][0] = true;
		
		int index = 0;
		for(int i=0; i<n; i++) {
			int newIndex = 1-index;
			
			for(int x=0; x<w; x++) {
				for(int y=0; y<h; y++) {
					int count = countNeighboursOn(x, y, index);
					if(isOn[x][y][index]) {
						isOn[x][y][newIndex] = count == 2 || count == 3;
					} else {
						isOn[x][y][newIndex] = count == 3;
					}
				}
			}
			isOn[0][0][newIndex] = isOn[0][h-1][newIndex] = isOn[w-1][0][newIndex] = isOn[w-1][h-1][newIndex] = true;
			
			index = newIndex;
		}
		
		int result = 0;
		for(int x=0; x<w; x++) {
			for(int y=0; y<h; y++) {
				result+= isOn[x][y][index] ? 1 : 0;
			}
		}
		
		System.out.println(""+result);
	}
	
	static int[] dx = { -1, -1, -1, 0,  0,  1, 1, 1 };
	static int[] dy = { -1,  0,  1, -1, 1, -1, 0, 1 };
	
	static int countNeighboursOn(int x, int y, int index) {
		int result = 0;
		for(int i=0; i<8; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if(nx >= 0 && nx < w && ny >= 0 && ny < h) {
				result += isOn[nx][ny][index] ? 1 : 0;
			}
		}
		return result;
	}

}