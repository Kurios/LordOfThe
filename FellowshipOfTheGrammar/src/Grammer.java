import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
public class Grammer {
	public GToken start;
	
	public LinkedList<GToken> tokens = new LinkedList<GToken>();
	public LinkedList<GToken> terminals = new LinkedList<GToken>();
	public LinkedList<GRule> rules = new LinkedList<GRule>();

	public GToken epTok = new GToken("<epsilon>",false);
	public Grammer(File grammer, Spec s) {
		// TODO Generate LL(1) Parser
		tokens.add(new GToken("<epsilon>",false));
		try {
			BufferedReader reader = new BufferedReader(new FileReader(grammer));
			String line = reader.readLine();
		
			while(line != null)
			{
				//For each line
				GRule rule = new GRule(line);
				while(rule.canNormalize())
				{
					rules.add(rule.normalize());
				}
				rules.add(rule);
				//This in theory gives us a set of rules that are normalized, ie: our | is implied by creating a new rule.
				
				//We generate a first set. Then we generate a follow set. Then we generate a demon magic table. Fuck our lives.
				line = reader.readLine();
			}
			for(SpecToken t : s.tokens)
			{
				GToken z = new GToken(t);
				tokens.add(z);//Fuck
				terminals.add(z);
			}
			for(GRule r : rules)
			{
				r.generateTokens(tokens);
			}
			GRule tstart = rules.get(0);
			for(GToken t : tokens)
			{
				if(t.token.equalsIgnoreCase(tstart.name))
				{
					start = t;
					
					start.follow.add(new GToken("<END_STRING>", false));
					break;
				}
			}
			System.out.println(start);
			System.out.println(tokens);
			System.out.println(start);
			for(GToken t: tokens){
				createFirstSet(t);
			}
			for(int ind = 0; ind < 100; ind++){
				for(GRule r: rules){
					createFollowSet(r);
				}
			}
			System.out.println(start.rules);
			System.out.println("\nHALP ME\n" + tokens.get(25));
			System.out.println("" + tokens.get(25).follow);
			
			//System.out.println(tokens);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeFirstAndFollowSet(){
		ArrayList<GToken> nullable = new ArrayList<GToken>();
		for(GToken t : tokens){
			if(t.isTerminal()){
				t.first.clear();
				t.follow.clear();
				t.first.add(t);
			}
			else {
				t.first.clear();
				t.follow.clear();
			}
		}
		boolean firstChanged = true, followChanged = true, nullChanged = true; 
		while(firstChanged || followChanged || nullChanged){
			firstChanged = false;
			followChanged = false;
			nullChanged = false;
			for(GRule rule: rules){
				GToken head = rule.self;
				GRule r = rule;
				
				int headIndex = indexOfToken(head);
				int k = r.tokenList.size() - 1;
				//if k=1 then nullable = nullable union x
				if(k == 0) {
					nullable.add(head);
					nullChanged = true;
					
				}
				
				//if {Y(1),...,Y(k)} subset of nullable then nullable = nullable union x
				boolean subsetOfNullable = true;
				for(GToken checkNull : r.tokenList){
					if(!nullable.contains(checkNull)) subsetOfNullable = false;
				}
				if(subsetOfNullable) {
					nullable.add(head);
					nullChanged = true;
					
				}
				
				
				//for i = 1 to k   except we subtracted 1 from both i and k
				for(int i = 0; i <= k; i++){
					//for j = i+1 to k
					for(int j = i+1; j <= k; j++){
						//if i=1 or {Y(1),...,Y(i-1)} subset of nullable then
						boolean subsetOfNull = true;
						for(int ind = 0; ind < i; ind++){
							if(!nullable.contains(r.tokenList.get(ind))) subsetOfNull = false;
						}
						//first(X) = first(X) union first(Y(i))
						if(i == 0 || subsetOfNull){
							for(GToken t : r.tokenList.get(i).first){
								if(!tokens.get(headIndex).first.contains(t)){
									tokens.get(headIndex).first.add(t);
									firstChanged = true;
								}
							}
						}
						
						//if i=k or {Y(i+1),...Y(k)} subset of nullable then
						//        follow(Y(i)) = follow(Y(i)) union follow(X)
						subsetOfNull = true;
						for(int ind = i+1; ind <= k; ind++){
							if(!nullable.contains(r.tokenList.get(ind))) subsetOfNull = false;
						}
						if(i == k || subsetOfNull){
							int yIndex = indexOfToken(r.tokenList.get(i));
							for(GToken t : tokens.get(headIndex).follow){
								if(!tokens.get(yIndex).follow.contains(t)){
									tokens.get(yIndex).follow.add(t);
									followChanged = true;
								}
							}
						}
						
						//if i+1=j or {Y(i+1),...,Y(j-1)} subset of nullable then
				        //		 follow(Y(i)) = follow(Y(i)) union first(Y(j))
						subsetOfNull = true;
						for(int ind = i+1; ind <= j-1; ind++){
							if(!nullable.contains(r.tokenList.get(ind))) subsetOfNull = false;
						}
						if(i+1 == j || subsetOfNull){
							int iIndex = indexOfToken(r.tokenList.get(i));
							int jIndex = indexOfToken(r.tokenList.get(j));
							for(GToken t : tokens.get(jIndex).first){
								if(!tokens.get(iIndex).follow.contains(t)){
									tokens.get(iIndex).follow.add(t);
									followChanged = true;
								}
							}
						}
						
					}
				}
				
				
				
				
			}
		}
		
	}
	
	public void createFirstSet(GToken t){
			if(t.firstSetMade) return;
			for(GRule r : t.rules){
				if(r.tokenList.get(0).token.equalsIgnoreCase("<epsilon>")){
					t.first.add(r.tokenList.get(0));
					continue;
				}
				for(GToken rt: r.tokenList){
					if(rt.equals(t)) continue;
					else if(rt.isTerminal()){
						t.first.add(rt);
						break;
					}
					else if(!rt.firstSetMade){
						createFirstSet(tokens.get(tokens.indexOf(rt)));
						rt.first = tokens.get(tokens.indexOf(rt)).first;
						rt.firstSetMade = true;
						for(GToken f : rt.first){
							if(t.first.contains(f)) continue;
							else t.first.add(f);
						}
						if(!rt.first.contains(new GToken("<epsilon>", false))){
							break;
						}
					}
					else {
						for(GToken f : rt.first){
							if(t.first.contains(f)) continue;
							else t.first.add(f);
						}
						if(!rt.first.contains(new GToken("<epsilon>", false))){
							break;
						}
					}
				}
			}
			t.firstSetMade = true;
	}
	
	public void createFollowSet(GRule r){
		for(int tInd = 1; tInd <r.tokenList.size(); tInd++){
			if(r.tokenList.get(tInd).isTerminal()) continue;
			else{
				if(! (tInd +1 < r.tokenList.size())) continue;
				if(r.tokenList.get(tInd+1).isTerminal()){
					if(!r.tokenList.get(tInd).follow.contains(r.tokenList.get(tInd+1)))
						r.tokenList.get(tInd).follow.add(r.tokenList.get(tInd+1));
					break;
				}
				else if( r.tokenList.get(tInd+1).first.contains(epTok) ){
					
					for(GToken tf : r.tokenList.get(tInd+1).first){
						if(r.tokenList.get(tInd).follow.contains(tf)) continue;
						else if(tf.equals(epTok )) continue;
						else r.tokenList.get(tInd).follow.addLast(tf);
					}
					
					for(GToken tf : r.self.follow){
						if(r.tokenList.get(tInd).follow.contains(tf)) continue;
						else r.tokenList.get(tInd).follow.addLast(tf);
					}
					
				}
				else if(! r.tokenList.get(tInd+1).first.contains(epTok)){
					for(GToken tf : r.tokenList.get(tInd+1).first){
						if(r.tokenList.get(tInd).follow.contains(tf)) continue;
						else if(tf.equals(epTok)) continue;
						else r.tokenList.get(tInd).follow.addLast(tf);
					}
				}
				
			}
			
		}
	}
	
	public int indexOfToken(GToken token){
		for(int i = 0; i< tokens.size(); i++){
			if(token.equals(tokens.get(i))) return i;
		}
		return -1;
	}
	
	//Sources:
	//    http://www.jflap.org/tutorial/grammar/LL/
	
	//ImWritingAThesisStatement
	//WhatTheFuckIsGoingOn?

	/*
	  enum Symbols {
        // the symbols:
        // Terminal symbols:
        TS_L_PARENS,    // (
        TS_R_PARENS,    // )
        TS_A,           // a
        TS_PLUS,        // +
        TS_EOS,         // $, in this case corresponds to '\0'
        TS_INVALID,     // invalid token
 
        // Non-terminal symbols:
        NTS_S,          // S
        NTS_F           // F
};
 
//Converts a valid token to the corresponding terminal symbol
enum Symbols lexer(char c)
{
        switch(c)
        {
                case '(':  return TS_L_PARENS;
                case ')':  return TS_R_PARENS;
                case 'a':  return TS_A;
                case '+':  return TS_PLUS;
                case '\0': return TS_EOS; // end of stack: the $ terminal symbol
                default:   return TS_INVALID;
        }
}
 
int main(int argc, char **argv)
{
        using namespace std;
 
        if (argc < 2)
        {
                cout << "usage:\n\tll '(a+a)'" << endl;
                return 0;
        }
 
        // LL parser table, maps < non-terminal, terminal> pair to action       
        map< enum Symbols, map<enum Symbols, int> > table; 
        stack<enum Symbols>     ss;     // symbol stack
        char *p;        // input buffer
 
        // initialize the symbols stack
        ss.push(TS_EOS);        // terminal, $
        ss.push(NTS_S);         // non-terminal, S
 
        // initialize the symbol stream cursor
        p = &argv[1][0];
 
        // setup the parsing table
        table[NTS_S][TS_L_PARENS] = 2;
        table[NTS_S][TS_A] = 1;
        table[NTS_F][TS_A] = 3;
 
        while(ss.size() > 0)
        {
                if(lexer(*p) == ss.top())
                {
                        cout << "Matched symbols: " << lexer(*p) << endl;
                        p++;
                        ss.pop();
                }
                else
                {
                        cout << "Rule " << table[ss.top()][lexer(*p)] << endl;
                        switch(table[ss.top()][lexer(*p)])
                        {
                                case 1: // 1. S �¨ F
                                        ss.pop();
                                        ss.push(NTS_F); // F
                                        break;
 
                                case 2: // 2. S �¨ (S + F)
                                        ss.pop();
                                        ss.push(TS_R_PARENS);   // )
                                        ss.push(NTS_F);         // F
                                        ss.push(TS_PLUS);       // +
                                        ss.push(NTS_S);         // S
                                        ss.push(TS_L_PARENS);   // (
                                        break;
 
                                case 3: // 3. F �¨ a
                                        ss.pop();
                                        ss.push(TS_A);  // a
                                        break;
 
                                default:
                                        cout << "parsing table defaulted" << endl;
                                        return 0;
                                        break;
                        }
                }
        }
 
        cout << "finished parsing" << endl;
 
        return 0;
}
	 */
	public GToken generateTerminal(String s)
	{
		return null;
	}
	
	public boolean matchesFile(File script) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(script));
			String line = reader.readLine();
			LinkedList<GToken> queue = new LinkedList<GToken>();
			
			while(line != null)
			{
				//We attempt to make a queue of terminals
				for(String s : line.split(" +"))
				{
					if(line.length() > 0)
					{
						boolean added = false;
						for(GToken t : terminals){
							if(t.matches(s))
							{
								queue.add(t);
								added = true;
								break;
							}
							else // we slide along back, looking for a greedy match.
							{
								
								int br = s.length();
								while(br > 0)
								{
									boolean match = false;
									for(GToken tr : terminals){
										if(tr.matches(s.substring(0, br)))
										{
											queue.add(tr);
											match = true;
											
											break;
										}
									}
									if(match)
									{
										System.out.println("MATCHED " + queue.peekLast() + " WITH " + s);
										s = s.substring(br);
										br = s.length();
										if(br > 0) match = false;
									}else{
										br--;
									}
								}
							}
						}
						if(!added)System.out.println("NO MATCH FOR: " + s);
						else System.out.println("MATCHED " + queue.peekLast() + " WITH " + s);
					}
				}
				line = reader.readLine();
				
			}
			System.out.println(queue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
