import java.util.*;
import java.io.*;

class Puzzle {

	public static void main(String[] args) throws Exception {
		String input;
		try( BufferedReader reader = new BufferedReader(new FileReader("in.txt")) ) {
			input = reader.readLine();
		}
		pos = 0;
		c = input.toCharArray();
		JsonThing root = parse();
		int result = root.getSum();
		System.out.println(""+result);
	}

	static int pos;
	static char[] c;
	
	static JsonThing parse() {
		if(c[pos] == '[') {
			return parseList();
		} else if(c[pos] == '\"') {
			return parseString();
		} else if(c[pos] == '{') {
			return parseObject();
		} else {
			return parseInt();
		}
	}
	
	static JsonInt parseInt() {
		JsonInt result = new JsonInt();
		result.value = parseJavaInt();
		return result;
	}

	static JsonString parseString() {
		pos++;
		JsonString result = new JsonString();
		result.value = parseJavaString();
		pos++;
		return result;		
	}
	
	static JsonList parseList() {
		pos++; // [
		JsonList result = new JsonList();
		while(c[pos] != ']') {
			JsonThing item = parse();
			result.items.add(item);
			if(c[pos] == ',') pos++;
		}
		pos++; // ]
		return result;		
	}
	
	static JsonObject parseObject() {
		pos++; // {
		JsonObject result = new JsonObject();
		while(c[pos] != '}') {
			pos++; // "
			String key = parseJavaString();
			pos++; // "
			pos++; // :
			JsonThing value = parse();
			result.values.put(key, value);
			if(c[pos] == ',') pos++;
		}
		pos++; // }
		return result;		
	}
	
	static int parseJavaInt() {
		String result = "";
		while(c[pos] == '-' || (c[pos] >= '0' && c[pos] <= '9')) {
			result += c[pos];
			pos++;
		}
		return Integer.parseInt(result);
	}
	
	static String parseJavaString() {
		String result = "";
		while(c[pos] != '\"') {
			result += c[pos];
			pos++;
		}
		return result;
	}
	
}

abstract class JsonThing {
	public abstract int getSum();
}

class JsonInt extends JsonThing {
	public int value;
	public int getSum() {
		return value;
	}
}

class JsonString extends JsonThing {
	public String value;
	public int getSum() {
		return 0;
	}
}

class JsonList extends JsonThing {
	public LinkedList<JsonThing> items;
	
	public JsonList() {
		items = new LinkedList<JsonThing>();
	}
	
	public int getSum() {
		int result = 0;
		for(JsonThing item : items) {
			result += item.getSum();
		}
		return result;
	}
}

class JsonObject extends JsonThing {
	public HashMap<String, JsonThing> values;
	
	public JsonObject() {
		values = new HashMap<String, JsonThing>();
	}
	
	public boolean isRed() {
		for(JsonThing item : values.values()) {
			if(item instanceof JsonString) {
				JsonString string = (JsonString)item;
				if(string.value.equals("red")) return true;
			}
		}
		return false;
	}
	
	public int getSum() {
		if(isRed()) return 0;
		int result = 0;
		for(JsonThing item : values.values()) {
			result += item.getSum();
		}
		return result;
	}
}