import java.util.*;
import java.io.*;

class Puzzle {
	
	static LinkedList<Sue> sues = new LinkedList<Sue>();
	static HashMap<String,Integer> searchValues = new HashMap<String,Integer>();
	
	public static void main(String[] args) throws Exception {
		
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			for(String line; (line = reader.readLine()) != null; ) {
				sues.add(getSue(line));
			}
		}
		
		searchValues.put("children", 3);
		searchValues.put("cats", 7);
		searchValues.put("samoyeds", 2);
		searchValues.put("pomeranians", 3);
		searchValues.put("akitas", 0);
		searchValues.put("vizslas", 0);
		searchValues.put("goldfish", 5);
		searchValues.put("trees", 3);
		searchValues.put("cars", 2);
		searchValues.put("perfumes", 1);

		for(Sue sue : sues) {
			if(isMatch(sue, searchValues)) {
				System.out.println(""+sue.number);	
			}
		}
	}
	
	static Sue getSue(String s) {
		String[] parts = s.split(" |:|,");
		Sue result = new Sue(Integer.parseInt(parts[1]));
		for(int i=3; i<parts.length; i+=4) {
			String key = parts[i];
			int value = Integer.parseInt(parts[i+2]);
			result.propertyValues.put(key, value);
		}
		return result;
	}
	
	static boolean isMatch(Sue sue, HashMap<String,Integer> searchValues) {
		for(String key : searchValues.keySet()) {
			if(sue.propertyValues.containsKey(key)) {
				if(key.equals("cats") || key.equals("trees")) {
					if(sue.propertyValues.get(key) <= searchValues.get(key)) return false;
				} else if(key.equals("pomeranians") || key.equals("goldfish")) {
					if(sue.propertyValues.get(key) >= searchValues.get(key)) return false;
				} else {
					if(sue.propertyValues.get(key) != searchValues.get(key)) return false;
				}
			}
		}
		return true;
	}
	
	
}

class Sue {
	public int number;
	public HashMap<String,Integer> propertyValues;
	public Sue(int number) {
		this.number = number;
		propertyValues = new HashMap<String,Integer>();
	}
}