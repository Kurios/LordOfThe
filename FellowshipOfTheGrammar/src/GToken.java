import java.util.LinkedList;
import java.util.regex.Pattern;


public class GToken extends SpecToken{
	
	
	public GToken(String token, String pattern) {
		super(token, pattern);
		// TODO Auto-generated constructor stub
	}
	
	public boolean matches(String s)
	{
		//TODO set it so it returns true if it matches the token. Or something.
		return false;
	}
	//TODO Define a token
	//First and follow sets
	LinkedList<GToken> first = new LinkedList<GToken>();
	LinkedList<GToken> follow = new LinkedList<GToken>();
	
	
	
}
