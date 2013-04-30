package scannerGenerator;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import regex.REPrimitiveFragment;

public class Token extends TString{
	String name;
	LinkedList<Object> components = new LinkedList<Object>();
	Token parent = null;
	String arg;
	LinkedList<TString> regex = new LinkedList<TString>();
	String combined = "";
	public boolean care = false;
	
	public Token(String name, String arg)
	{
		super(arg);
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

	public void compile(Hashtable<String,Token> table) throws Exception
	{
		int start = 0;
		boolean inIdentifier  = false;

		for(int j = 0 ; j < arg.length() - 4; j++)
		{
			if(arg.charAt(j) == ' ')
			{
				if(arg.charAt(j + 1) == 'I' || arg.charAt(j + 1) == 'i')
				{
					if(arg.charAt(j + 2) == 'N' || arg.charAt(j + 2) == 'n'){
						if(arg.charAt(j + 3) == ' ')
						{
							if(!table.containsKey(arg.substring(j + 4)))
							{
								throw new Exception(arg.substring(j + 4) + "Does not Exist");
							}
							parent = table.get(arg.substring(j + 4));
							arg = arg.substring(0, j);
						}
					}
				}
			}
		}				
		start = 0;
		for(int i = 0 ; i < arg.length(); i++)
		{
			if(inIdentifier)
			{
				if(!((arg.charAt(i) >= 'a' && arg.charAt(i) <= 'z') || (arg.charAt(i) >= 'A' && arg.charAt(i) <= 'Z' )))
				{
					if(!table.containsKey(arg.substring(start, i)))
					{
						throw new Exception(arg.substring(start, i) + "Does not Exist");
					}
					regex.add(table.get(arg.substring(start, i)));
					inIdentifier = false;
					start = i;
				}
			}
			else if(arg.charAt(i) == '$'){
				if(start < i)
				{
					regex.add(new TString(arg.substring(start, i)));
				}
				inIdentifier = true;
				start = i;
			}

		}
		if(inIdentifier){
			if(!table.containsKey(arg.substring(start, arg.length())))
			{
				throw new Exception(arg.substring(start, arg.length()) + "Does not Exist!");
			}
			regex.add(table.get(arg.substring(start, arg.length())));
		}else{
			regex.add(new TString(arg.substring(start, arg.length())));
		}
	}

	public String display() throws Exception
	{
		String ret = "";
		if(parent == null)
		{
			for(Iterator<TString> itt = regex.iterator(); itt.hasNext() ; ret = ret + itt.next().display());
		}else{
			ret = new REPrimitiveFragment(regex.getFirst().display().substring(1, regex.getFirst().display().length() - 1)).combineStr(new REPrimitiveFragment(parent.regex.getFirst().display().substring(1, parent.regex.getFirst().display().length() - 1)));
		}
		return ret;
	}
	/***
	 * 
	 * @param s, a string of which the first char is used
	 * @return the token that the string s will lead to, or null, if no token exists
	 */

	public Token resolve(String s){
		//TODO
		return null;
	}


	public String toString()
	{
		try
		{
			if(parent == null)
				return "{" + name + " : " + display() + "}";
			else{
				String parentStr = new REPrimitiveFragment(regex.getFirst().display().substring(1, regex.getFirst().display().length() - 1)).combineStr(new REPrimitiveFragment(parent.regex.getFirst().display().substring(1, parent.regex.getFirst().display().length() - 1)));
				return "{" + name + " : "+ display() +  " child of " + parent +"}";
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			return "";
		}
	}

	public String getName() {
		return name;
	}
}
