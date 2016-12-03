import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws IOException {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	public void solve() {
		solveAll();
		System.out.println(minCost + "");
	}
	
	Weapon[] weapons = {
		new Weapon(8, 4),
		new Weapon(10, 5),
		new Weapon(25, 6),
		new Weapon(40, 7),
		new Weapon(74, 8)
	};
	Armor[] armors = {
		new Armor(13, 1),
		new Armor(31, 2),
		new Armor(53, 3),
		new Armor(75, 4),
		new Armor(102, 5)
	};
	Ring[] rings = {
		new Ring(25, 1, 0),
		new Ring(50, 2, 0),
		new Ring(100, 3, 0),
		new Ring(20, 0, 1),
		new Ring(40, 0, 2),
		new Ring(80, 0, 3)
	};
	
	int minCost = Integer.MAX_VALUE;
	boolean[] ringTaken = new boolean[rings.length];
	
	void solveAll() {
		for(int w=0; w < weapons.length; w++) {
			for(int a=0; a < armors.length; a++) {
				for(int ringsToTake = 0; ringsToTake <= 2; ringsToTake++) {
					int cost = weapons[w].cost + armors[a].cost;
					solveRings(cost, weapons[w].damage, armors[a].armor, ringsToTake, 0);
				}
			}
		}
	}
	
	void solveRings(int cost, int damage, int armor, int ringsToTake, int done) {
		if(done == ringsToTake) {
			if(isOk(damage, armor)) {
				if(cost < minCost) {
					System.out.println("try: " + cost + " " + damage + " " + armor + " " + ringsToTake);
				}
				//System.out.println(" IS OK");
				minCost = Math.min(minCost, cost);
			}
		} else {
			//choose ring
			for(int i=0; i< rings.length; i++) {
				if(!ringTaken[i]) {
					ringTaken[i] = true;
					Ring r = rings[i];
					solveRings(cost + r.cost, damage + r.damage, armor + r.armor, ringsToTake, done+1);
					ringTaken[i] = false;
				}
			}
		}
	}
	
	boolean isOk(int damage, int armor) {
		int turnCountMe = (int)Math.ceil( 100.0 / Math.max(1, 8-armor) );
		int turnCountBoss = (int)Math.ceil( 109.0 / Math.max(1, damage-2) );
		return turnCountMe >= turnCountBoss;
	}
	
}

class Weapon {
	int cost, damage;
	public Weapon(int cost, int damage) {
		this.cost = cost;
		this.damage = damage;
	}
}

class Armor {
	int cost, armor;
	public Armor(int cost, int armor) {
		this.cost = cost;
		this.armor = armor;
	}
}

class Ring {
	int cost, damage, armor;
	public Ring(int cost, int damage, int armor) {
		this.cost = cost;
		this.damage = damage;
		this.armor = armor;
	}
}