import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import scannerGenerator.*;

public class ScannerGenerator {
	
		public Hashtable<String,Token> tokens = new Hashtable<String,Token>();
	
		void scan(String file) throws Exception
		{
			boolean care = false;
			String[] files = file.split("\n");
			for(int i = 1; i < files.length; i++)
			{
				if(files[i].length() > 2)
				{
					Token t = null;
					int firstSpace = files[i].indexOf(' ');
					t = new Token(files[i].substring(0, firstSpace),files[i].substring(firstSpace + 1));
					t.care = care;
					tokens.put(files[i].substring(0, firstSpace),t);
					/*
					String[] words = files[i].split(" ");
					for(int j = 0; j < words.length; j++)
					{
						if(j == 0)
						{
							t = new Token(words[0], );
							tokens.put(words[0],t);
						}
						else if(words[j].length() > 0)
						{
							if(words[j].charAt(0) == '$' )
							{
								if(tokens.containsKey(words[j])){
									t.addToken(tokens.get(words[j]));
								}else{
									throw new Exception("Token "+words[j]+" did not exist : line " + i +" word "+ j + " " + files[i]);
								}
							}else if(words[j].equalsIgnoreCase("IN")){
								if(words.length > j + 1)
								{
									if(tokens.containsKey(words[j+1])){
										t.setParent(tokens.get(words[j+1]));
									}else{
										throw new Exception("Token "+words[j+1]+" did not exist : line " + i +" word "+ j+1 + " " + files[i]);
									}
								j++;
								}else{
									throw new Exception("Incorrect use of the keyword IN. Needs target token. : line " + i +" word "+ j + " " + files[i]);
							}
							}else{
								t.addToken(words[j]);
							}
							
						}
					}
					 */
				}else{
					care = true;
				}
			}
			for(Iterator<Token> itt = tokens.values().iterator(); itt.hasNext() ; itt.next().compile(tokens) );
			LinkedList<String> toKill = new LinkedList<String>();
			for(Iterator<String> itt = tokens.keySet().iterator(); itt.hasNext() ;  )
			{
				String s = itt.next();
				if(!tokens.get(s).care)
					{
						toKill.add(s);
					}
			}
			for(String s : toKill)
			{
				tokens.remove(s);
				System.out.println("not considering " + s);
			}

		}

		public String getTokensAsString() {
			String ret = "";
			for(Iterator<Token> t = tokens.values().iterator();t.hasNext();ret += t.next().toString() + "\n");
			return ret;
		}
		

}
