import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		Solver solver = new Solver();
		solver.solve();
	}
	
}

class Solver {
	
	private HashMap<String,Integer> elementNumber = new HashMap<String,Integer>();
	private HashMap<String,String> transformations = new HashMap<String,String>();
	
	private int getElementNumber(String s) {
		if(!elementNumber.containsKey(s)) {
			elementNumber.put(s, elementNumber.size());
		}
		return elementNumber.get(s);
	}
	
	public void solve() throws Exception {
		//ArrayList<Integer> input = null;
		String input = null;
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				if(line.contains(" => ")) {
					Scanner scanner = new Scanner(line).useDelimiter(" => ");
					String s1 = scanner.next();
					String s2 = scanner.next();
					// Transformation transformation = new Transformation();
					// transformation.to = getElementNumber(s1); // reverse the transformations!
					// transformation.from = getElementNumber(s2);
					// System.out.println("" + transformation.from + " " + transformation.to);
					// transformations.add(transformation);
					transformations.put(s2,s1);
				} else if(line.length() > 0) {
					//input = new ArrayList<Integer>();
					input = line;
					break;
				}
			}
		}
		
		nodes = new HashMap<String,Node>();
		int result = AStar(input, "e");
		
		System.out.println(result + "");
	}
	
	private HashMap<String,Node> nodes;
	
	private Node getNode(String s) {
		if(!nodes.containsKey(s)) {
			nodes.put(s, new Node(s));
		}
		return nodes.get(s);
	}
	
	private HashSet<String> getNeighbourStrings(String cur) {
		HashSet<String> result = new HashSet<String>();
		char[] c = cur.toCharArray();
		int n = c.length;
		for(int i=0; i<n; i++) {
			for(int l=1; l<=10 && i+l-1<n; l++) {
				String s = cur.substring(i, i+l);
				String from = s;
				//for(Transformation transformation : transformations) {
				if(transformations.containsKey(from)) {
					String to = transformations.get(from);
					//if(s.equals(transformation.from)) {
						//System.out.println(cur + " " + s + " " + transformation.to + " " + i + " " + l);
						//String newResult = cur.substring(0, i) + transformation.to + cur.substring(i+l);
						String newResult = cur.substring(0, i) + to + cur.substring(i+l);
						if(!result.contains(newResult)) {
							result.add(newResult);
						//} else {
							//	System.out.println("already in results");
						}
					// }
				}
			}
		}
		return result;
	}
	
	public int AStar(String start, String end) throws Exception {
		LinkedList<Node> queue = new LinkedList<Node>();
		Node startNode = getNode(start);
		startNode.dist = 0;
		queue.add(startNode);
		int maxDist = 0;
		while(!queue.isEmpty()) {
			Node current = queue.removeFirst();
			current.color = 2;
			if(current.value.equals(end)) {
				return current.dist;
			}
			if(current.dist > maxDist) {
				System.out.println("" + maxDist + " " + nodes.size());
				maxDist++;
			}
			
			for(String s : getNeighbourStrings(current.value)) {
				Node neighbour = getNode(s);
				neighbour.dist = Math.min(neighbour.dist, current.dist + 1);
				if(neighbour.color == 0) {
					neighbour.color = 1;
					queue.add(neighbour);
				}
			}
		}
		
		throw new Exception("end not found");
	}
	
}

class Transformation {
	public int from, to;
}

class Node implements Comparable {
	public String value;
	public int color; // 0 = to do, 1 = in queue, 2 = done
	public int dist;
	public int minDistLeft;
	
	public Node(String s) {
		value = s;
		color = 0;
		dist = Integer.MAX_VALUE;
	}
	
	public int compareTo(Object o) {
		Node other = (Node)o;
		return this.value.compareTo(other.value);
	}
	// public boolean equals(Object o) {
		// Node other = (Node)o;
		// return this.value.equals(other.value);
	// }
	// public int hashCode() {
		// return value.hashCode();
	// }
}