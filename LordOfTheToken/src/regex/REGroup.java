package regex;

public class REGroup {
	
	RENode head = new RENode();

	public void generate(String s,String goalState) {
		try {
			head.addPath(s, goalState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RENode getHead() {
		// TODO Auto-generated method stub
		return head;
	}

	public REGroup closeGroup(char charAt, String string) {
		RENode tail = head;
		//while(!tail.states.isEmpty())
		//	for(int i = 0; i < )
		switch (charAt)
		{
			case '?': //this.addPath(regex.substring(2), string); break;
			case '*':  //this.addPath(regex.substring(2), string);
			case '+': //head.add(head); break;
		}
		return null;
	}

}
