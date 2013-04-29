import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
public class Grammer {

	public LinkedList<GToken> tokens = new LinkedList<GToken>();
	public LinkedList<GRule> rules = new LinkedList<GRule>();
	public Grammer(File grammer, Spec s) {
		// TODO Generate LL(1) Parser
		
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
				tokens.add(new GToken(t));
			}
			for(GRule r : rules)
			{
				r.generateTokens(tokens);
			}
			System.out.println(tokens);
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
			if(t.isTerminal()) t.first.add(t);
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
				
			}
		}
		
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
	public boolean matchesFile(File script) {
		// TODO Auto-generated method stub
		return false;
	}

}
