import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {

	private long a = 252533, m = 33554393;

	public void solve() throws Exception {
		int x = 3075; 
		int y = 2981;
		//x = 2; y = 3;
		int d = x + y;
		int xTop = d - 1;
		int valTop = (d * (d-1)) / 2;
		int valPos = valTop - (y-1);
		System.out.println("position: " + valPos);
		
		long start = 20151125;
		long res = start;
		for(int i=0; i < valPos - 1; i++) {
			res = (res * a) % m;
		}
		System.out.println("" + res);
	}

}