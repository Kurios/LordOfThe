import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import scannerGenerator.Token;


public class TransTable {

	public String[] inputs;
	public Token[] states;
	
	public Hashtable<String, Token> table;
	
	/**
	 * Creates a Transition Table from an input alphabet and a list of Token states
	 * @param inputs Each character of the alphabet in String form
	 * @param states Each token of the parser
	 */
	public TransTable(String[] inputs, Token[] states){
		this.inputs = inputs;
		this.states = states;
		setUpTable();
	}
	
	public void setUpTable(){
		for(String i : inputs){
			for(Token s : states){
				Token result = s.resolve(i);
				String k = i + " " + s.getName();
				table.put(k, result);
			}
		}
	}
	
	/**
	 * Prints the table to the console and an output file named
	 */
	public void printTable(){
		String wLine = "";
		wLine += "State:     Input:";
		System.out.print("State:     Input:"); //17 Characters
		for(String i : inputs){
			System.out.print(" |      " + i + "    "); //13 Characters
			wLine += " |      " + i + "    ";
		}
		System.out.println();
		wLine+="\n";
		
		for(Token s : states){
			String line = "   " + s.getName();
			while(line.length() < 17){
				line+= " ";
			}
			for(String i : inputs){
				Token resolve = table.get(i + " " + s.getName());
				if(resolve == null){
					line+= " |    ----   ";
				}
				else{
					String st = " | "; //3 of 13
					st += resolve.getName();
					while(st.length()<13){
						st += " ";
					}
					if(st.length() > 13){
						st = st.substring(0, 13);
					}
					line+= st; //Should always be length of 13
				}
				System.out.println(line);
				wLine+=line + "\n";
				
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("table.txt"));
			writer.write(wLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
