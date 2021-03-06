import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.*;

public class GRule{
	
	String name = "";
	String rule = "";
	ArrayList<GToken> tokenList = new ArrayList<GToken>();
	GToken self = null;

	public GRule(String line) {
		//System.out.println(line);
		String[] lines = line.split(" ::= ");
		name = lines[0];
		rule = lines[1].replace(">", "> ");
		//System.out.println("name = "+name + " rule = " + rule);
	}
	
	public GRule(String name, String line) {
		//System.out.println(line);
		this.name = name;
		this.rule = line;
		//System.out.println("name = "+name + " rule = " + rule);
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
		//GToken us = null;
		//First we find ourselves.
		for(GToken t : tokens)
		{
			if(t.token.equalsIgnoreCase(name)){
				self = t;
				break;
			}
		}
		if(self == null){
			self = new GToken(name,true);
			tokens.add(self);
		}
		//Then we add us to us.
		self.rules.add(this);
		
		//Then we add all its rules.
		//System.out.println(rule);
		
		for(String s : rule.split(" ")){
			if(s.length() > 0){
				System.out.print(" \""+s+"\"");
				boolean term = true;
				if(s.charAt(0)=='<') term = false;
				GToken tar = null;
				//System.out.println(tokens);
				for(GToken t : tokens)
				{
					
					if(t.token.equalsIgnoreCase(s)){
						tar = t;
						break;
					}
					if(term && (t.matches(s) && !t.token.equalsIgnoreCase("<epsilon>")))
					{
						tar = t;
						break;
					}
				}
				if(tar == null){
					tar = new GToken(s,true);
					//System.out.println("created: " + s);
					tokens.add(tar);
				}
				tokenList.add(tar);
			}
		}
		//System.out.println("");	
		//System.out.println("created: " + name + " : " + tokenList);
	}
	
	public String toString()
	{
		return "GRule : " + tokenList;
	}

}
