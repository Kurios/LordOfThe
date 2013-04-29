import java.util.ArrayList;
import java.util.LinkedList;


public class GToken extends SpecToken {

  public GToken(String token, boolean terminal) {
    super(token, token);
    this.terminal = terminal;
  }

  public GToken(SpecToken t){
    this(t.token, true);
    this.pattern = t.pattern;
  }

  boolean terminal;
  //First and follow sets

  LinkedList<GToken> first = new LinkedList<GToken>();
  LinkedList<GToken> follow = new LinkedList<GToken>();

  //Rules beginning with this token (i.e. A ::= stuff* stores in token A)
  ArrayList<GRule> rules = new ArrayList<GRule>();

  public boolean isTerminal(){
    return terminal;
  }

  
  public String toString()
  {
	  String ret = "GToken: Name:" + this.token + " Rules: " + this.rules;
	  return ret;
  }


}