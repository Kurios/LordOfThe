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
}