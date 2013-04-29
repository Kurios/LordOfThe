import java.util.regex.*;

public class SpecToken {
	
	String token = "";
	String pattern;

	public SpecToken(String token,String pattern)
	{
		this.token = token;
		this.pattern = pattern;
	}
	
	public boolean matches(String s)
	{
		//TODO set it so it returns true if it matches the token. Or something.
		return Pattern.matches(pattern, s);
	}
}
