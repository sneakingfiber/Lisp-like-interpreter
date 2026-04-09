import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		variabili k = new variabili();
		//tokenizer tokens=new Parser(k);
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(args[0]));
		tokenizer.scanner(br);
		Parser p=new Parser(k);
		p.Sintax_check(0);
		Execution t= new Execution(k);
		t.exec(0);
	}
	
}
