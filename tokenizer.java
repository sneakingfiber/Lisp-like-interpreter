
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
public interface tokenizer {
	public ArrayList<String> tokens = new ArrayList<String>();
	
	public static void scanner(BufferedReader file) throws IOException  {

	BufferedReader br = file;
	int val;
	String parentesi;

	StreamTokenizer stream= new StreamTokenizer(br);  
	stream.wordChars('a', 'z');
	 stream.wordChars('0', '9');
	int currentToken;
	while( (currentToken = stream.nextToken()) != StreamTokenizer.TT_EOF) {
		
		switch(currentToken) {
		
		case StreamTokenizer.TT_WORD:
			
			tokens.add(stream.sval);
			
		case StreamTokenizer.TT_NUMBER:
			val = (int)stream.nval;
			if(currentToken==-3) {continue;}
			String num=Integer.toString(val);
			tokens.add(num);
		case StreamTokenizer.TT_EOF:
			break;
		default: parentesi=Character.toString(((char) currentToken)); tokens.add(parentesi);
		} 
	}
}}
	
