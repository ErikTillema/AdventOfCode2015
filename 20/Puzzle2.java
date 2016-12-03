import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws IOException {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	private int[] score;
	private int n;
	
	public void solve() {
		n = 3600000;
		score = new int[n+1];
	
		for(int i=1; i<=n; i++) {
			for(int j=i; j<=50*i && j<=n; j+=i) {
				score[j] += 11*i;
			}
		}
		
		int result = -1;
		for(int i=1; i<=n; i++) {
			if(score[i] >= 36000000) {
				result = i;
				break;
			}
		}
		
		System.out.println(result+"");
		int sum = result;
		for(int j=1; j<=Math.sqrt(result); j++) {
			if( (result%j) == 0) {
				sum+= j + (result/j);
				System.out.print(j + " ");
			}
		} 
		sum *= 11;
		System.out.println("\n" + sum + "");
	}
	
	
}