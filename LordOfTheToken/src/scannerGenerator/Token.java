package scannerGenerator;

import java.util.LinkedList;

public class Token {
	String name;
	LinkedList<Object> components = new LinkedList<Object>();
	Token parent = null;
	String arg;
	
	public Token(String name, String arg)
	{
		this.name = name;
		this.arg = arg;
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
	
	public void compile()
	{
		//TODO
	}
	
	
	
	public Token resolve(String input){
		//TODO
		return null;
	}
	
	public String toString()
	{
		if(parent == null)
			return "{" + name + " Contains" + components.toString() + "}";
		else{
			return "{" + name + " Contains" + components.toString() + "child of " + parent.name +"}";
		}
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
