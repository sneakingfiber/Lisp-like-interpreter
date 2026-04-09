
public class Errors {
	Errors(){}
	public void PrintErr(String message, int token)  {
		System.out.println(message);
		System.out.print(token);
		System.exit(0);
	}
}
