import java.util.ArrayList;
import java.util.LinkedList;


public class GToken extends SpecToken {

  public GToken(String token, boolean terminal) {
    super(token, token);
    this.terminal = !terminal;
  }

  public GToken(SpecToken t){
    this(t.token, false);
    this.terminal = true;
    this.pattern = t.pattern;
  }

  boolean terminal;
  //First and follow sets

  LinkedList<GToken> first = new LinkedList<GToken>();
  LinkedList<GToken> follow = new LinkedList<GToken>();
  
  boolean firstSetMade = false;

  //Rules beginning with this token (i.e. A ::= stuff* stores in token A)
  ArrayList<GRule> rules = new ArrayList<GRule>();

public boolean followSetMade = false;

  public boolean isTerminal(){
    return terminal;
  }

  public boolean equals(GToken token){
	  return token.token.equalsIgnoreCase(this.token);
  }
  
  public String toString()
  {
	  String ret = "GToken: Name:" + this.token + " Terminal? "+ terminal;// + " Rules: " + this.rules + " First: "; 
			 // ret+= this.first + " Follow: " ;
			 //		  ret+= this.follow + " ) ";
	  return ret;
  }


}