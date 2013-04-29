import java.util.*;


public class ParseTable 
{
	LinkedList<GToken> reference=new LinkedList<GToken>();
	LinkedList<HashMap<GToken,GRule>> table=new LinkedList<HashMap<GToken,GRule>>();
	public ParseTable(LinkedList<GToken> tokens,LinkedList<GRule> rules)
	{
		for (GRule r:rules)
		{
			table.add(new HashMap<GToken,GRule>());
			reference.add(r.self);
			if(!(r.tokenList==null))
			{
				for (GToken fir:r.tokenList.get(0).first)
				{
					table.get(reference.indexOf(r.self)).put(fir, r);//this is stupid and i feel stupid
					//fir is now for all rules in follow.
				}
				for (GToken fol:r.tokenList.get(0).follow)
				{
					table.get(reference.indexOf(r.self)).put(fol, r);//this is stupid and i feel stupid
					//fir is now for all rules in follow.
				} 
				
			}
		}
		//whelp, now if you want a resulution at (a,b) you do
		//table.get(reference.indexOf(a)).get(b); //which returns the rule you do
		/*
		for (GToken t:tokens)
		{
			
			
		}
		*/
	}
	public GRule getCell(GToken a,GToken b)
	{
		return table.get(reference.indexOf(a)).get(b); //which returns the rule you do
		
	}
	public String toString()
	{
		String ret="";
		ret+="hi";
		return " ";
		
	}
}
