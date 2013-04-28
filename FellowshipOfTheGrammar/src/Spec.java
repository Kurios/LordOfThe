import java.io.File;
import java.util.LinkedList;


public class Spec {

	LinkedList<SpecToken> tokens = new LinkedList<SpecToken>();
	
	public Spec(File spec) {
		// TODO Convert file into spec tokens using project 1. Or something.
	}
	
	public SpecToken matches(String s){
		//TODO Return the token that successfully matches the above.
		return tokens.getFirst();
	}

}
