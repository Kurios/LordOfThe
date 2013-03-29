package regex;

import java.util.ArrayList;
import java.util.LinkedList;

public class RENode {
	
	String name;
	ArrayList<REPrimitiveFragment> transitions = new ArrayList<REPrimitiveFragment>();
	ArrayList<RENode> states = new ArrayList<RENode>();
	String returnValue = "";
	
	public void add(REPrimitiveFragment frag, RENode node)
	{
		transitions.add(frag);
		states.add(node);
	}
	
	public RENode[] transition(String s)
	{
		LinkedList<RENode> ret = new LinkedList<RENode>();
		for(int i = 0; i < states.size(); i++)
		{
			if(transitions.get(i).matches(s.charAt(0)))
			{
				ret.add(states.get(i));
			}
		}
		return (RENode[]) ret.toArray();
	}
	public String wrappedAddPath(String regex,RENode loopback) throws Exception
	{
		
		return regex;
	}
	public boolean addPath(String regex,String goalStateName) throws Exception
	{
		REPrimitiveFragment frag;
		RENode node;
		int i;
		if(regex.length() > 0){
			switch(regex.charAt(0))
			{
			case '[' : 	
						for(i = 1; i < regex.length() && regex.charAt(i) != ']' ; i++);
						frag = new REPrimitiveFragment(regex.substring(1, i));
						node = new RENode();
						transitions.add(frag);
						states.add(node);
						if(regex.length() < i + 1)
						{
							switch(regex.charAt(i+1))
							{
							case '?': this.addPath(regex.substring(i+2), goalStateName); break;
							case '*': this.addPath(regex.substring(i+2), goalStateName);
							case '+': node.add(frag, node);
							default: node.addPath(regex.substring(i+2), goalStateName);
							}
						}else{
							node.addPath(regex.substring(i+1), goalStateName);
						}
						break;
						
			case '(' : 
			case '.' : 	frag = new REPrimitiveFragment("^"); 
						node = new RENode();
						transitions.add(frag);
						states.add(node);
						if(regex.length() < 1)
						{
							switch(regex.charAt(1))
							{
							case '?': this.addPath(regex.substring(2), goalStateName); break;
							case '*': this.addPath(regex.substring(2), goalStateName);
							case '+': node.add(frag, node);
							default: node.addPath(regex.substring(2), goalStateName);
							}
						}else{
							node.addPath(regex.substring(2), goalStateName);
						}
						break;
			default  :	
				//for(i = 1; i < regex.length() && regex.charAt(i) != ']' ; i++);
				frag = new REPrimitiveFragment(regex.substring(0, 0));
				node = new RENode();
				transitions.add(frag);
				states.add(node);
				if(regex.length() < 1)
				{
					switch(regex.charAt(1))
					{
					case '?': this.addPath(regex.substring(2), goalStateName); break;
					case '*': this.addPath(regex.substring(2), goalStateName);
					case '+': node.add(frag, node);
					default: node.addPath(regex.substring(2), goalStateName);
					}
				}else{
					node.addPath(regex.substring(1), goalStateName);
				}
				break;
			}
		}else{
			returnValue += goalStateName;
		}
		return true;
	}
}