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
	
}
