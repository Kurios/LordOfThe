import java.util.*;


public class ParseTable 
{
	LinkedList<GToken> reference=new LinkedList<GToken>();
	LinkedList<HashMap<GToken,GRule>> table=new LinkedList<HashMap<GToken,GRule>>();
	public ParseTable(LinkedList<GToken> tokens,LinkedList<GRule> rules)
	{
		for (GRule r:rules)
		{
			table.add(new HashMap());
			reference.add(r.self);
			if(!(r.tokenList==null))
			{
				for (GToken fir:r.tokenList.get(0).first)
				{
					//fir is now for all rules in follow.
				}
				
				
			}
		}
		/*
		for (GToken t:tokens)
		{
			
			
		}
		*/
	}
}
