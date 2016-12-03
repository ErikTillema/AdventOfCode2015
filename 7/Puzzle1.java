import java.io.*;
import java.util.*;

class Puzzle {
	
	public static int solved = 0;
	static HashMap<String,Wire> wires;
	static List<Expression> expressions;
	
	public static void main(String[] args) throws Exception {
		wires = new HashMap<String,Wire>();
		expressions = new LinkedList<Expression>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
			for(String line; (line = reader.readLine()) != null; ) {
				addExpression(line);
			}
		}
		
		if(false) {
			for(int i=0; i<10; i++) {
				for(Expression expression : expressions) {
					expression.evaluate();
				}
				System.out.println("Solved "+solved);
			}
			for(Wire wire : wires.values()) {
				System.out.println("Wire "+wire.name+" : "+wire.value);
			}
		} else {
			for(Wire wire : wires.values()) {
				System.out.println("Wire "+wire.name+" : "+wire.value);
			}
			
			Wire wireA = wires.get("a");
			for(int i=0; !wireA.hasValue && i<10000; i++) {
				int expressionsSolved = 0;
				int canBeSolved = 0;
				for(Expression expression : expressions) {
					if(expression.isSolved) {
						expressionsSolved++;
					} else if(expression.canSolve()) {
						canBeSolved++;
					}
				}
				System.out.println("Expressions Solved "+expressionsSolved);
				System.out.println("Expressions that can be solved "+canBeSolved);
				if(canBeSolved == 0) {
					for(Wire wire : wires.values()) {
						if(wire.hasValue) {
							System.out.println("Wire "+wire.name+" : "+wire.value);
						}
					}
					break;
				}
				
				for(Expression expression : expressions) {
					expression.evaluate();
				}
				
				System.out.println("Wires Solved "+solved);
			}
			
			int result = wireA.value;
			System.out.println(""+result);
		}
	}
	
	static void addExpression(String line) {
		System.out.println(line);
		Scanner scanner = new Scanner(line);
		if(line.contains("AND")) {
			Wire input1 = getWire(scanner.next());
			scanner.next();
			Wire input2 = getWire(scanner.next());
			scanner.next();
			Wire output = getWire(scanner.next());
			Expression expression = new AndExpression(input1, input2, output);
			expressions.add(expression);
		} else if(line.contains("OR")) {
			Wire input1 = getWire(scanner.next());
			scanner.next();
			Wire input2 = getWire(scanner.next());
			scanner.next();
			Wire output = getWire(scanner.next());
			Expression expression = new OrExpression(input1, input2, output);
			expressions.add(expression);
		} else if(line.contains("NOT")) {
			scanner.next();
			Wire input = getWire(scanner.next());
			scanner.next();
			Wire output = getWire(scanner.next());
			Expression expression = new NotExpression(input, output);
			expressions.add(expression);
		} else if(line.contains("LSHIFT")) {
			Wire input = getWire(scanner.next());
			scanner.next();
			int amount = scanner.nextInt();
			scanner.next();
			Wire output = getWire(scanner.next());
			Expression expression = new LeftShiftExpression(input, output, amount);
			expressions.add(expression);
		} else if(line.contains("RSHIFT")) {
			Wire input = getWire(scanner.next());
			scanner.next();
			int amount = scanner.nextInt();
			scanner.next();
			Wire output = getWire(scanner.next());
			Expression expression = new RightShiftExpression(input, output, amount);
			expressions.add(expression);
		} else {
			try {
				int amount = scanner.nextInt();
				scanner.next();
				Wire output = getWire(scanner.next());
				Expression expression = new SetExpression(amount, output);
				expressions.add(expression);
			} catch(Exception e) {
				Wire input = getWire(scanner.next());
				scanner.next();
				Wire output = getWire(scanner.next());
				Expression expression = new SetExpression2(input, output);
				expressions.add(expression);
			}
		}
	}
	
	static Wire getWire(String name) {
		try {
			int value = Integer.parseInt(name);
			if(!wires.containsKey(name)) {
				Wire wire = new Wire(name);
				wire.setValue(value);
				wires.put(name, wire);
			}
			return wires.get(name);
		} catch(Exception e) {
			if(!wires.containsKey(name)) wires.put(name, new Wire(name));
			return wires.get(name);
		}
	}

}

class Wire {
	public String name;
	public int value;
	public boolean hasValue;
	public Wire(String name) {
		this.name = name;
		hasValue = false;
	}
	public void setValue(int value) throws Exception {
		if(hasValue && this.value != value) throw new Exception("huh " + name + " " + value);
		if(value < 0) throw new Exception("fout " + name + " " + value);
		if(!hasValue) Puzzle.solved++;
		this.value = value;
		hasValue = true;
	}
}

abstract class Expression {
	public boolean isSolved;
	public abstract boolean canSolve();
	public abstract void evaluate() throws Exception;
}

class SetExpression extends Expression {
	public Wire output;
	public int amount;
	public void evaluate() throws Exception {
		output.setValue(amount);
		isSolved = true;
	}
	public boolean canSolve() {
		return true;
	}
	public SetExpression(int amount, Wire output) {
		this.amount = amount;
		this.output = output;
	}
}

class SetExpression2 extends Expression {
	public Wire input;
	public Wire output;
	public void evaluate() throws Exception {
		if(input.hasValue) {
			output.setValue(input.value);
			isSolved = true;
		}
	}
	public boolean canSolve() {
		return input.hasValue;
	}
	public SetExpression2(Wire input, Wire output) {
		this.input = input;
		this.output = output;
	}
}

class RightShiftExpression extends Expression {
	public Wire input, output;
	public int amount;
	public void evaluate() throws Exception {
		if(input.hasValue) {
			output.setValue(input.value >> amount);
			isSolved = true;
		}
	}
	public boolean canSolve() {
		return input.hasValue;
	}
	public RightShiftExpression(Wire input, Wire output, int amount) {
		this.input = input;
		this.output = output;
		this.amount = amount;
	}
}

class LeftShiftExpression extends Expression {
	public Wire input, output;
	public int amount;
	public void evaluate() throws Exception {
		if(input.hasValue) {
			output.setValue( (input.value << amount) & ((1<<16)-1) );
			isSolved = true;
		}
	}
	public boolean canSolve() {
		return input.hasValue;
	}
	public LeftShiftExpression(Wire input, Wire output, int amount) {
		this.input = input;
		this.output = output;
		this.amount = amount;
	}
}

class NotExpression extends Expression {
	public Wire input, output;
	public void evaluate() throws Exception {
		if(input.hasValue) {
			output.setValue( (~input.value) & ((1<<16)-1) );
			isSolved = true;
		}
	}
	public boolean canSolve() {
		return input.hasValue;
	}
	public NotExpression(Wire input, Wire output) {
		this.input = input;
		this.output = output;
	}
}

class AndExpression extends Expression {
	public Wire input1, input2, output;
	public void evaluate() throws Exception {
		if(input1.hasValue && input2.hasValue) {
			output.setValue(input1.value & input2.value);
			isSolved = true;
		}
	}
	public boolean canSolve() {
		return input1.hasValue && input2.hasValue;
	}
	public AndExpression(Wire input1, Wire input2, Wire output) {
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
	}
}

class OrExpression extends Expression {
	public Wire input1, input2, output;
	public void evaluate() throws Exception {
		if(input1.hasValue && input2.hasValue) {
			output.setValue(input1.value | input2.value);
			isSolved = true;
		}
	}
	public boolean canSolve() {
		return input1.hasValue && input2.hasValue;
	}
	public OrExpression(Wire input1, Wire input2, Wire output) {
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
	}
}