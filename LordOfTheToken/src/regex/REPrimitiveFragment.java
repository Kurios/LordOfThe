package regex;

import java.util.Arrays;

/***
 * Fragment defined as of the REGEX document defining character classes. This also verry verry closely resembles transition requirments.
 * @author Kurios
 *
 */
public class REPrimitiveFragment {
	
	boolean chars[] = new boolean[126 - 32];
	
	/***
	 * Takes in a definition for a character class, as per regex standard
	 * ie, the string, "A-Z" will generate a primitiveFragement that represents all letters in the range A-Z
	 * 
	 * so basically, acceptable input is chars. \-^ need to be escaped with a \
	 * the special char - creates a range between two ASCII chars, common examples are 0-9, A-Z, and a-z
	 * if it starts with a ^, will will create an inverse matching character, the equivalent of not the following. If a ^ exists unexcaped elsewhere, it throws an error.
	 * @param def
	 * @throws Exception
	 */
	public REPrimitiveFragment(String def) throws Exception{
		Arrays.fill(chars, false);
		char[] charArray = def.toCharArray();
		char last = 0;
		boolean assignment = true;
		for(int i = 0; i < charArray.length ; i++)
		{
			if(charArray[i] == '\\')
			{
				
			}else if(charArray[i] == '-'){
				i++;
				while(last != charArray[i])
				{
					chars[last - 32] = assignment;
					last++;
				}
				chars[last - 32] = assignment;
			}else if(charArray[i] == '^'){ // We invert it
				if(i != 0){
					throw new Exception("Failed to compile, inappropiatly placed '^' in regex");
				}
				Arrays.fill(chars, true);
				assignment = false;
			}else{
				last = charArray[i];
				chars[charArray[i]-32] = assignment;
			}
		}
		
	}

	public String combineStr(REPrimitiveFragment frag)
	{
		String ret = "[";
		for(int i = 0; i < chars.length; i++)
		{
			if(chars[i] && frag.chars[i])
			{
				ret += String.valueOf((char)(i + 32));
			}
		}
		return ret + "]";	
	}
	
	public boolean matches(char c)
	{
		return chars[c - 32];
	}
}
