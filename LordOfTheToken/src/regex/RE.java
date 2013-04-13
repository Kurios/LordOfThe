package regex;

import scannerGenerator.Token;

/***
 * This is a general regular expressions handler.
 * 
 * Custom created so I'm not "stealing" codes.
 * 
 * It takes in a token, and adds it to its database
 * 
 * @author Kurios
 *
 */
public class RE {
	
	RENode startState = new RENode();
	
	public RE()
	{
		startState.name = "empty";
	}
	
	public boolean addToken(Token t)
	{
		try {
			return startState.addPath(t.display(),t.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean matchec(char c)
	{
		return false;
	}
	
	public void generate(Token t) throws Exception
	{
		REGroup g = new REGroup();
		REGroup working = g;
		int j = 0;
		int i = 0;
		for(j = 0; j < t.display().length() ; j++)
		{
			switch(t.display().charAt(i))
			{
			case '(' : 	i = j + 1; break;
			case ')' : 	working.generate(t.display().substring(i, j), "");
						working = working.closeGroup(t.display().charAt(j+1),"&");
						break;
			case '|' : 	working.generate(t.display().substring(i, j), "");
						working = working.closeGroup(t.display().charAt(j+1),"|"); 
						break;
			}
		}
		working.generate(t.display().substring(i, j),t.getName());
		startState.merge(g.getHead());
	}
	
	public String toString()
	{
		return startState.toString();
	}

	public String match(String string) {
		return startState.match(string);
	}
}
