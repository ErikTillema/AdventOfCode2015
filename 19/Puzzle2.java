import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	private HashMap<String,Atom> atoms = new HashMap<String,Atom>();
	private ArrayList<Transformation> transformations = new ArrayList<Transformation>();
	
	private Atom getAtom(String s) {
		if(!atoms.containsKey(s)) {
			atoms.put(s, new Atom(s, atoms.size()));
		}
		return atoms.get(s);
	}
	
	private Molecule getMolecule(String s) {
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		char[] c = s.toCharArray();
		int pos = 0;
		StringBuilder t = new StringBuilder();
		do {
			//add letters
			t.append(c[pos++]);
			while(pos < c.length && 'a' <= c[pos] && c[pos] <= 'z') {
				t.append(c[pos++]);
			}
			atoms.add(getAtom(t.toString()));
			t.setLength(0);
		} while(pos < c.length);
		return new Molecule(atoms);
	}
	
	public void solve() throws Exception {
		Molecule input = null;
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				if(line.contains(" => ")) {
					Scanner scanner = new Scanner(line).useDelimiter(" => ");
					String s1 = scanner.next();
					String s2 = scanner.next();
					Atom to = getAtom(s1); // reverse the transformations!
					Molecule from = getMolecule(s2);
					transformations.add(new Transformation(from, to));
				} else if(line.length() > 0) {
					//input = new ArrayList<Integer>();
					input = getMolecule(line);
					System.out.println("" + input);
					break;
				}
			}
		}
		
		Collections.sort(transformations);
		for(Transformation t : transformations) {
			System.out.println("" + t.from + " " + t.to);
		}

		int result = backtrack(0, input);
		
		System.out.println(result + "");
	}

	private int backtrack(int steps, Molecule cur) {
		if(cur.atoms.size() == 1 && cur.atoms.get(0).name.equals("e")) {
			return steps;
		} else {
			for(Transformation t : transformations) {
				// try t
				Molecule m = cur.applyTransformation(t);
				if(m != null) {
					int result = backtrack(steps+1, m);
					if(result != -1) return result;
				}
			}
			return -1;
		}
	}
		
}

class Atom implements Comparable {	
	public String name;
	public Integer nr;
	
	public Atom(String name, int nr) {
		this.name = name;
		this.nr = new Integer(nr);
	}
	
	public int compareTo(Object o) {
		Atom other = (Atom)o;
		return this.nr.compareTo(other.nr);
	}
	
	public boolean equals(Object o) {
		return compareTo(o) == 0;
	}
	
	public int hashCode() {
		return nr.hashCode();
	}
	
	public String toString() {
		return name + "("+nr+") ";
	}
}

class Molecule implements Comparable {
	public ArrayList<Atom> atoms;
	
	public Molecule(ArrayList<Atom> atoms) {
		this.atoms = atoms;
	}
	
	public Molecule applyTransformation(Transformation t) {
		for(int start = 0; start <= atoms.size() - t.from.atoms.size(); start++) {
			boolean isOk = true;
			for(int i=0; i<t.from.atoms.size(); i++) {
				if(!atoms.get(start+i).equals(t.from.atoms.get(i))) {
					isOk = false;
					break;
				}
			}
			
			if(isOk) {
				// return new Molecule
				ArrayList<Atom> result = new ArrayList<Atom>();
				for(int i=0; i<start; i++)
					result.add(atoms.get(i));
				result.add(t.to);
				for(int i=start+t.from.atoms.size(); i<atoms.size(); i++)
					result.add(atoms.get(i));
				
				return new Molecule(result);
			}
		}
		return null;
	}
	
	public int compareTo(Object o) {
		Molecule other = (Molecule)o;
		if(this.atoms.size() != other.atoms.size()) {
			return other.atoms.size() - this.atoms.size();
		}
		for(int i=0; i < this.atoms.size(); i++) {
			int result = this.atoms.get(i).compareTo( other.atoms.get(i) );
			if(result != 0) return result;
		}
		return 0;
	}
	
	public boolean equals(Object o) {
		return compareTo(o) == 0;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Atom atom : atoms) {
			result.append(atom.toString());
		}
		return result.toString();
	}

}

class Transformation implements Comparable<Transformation> {
	public Molecule from;
	public Atom to;
	
	public Transformation(Molecule from, Atom to) {
		this.from = from;
		this.to = to;
	}
	
	public int compareTo(Transformation other) {
		//Transformation other = (Transformation)o;
		int result = this.from.compareTo(other.from);
		if(result != 0) return result;
		return this.to.compareTo(other.to);
	}
	
}

