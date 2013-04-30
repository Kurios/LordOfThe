import java.util.*;


public class ParseTable 
{
	LinkedList<GToken> reference=new LinkedList<GToken>();
	LinkedList<HashMap<GToken,GRule>> table=new LinkedList<HashMap<GToken,GRule>>();
	public ParseTable(LinkedList<GToken> tokens,LinkedList<GRule> rules,GToken epsilon)
	{
		for (GRule r:rules) //for each rule in grammar
		{
			table.add(new HashMap<GToken,GRule>());
			reference.add(r.self);
			if(!(r.tokenList==null))//if it exists
			{
				for (GToken fir:r.tokenList.get(0).first)//grab the first set of the first token
				{//this is inserted into r.self,fir on the rule section.
					if(!fir.equals(epsilon))table.get(reference.indexOf(r.self)).put(fir, r);//if not add straight up
					if(fir.equals(epsilon))//
					{
						//this is supposed to be for "if has the epsilon thinggy
						//table.get(reference.indexOf(r.self)).put(fir, r);//this is stupid and i feel stupid
						//for (GToken firt:r.tokenList.get(0).first)if(r.self.follow.contains(firt))table.get(reference.indexOf(r.self)).put(firt, r);
						//the above line , since it found an epsilon result in r.self, actually uses
						//the damn follow sets. so for all the things in the first set, it 
						//here has to traverse the fol set of self for a match to tokenlist.get(0)
						for (GToken foll:r.self.follow)table.get(reference.indexOf(r.self)).put(foll, r);
						//and THIS line is when i realized i was being stupid and didn't need an if statment
					}
					//fir is now for all rules in follow.
				}
				/*
				for (GToken fol:r.tokenList.get(0).follow)
				{
					table.get(reference.indexOf(r.self)).put(fol, r);//this is stupid and i feel stupid
					//fir is now for all rules in follow.
				} 
				*/
				
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
	public boolean interpret(LinkedList<GToken> input,GToken start)//takes in a goddamn stack n a fucking input
	{
		//stack starts with start token/symbol and an ending symbol
		LinkedList<GToken> stack=new LinkedList<GToken>();
		GToken end=new GToken("<END_STRING>",true);
		stack.addFirst(end);
		stack.addFirst(start);
		int j=0;
		while(!stack.get(0).equals(end)&&j<40000)//fix
		{
			j++;
			GToken compare=stack.get(0);//is this zero?
			GRule toRun=getCell(compare,input.pop());//this shit all psudo, should
			//be the compare thingy colum goto row.
			//push the shit onto the stack
			//for (GToken t:toRun.tokenList)
			//for(int i=toRun.tokenList.size()-1;i<0;i--)
			int i=toRun.tokenList.size()-1;
			while(i>0)
			{
				
				stack.addFirst(toRun.tokenList.get(i));
				i--;
			}
			if(j==39998) return false;
		}
		return true;
		
	}
}
