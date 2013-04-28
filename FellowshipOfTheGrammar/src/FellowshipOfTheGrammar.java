import java.io.File;


public class FellowshipOfTheGrammar {

	public static void main(String[] arrrrrrrrggs)//Yes, we start like a pirate! Yar!
	{
		//TODO Turn Strings to files
			File grammer = null;
			File script = null;
			File spec = null;
		
		Spec s = new Spec(spec);
		Grammer g = new Grammer(grammer,s);
		boolean b = g.matchesFile(script);
		System.out.print("The file matches the grammer? : ");
		System.out.println(b);
	}
}
