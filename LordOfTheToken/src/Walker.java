import java.util.Stack;

import scannerGenerator.*;
public class Walker 
{
	Stack<Token> jim=new Stack<Token>();
	//TEXAS RANNNNGER
	void tokenize(String file) throws Exception
	{
		String[] files = file.split("\n");
		//you should push the beginning thingy on here
		// called $empty
		Token t=new Token("$empty","");
		jim.push(t);
		for(int i = 0; i < files.length; i++)
		{
			
			String[] words = files[i].split(" ");
			for(int j=0;j<words.length;j++)
			{
				Token state=jim.pop();
				//if(token==NULL)
				//if it starts with a _ something sucks if it starts with $ it's a valid end state
				//Token ret=table.getState(token,words[j])
				//array of possibles or matching
				//take empty state
				//getstate empty, firstchar
				//whilenot at end, traverse, then validate that end thing is end
				//pulled a
				
				
				//int firstSpace = files[i].indexOf(' ');
				//t = new Token(files[i].substring(0, firstSpace),files[i].substring(firstSpace + 1));
				//tokens.put(files[i].substring(0, firstSpace),t);
			}
			
		
		}
		
	}
	
}
