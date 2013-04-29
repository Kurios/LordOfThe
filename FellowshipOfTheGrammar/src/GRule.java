import java.util.regex.*;

public class GRule {
	
	String name = "";
	String rule = "";

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
		if(rule.matches(".*|.*"))
			return false; //TODO FIX THIS. RETURNS TRUE or TRUE
		return false;
	}

	public GRule normalize() {
		if(canNormalize())
		{
			String[] s = rule.split(" *| *", 2);
			rule = s[1];
			return new GRule(name, s[0]);
		}
		return null;
	}

}
