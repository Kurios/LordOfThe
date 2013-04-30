import java.io.File;


public class FellowshipOfTheGrammar {

	public static void main(String[] args)//Yes, we start like a pirate! Yar!
	{
		//TODO Turn Strings to files
		File grammer = null;
		File spec = null;
		File script = null;
		
		if(args.length == 0)
		{
			grammer = new File("grammar.txt");
			spec = new File("spec.txt");
			script = new File("script.txt");	
		}
		else if(args.length == 3)
		{
			grammer = new File(args[0]);
			spec = new File(args[1]);
			script = new File(args[2]);
		}
		else if(args.length != 3)
		{
			System.out.println("We need 3 input files in the format <Grammar> <Spec> <Script>");
			return;
		}

		
		Spec s = new Spec(spec);
		Grammer g = new Grammer(grammer,s);
		boolean b = g.matchesFile(script);
		System.out.print("The file matches the grammer? : ");
		System.out.println(b);
	}
}
