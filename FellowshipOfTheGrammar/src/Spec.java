import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import regex.RE;
import scannerGenerator.Token;


public class Spec {

	LinkedList<SpecToken> tokens = new LinkedList<SpecToken>();
	
	public Spec(File spec) {
		try {
		BufferedReader reader = new BufferedReader(new FileReader(spec));
		String line = reader.readLine();
		String specString = "";
		while(line != null)
		{
			specString += "\n" + line;
			line = reader.readLine();
		}
		ScannerGenerator scanner = new ScannerGenerator();
		scanner.scan(specString);
		//System.out.println(scanner.getTokensAsString());
		RE re = new RE();
		for(Iterator<Token> itt = scanner.tokens.values().iterator();itt.hasNext();)
		{
			Token t = itt.next();
			SpecToken st = new SpecToken(t.getName(),t.display());
			tokens.add(st);
			//re.generate(itt.next());
		}
		
		reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SpecToken matches(String s){
		for(Iterator<SpecToken> itt = tokens.iterator();itt.hasNext();){
			SpecToken t = itt.next();
			if(t.matches(s))
			{
				return t;
			}
		}
		return null;
	}

}
