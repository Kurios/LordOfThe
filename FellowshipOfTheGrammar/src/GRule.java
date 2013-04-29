import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.*;

public class GRule{
	
	String name = "";
	String rule = "";
	ArrayList<GToken> tokenList;

	public GRule(String line) {
		//System.out.println(line);
		String[] lines = line.split(" ::= ");
		name = lines[0];
		rule = lines[1];
		System.out.println("name = "+name + " rule = " + rule);
	}
	
	public GRule(String name, String line) {
		//System.out.println(line);
		this.name = name;
		this.rule = line;
		System.out.println("name = "+name + " rule = " + rule);
	}

	public boolean canNormalize() {
		if(rule.matches(".*\\|.*"))
		{
			//System.out.println("match found in "+rule);
			return true;
		}
		return false;
	}

	public GRule normalize() {
		if(canNormalize())
		{
			String[] s = rule.split(" *\\| *", 2);
			rule = s[1];
			return new GRule(name, s[0]);
		}
		return null;
	}

	public void generateTokens(LinkedList<GToken> tokens) {
		
		RE
		String[] strTokens = rule.split(" ");
		
	}

}
