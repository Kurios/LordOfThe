package scannerGenerator;

import java.util.LinkedList;

public class Token {
	String name;
	LinkedList<Object> components = new LinkedList<Object>();
	Token parent = null;
	
	public Token(String name)
	{
		this.name = name;
	}

	public void addToken(Token token) {
		this.components.add(token);
	}

	public void addToken(String string) {
		this.components.add(string);
	}
	
	public void setParent(Token token)
	{
		parent = token;
	}
	public String toString()
	{
		if(parent == null)
			return "{" + name + " Contains" + components.toString() + "}";
		else{
			return "{" + name + " Contains" + components.toString() + "child of " + parent.name +"}";
		}
	}
}
