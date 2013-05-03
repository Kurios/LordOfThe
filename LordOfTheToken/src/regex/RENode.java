package regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
/**
 * The above examples show digits 0 through 9 out of ASCII printable characters define  a character class DIGIT and thus,
 *  anywhere DIGIT appears it could mean any of the 0 through 9.  We have formed a sub-class NON-ZERO out of this on the 2nd line
 *   - NON-ZERO removes 0 out of DIGIT and means it corresponds to 1 through 9 anywhere it appears. Similarly, we have CHAR class
 *    defined out of ASCII printable characters first and then a sub-class UPPER defined out of thisÅc 
 * The following characters must be escaped if they were to appear as a part of alphabet (i.e., preceded by a backslash): 
 * space, \, *, +, |, [, ], (,), ., ' and ".  If they are not escaped, they will be treated as regex operators as follows: 
 * a space without a preceding \ in a regex is ignored,  | is the regex union, ( ) are for grouping, * + are for repetitions, 
 * [ ] and . are for character classes (see RegEx section below).
 *
 *
 *
 * RegEx  Specification
 * To build a regex, you must use characters and character classes as the basic building blocks, | for union of subexpressions, 
 * round parentheses to surround a subexpression  for giving it a higher precedence, * and + for zero-to-infinite and one-to-infinite 
 * repetitions (e.g., (ab)+ is ab, abab, etc.). Empty strings in larger regexÅfs are implicit. For example, 'a(|b)' 
 * represents the language {a, ab}, and '(|b)' means Ågempty string or bÅh.
 *
 * When matching strings, * +   are ÅggreedyÅh up to the end of the line (detected by \n) . For example, searching for (ab)* in 
 * a line abababa followed by line abab matches this regex twice: once for the 3 ab in that first line and once for the 2 ab in 
 * the second line (as a separate, second match).
 *
 * The precedences of regex operators are : ( )  highest followed by * and + followed by concatenation followed by | (union) - 
 * the full grammar for regex is given in the Appendix. 
 *
 * First the scanner generator will read the regex specification of a token and make sure it is legal - 
 * it will then use the precedences  and generate primitive NFAs and put those together as per the precedences and
 *  make one giant NFA out of it. It will then convert the giant NFA to a DFA. 
 *
 * @author Kurios
 *
 */
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
						i++;
						states.add(node);
						if(regex.length() > i)
						{
							switch(regex.charAt(i))
							{
							case '?': this.addPath(regex.substring(i+1), goalStateName); break;
							case '*': this.addPath(regex.substring(i+1), goalStateName);
							case '+': 
							node.add(frag, node); 
							node.addPath(regex.substring(i+1), goalStateName);
							break;
							default: node.addPath(regex.substring(i), goalStateName);
							}
						}else{
							node.addPath(regex.substring(i), goalStateName);
						}
						break;
			case '.' : 	frag = new REPrimitiveFragment("^"); 
						node = new RENode();
						transitions.add(frag);
						states.add(node);
						if(regex.length() > 1)
						{
							switch(regex.charAt(1))
							{
							case '?': this.addPath(regex.substring(2), goalStateName); break;
							case '*': this.addPath(regex.substring(2), goalStateName);
							case '+': node.add(frag, node); break;
							default: node.addPath(regex.substring(1), goalStateName);
							}
						}else{
							node.addPath(regex.substring(2), goalStateName);
						}
						break;
			case '\\' :
				regex = regex.substring(1);
			default  :	
				//for(i = 1; i < regex.length() && regex.charAt(i) != ']' ; i++);
				frag = new REPrimitiveFragment("\\"+regex.substring(0, 1));
				node = new RENode();
				transitions.add(frag);
				states.add(node);
				if(regex.length() > 1)
				{
					switch(regex.charAt(1))
					{
					case '?': this.addPath(regex.substring(2), goalStateName); break;
					case '*': this.addPath(regex.substring(2), goalStateName);
					case '+':   node.add(frag, node); 
								node.addPath(regex.substring(1), goalStateName);break;
					default: node.addPath(regex.substring(1), goalStateName);
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

	public void merge(RENode node) {
		// TODO Auto-generated method stub
		for(int i = 0;i < node.transitions.size(); i++)
		{
			transitions.add(node.transitions.get(i));
			states.add(node.states.get(i));
		}
	}
	
	public String toString()
	{
		HashSet<RENode> set = new HashSet<RENode>();
		set.add(this);
		String ret =  "[ " + " " + returnValue  +  " ";
		for(int i = 0;i<transitions.size();i++)
		{
			ret += " " + transitions.get(i) + " ->" + states.get(i).toString(set);
		}
		return ret + " ]"; 
	}
	
	public String toString(HashSet<RENode> table)
	{
		String ret =  "[ " + " " + returnValue  +  " ";
		if(!table.contains(this))
		{
			table.add(this);
			for(int i = 0;i<transitions.size();i++)
			{
				ret += " " + transitions.get(i) + " ->" + states.get(i).toString(table);
			}	
		}
		return ret + " ]\n";
	}

	public String match(String string) {
		if(string.isEmpty())
		{
			return returnValue;
		}
		String ret = "";
		for(int i = 0;i<transitions.size();i++)
		{
			if(transitions.get(i).matches(string.charAt(0)))
			{
				ret += states.get(i).match(string.substring(1));
			}
		}
		return ret;
	}

	public void addtoLast(RENode node, REPrimitiveFragment frag) {
		if(transitions.size() > 0)
		{
			for(int i = 0;i<transitions.size();i++)
			{
				states.get(i).addtoLast(node,frag);
			}
		}else{
			this.add(frag, node);
		}
	}
}