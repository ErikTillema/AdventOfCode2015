import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws IOException {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	int minCost = Integer.MAX_VALUE;
	
	public void solve() {
		int cost = 0;
		State state = new State(499, 50, 71, 0, 0, 0);
		//State state = new State(250, 10, 14, 0, 0, 0);
		if(state.canSpellShield()) DFS(cost + 113, state.spellShield());
		if(state.canSpellPoison()) DFS(cost + 173, state.spellPoison());
		if(state.canSpellRecharge()) DFS(cost + 229, state.spellRecharge());
		if(state.canSpellMissile()) DFS(cost + 53, state.spellMissile());
		if(state.canSpellDrain()) DFS(cost + 73, state.spellDrain());
		
		System.out.println(minCost+"");
	}
	
	void DFS(int cost, State state) { // this is state right after we've cast a spell
		//prune:
		if(cost >= minCost) return;
	
		state.advance();
		if(state.isWinning) {
			minCost = Math.min(cost, minCost);
			return;
		} else if(state.isLosing) {
			return;
		}
		// it's our turn again.
		// try all spells
		if(state.canSpellShield()) DFS(cost + 113, state.spellShield());
		if(state.canSpellPoison()) DFS(cost + 173, state.spellPoison());
		if(state.canSpellRecharge()) DFS(cost + 229, state.spellRecharge());
		if(state.canSpellMissile()) DFS(cost + 53, state.spellMissile());
		if(state.canSpellDrain()) DFS(cost + 73, state.spellDrain());
	}
	
}

class State {
	public int mana, hpme, hpboss;
	public int shieldTimer, poisonTimer, rechargeTimer;
	public boolean isWinning, isLosing;
	
	public State(int mana, int hpme, int hpboss, int shieldTimer, int poisonTimer, int rechargeTimer) {
		this.mana = mana;
		this.hpme = hpme;
		this.hpboss = hpboss;
		this.shieldTimer = shieldTimer;
		this.poisonTimer = poisonTimer;
		this.rechargeTimer = rechargeTimer;
		this.isWinning = false;
		this.isLosing = false;
	}
	
	public void advance() {
		if(hpboss <= 0) {
			isWinning = true;
			return;
		}
		if(hpme <= 0) {
			isLosing = true;
			return;
		}
		
		//boss turn
		if(poisonTimer > 0) {
			hpboss -= 3;
			poisonTimer--;
		}
		if(rechargeTimer > 0) {
			mana += 101;
			rechargeTimer--;
		}
		int armor = 0;
		if(shieldTimer > 0) {
			armor = 7;
			shieldTimer--;
		}
		if(hpboss <= 0) {
			isWinning = true;
			return;
		}
		hpme -= (10 - armor); // @@@ 8
		if(hpme <= 0) {
			isLosing = true;
			return;
		}
		
		//beginning of my turn
		//decrease hit points
		hpme--;
		if(hpme <= 0) {
			isLosing = true;
			return;
		}		
		
		if(poisonTimer > 0) {
			hpboss -= 3;
			poisonTimer--;
		}
		if(rechargeTimer > 0) {
			mana += 101;
			rechargeTimer--;
		}
		if(shieldTimer > 0) {
			shieldTimer--;
		}
		if(hpboss <= 0) {
			isWinning = true;
			return;
		}
	}
	
	public boolean canSpellShield() {
		return mana >= 113 && shieldTimer == 0;
	}
	public State spellShield() {
		return new State(mana - 113, hpme, hpboss, 6, poisonTimer, rechargeTimer);
	}
	
	public boolean canSpellPoison() {
		return mana >= 173 && poisonTimer == 0;
	}
	public State spellPoison() {
		return new State(mana - 173, hpme, hpboss, shieldTimer, 6, rechargeTimer);
	}
	
	public boolean canSpellRecharge() {
		return mana >= 229 && rechargeTimer == 0;
	}
	public State spellRecharge() {
		return new State(mana - 229, hpme, hpboss, shieldTimer, poisonTimer, 5);
	}
	
	public boolean canSpellMissile() {
		return mana >= 53;
	}
	public State spellMissile() {
		return new State(mana - 53, hpme, hpboss-4, shieldTimer, poisonTimer, rechargeTimer);
	}	
	
	public boolean canSpellDrain() {
		return mana >= 73;
	}
	public State spellDrain() {
		return new State(mana - 73, hpme+2, hpboss-2, shieldTimer, poisonTimer, rechargeTimer);
	}	
}