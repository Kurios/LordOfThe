import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import regex.RE;
import scannerGenerator.Token;


public class Driver {
	
	public static void main(String[] args)
	{	
		try {
			String inputFile = "SampleInput";
			String specFile = "SampleSpec";
			String output = "TableOut";
			if(args.length == 2)
			{
				inputFile = args[0];
				specFile = args[1];
			}

			
			File f = new File(specFile);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			
				line = reader.readLine();
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
				re.generate(itt.next());
			BufferedWriter writer = new BufferedWriter(new FileWriter("table.txt"));
			writer.write(re.toString());
			writer.flush();
			writer.close();
			f = new File(inputFile);
			reader = new BufferedReader(new FileReader(f));
			line = reader.readLine();
			while(line != null)
			{
				String[] strs = line.split(" ");
				for(int i = 0; i<strs.length;i++)
				{
					System.out.print("\n" + re.match(strs[i]) + " " + strs[i]);
				}
				line = reader.readLine();
			}
		}
		catch ( Exception e)
		{
			e.printStackTrace();
		}
	}
}
