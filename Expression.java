import java.util.*;

/**
 * Full courtesy of @KTH. Minor adjustments made to fulfill task. Recursive
 * handler of algebraic expressions.
 * 
 * @author Christian Ryan
 *
 */

public class Expression {

	Scanner scanner;
	private StringBuilder soFar = new StringBuilder();
	private static int cost = 0;

	public static String Calculate(String expression) {
		if (expression != null) {
			String s = padWithSpace(expression);
			Expression p = new Expression(new Scanner(s));
			double result = p.readExpr();
			return "" + result;
		} else {
			return null;
		}
	}

	public static int giveCost() {
		return cost;
	}
	/**
	 * @param s
	 *            Expression String where operators will be space separated
	 *            1+2*3*(1-2) -> 1 + 2 * 3 * ( 1 - 2 )
	 */
	static String padWithSpace(String s) {
		s = s.replaceAll("\\+", " + ");
		s = s.replaceAll("-", " - ");
		s = s.replaceAll("\\*", " * ");
		s = s.replaceAll("/", " / ");
		s = s.replaceAll("\\)", " ) ");
		s = s.replaceAll("\\(", " ( ");
		s = s.replaceAll("\"", "");
		return s;
	}

	/////////////////////////////////////////////////////////////////////////////
	// Constructor
	Expression(Scanner s) {
		scanner = s;
	}

	/////////////////////////////////////////////////////////////////////////////
	// I/O methods - note must have space between tokens, 3*2 will not be parsed
	private double popValue() {
		String next = scanner.next();
		soFar.append(" " + next);
		return Double.parseDouble(next);
	}

	private double popNegativeValue() {
		scanner.next("-");
		if (peek('(')) {
			double x = readFactor();
			return -1 * x;
		}
		String next = scanner.next();
		soFar.append(" -" + next);
		return -1 * Double.parseDouble(next);
	}

	private String popToken() {
		String next = scanner.next();
		soFar.append(" " + next);
		return next;
	}

	private boolean peek(char s) {
		if (scanner.hasNext()) {
			if (scanner.hasNext("\\" + s)) // if s = '*' then must prefix "\\*"
				return true;
		}
		return false;
	}

	/////////////////////////////////////////////////////////////////////////////
	// Recursive descent
	//
	// Tip trace outputs with:
	// System.out.println("in method X:Parsed so far: " + soFar);

	double readExpr() {
		// expr ::= term | term + expr | term - expr

		double x = readTerm(); // expr ::= term ...
		cost ++;

		if (peek('+')) { // expr ::= term + expr
			popToken();
			x += readExpr();
		}
		if (peek('-')) {
			x += readExpr();
		}
		return x;
	}

	protected double readTerm() {
		// term ::= factor | factor * term | factor / term

		double x = readFactor(); // term ::= factor

		if (peek('*')) { // term ::= factor * term
			popToken();
			return x * readTerm();
		}
		if (peek('/')) {
			popToken();
			return x / readTerm();
		} // TODO term ::= factor / term
		return x;
	}

	protected double readFactor() {
		// factor ::= ( expr ) | value

        if (peek('(')) {
            popToken();  // '('
            double x = readExpr();
            if (peek(')')){
            popToken();  // ')'        // TODO check if ')'
            }
            return x;
        } else {
            return readVal();
        }

	}

	protected double readVal() {
		if (peek('-')) {
			return popNegativeValue();
		} else {
			return popValue(); // TODO negative numbers?
		}
	}

}
