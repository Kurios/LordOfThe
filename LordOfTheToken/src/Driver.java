import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Driver {
	
	public static void main(String[] args)
	{	
		try {
			String inputFile = "SampleInput";
			String specFile = "SampleSpec";
			
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
			System.out.println(scanner.getTokensAsString());
		}
		catch ( Exception e)
		{
			e.printStackTrace();
		}
	}
}
