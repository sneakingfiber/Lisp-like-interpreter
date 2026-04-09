
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Execution implements tokenizer{
	//private ArrayList<String> keywords = new ArrayList<String>(Arrays.asList("BLOCK","IF","WHILE","SET","PRINT","INPUT"));
	//private tabella b= new tabella();
	private variabili k= new variabili();
	private Errors e;
	
	Execution(variabili a){
		//fornisco la lista delle variabili a questa classe
		k=a;
	};
	
	public void exec(int token) {
		while(token!=tokens.size()) {
			if(tokens.get(token).equals("(")) {evaluate(token); token=tip(token);}
			if(token==tokens.size()) System.exit(0);
			if(tokens.get(token).equals(")")) token++; 
			//if(tokens.get(token).equals(")")&& token==tokens.size()) System.exit(0);
		}
	}
	
	public int evaluate(int token) {
		
		token++;
		int b=0;
		//System.out.println(tokens.get(token));
		String word=tokens.get(token);
		switch(word) {
		case("BLOCK"):  b=this.BLOCK(token);  return b;
		case("IF"): b=this.IF(token); return b;
		case("SET"): b=this.SET(token); return b;
		case("WHILE"): b=this.WHILE(token); return b;
		case("PRINT"): b=this.PRINT(token);return b;
		case("INPUT"): b=this.INPUT(token); return b;
		case("GT"):  b=this.GT(token); return b;
		case("LT"): b=this.LT(token); return b;
		case("OR"): b=this.OR(token); return b;
		case("NOT"): b=this.NOT(token); return b;
		case("AND"): b=this.AND(token); return b;
		case("ADD"): b=this.ADD(token); return b;
		case("SUB"): b=this.SUB(token);return b;
		case("DIV"): b=this.DIV(token); return b;
		case("MUL"): b=this.MUL(token); return b;
		case("EQ"): b=this.EQ(token); return b;
		default: return 0;}
		
	}
	
	private int tip(int token) {
		
		if(token==tokens.size()) return tokens.size();
		while(tokens.get(token).equals(")")==false ) {
			
			if(tokens.get(token).equals("(")) {token=tip(token+1); token++;if(token==tokens.size()) {break;} continue;}
			token++;
		}
		return token;
		
	}
	
	private int BLOCK(int token) {
		
		int a=tip(token); 
	    token++;
		//in a è salvato il token della fine di block
		while(token!=a) {
		
		
		if(tokens.get(token).equals("(")) { evaluate(token); token=tip(token+1); continue;}
		token++;}
		return a;
	}
	
	private int IF(int token) {
		int value=0;
		token++;
		if(evaluate(token)==1) { 
			token++;
			token=tip(token);
			//salto lo statement della condizione
			//puntatore token alla ( della prima istruzione dell'if
			token++;
			value=evaluate(token);
			token=tip(token+1); //punto alla fine della prima istruzione dell'if
			token++;
			token=tip(token+1);
		}
		else {
			token++;
			token=tip(token);
			//salto lo statement della condizione (siamo alla parentesi chiusa)  
			
			token=tip(token+2);
			//salto lo statement della prima istruzione 
			token++;
			 //siamo al token ( della seconda istruzione
			value=evaluate(token);token=tip(token+1);
		}
		return value;
	}
	
	private int SET(int token) {
		token++; 
		if(k.VarVerify(tokens.get(token))) {
			if(tokens.get(token+1).matches("-?\\d+")){k.ChangeValue(tokens.get(token),Integer.parseInt(tokens.get(token+1)));}
			if(k.VarVerify(tokens.get(token+1))) {k.ChangeValue(tokens.get(token), k.GetValue(tokens.get(token+1)));}
			if(tokens.get(token+1).equals("(")) {k.ChangeValue(tokens.get(token),evaluate(token+1));}}
		
		else {
		if(tokens.get(token+1).matches("-?\\d+")){k.NewVar(tokens.get(token),Integer.parseInt(tokens.get(token+1)));}
		if(k.VarVerify(tokens.get(token+1))) {k.NewVar(tokens.get(token), k.GetValue(tokens.get(token+1)));}
		if(tokens.get(token+1).equals("(")) {k.NewVar(tokens.get(token),evaluate(token+1));}}
		return 1;
	}
	
	private int GT(int token) {
		token++;
		int a[]=new int[2];
		for(int i=0;i<2;i++) {
		if(tokens.get(token).matches("-?\\d+")) {a[i]=Integer.parseInt(tokens.get(token)); token++; continue;}
		if(k.VarVerify(tokens.get(token))) {a[i]=k.GetValue(tokens.get(token)); token++; continue;}
		if(tokens.get(token+1).equals("(")) {a[i]=evaluate(token);token=tip(token);continue;}}
		if(a[0]>a[1]) return 1;
		else return 0;
	}
	
	private int LT(int token) {
		token++;
		int a[]=new int[2];
		for(int i=0;i<2;i++) {
		if(tokens.get(token).matches("-?\\d+")) {a[i]=Integer.parseInt(tokens.get(token)); token++; continue;}
		if(k.VarVerify(tokens.get(token))) {a[i]=k.GetValue(tokens.get(token)); token++; continue;}
		if(tokens.get(token+1).equals("(")) {a[i]=evaluate(token);token=tip(token);continue;}}
		if(a[0]<a[1]) return 1;
		else return 0;
	}
	private int AND(int token) {
		int a=token+1,i=tip(token);
		while(a<i) {
			if(tokens.get(a).equals("(")) {
				if(evaluate(a)==1) { a=tip(a);} 
				else return 0;}}
		return 1;
	}
	private int OR(int token) {
		int a=token+1,i=tip(token);
		while(a<i) {
			if(tokens.get(a).equals("(")) {
				if(evaluate(a)==0) { a=tip(a);} 
				else return 1;}}
		return 0;
		}
	private int NOT(int token) {
		token++;
		if(evaluate(token)==1) return 0;
		else return 1;
	}
	private int EQ(int token) {
		token++;
		int a[]=new int[2];
		for(int i=0;i<2;i++) {
		if(tokens.get(token).matches("-?\\d+")) {a[i]=Integer.parseInt(tokens.get(token)); token++; continue;}
		if(k.VarVerify(tokens.get(token))) {a[i]=k.GetValue(tokens.get(token)); token++; continue;}
		if(tokens.get(token+1).equals("(")) {a[i]=evaluate(token);token=tip(token);continue;}}
		if(a[0]==a[1]) {return 1;}
		else return 0;
	}
	private int ADD(int token) {
		int i=tip(token),sum=0;
		
		while(token<i) {
			token++;
			if(tokens.get(token).equals("(")) {sum=sum+evaluate(token); token=tip(token);} 
			if(tokens.get(token).matches("-?\\d+")) {sum=sum+Integer.parseInt(tokens.get(token));}
			if(k.VarVerify(tokens.get(token))){ sum=sum+k.GetValue(tokens.get(token));}}
					
			return sum; }
	
	private int MUL(int token) {
	int i=tip(token),sum=1;
	
	while(token<i) {
		token++;
		if(tokens.get(token).equals("(")) {sum=sum*evaluate(token); token=tip(token);} 
		if(tokens.get(token).matches("-?\\d+")) {sum=sum*Integer.parseInt(tokens.get(token));}
		if(k.VarVerify(tokens.get(token))){ sum=sum*k.GetValue(tokens.get(token));}}
				
		return sum; }
	
	private int DIV(int token) {
		int i=tip(token),sum=0;
		
			token++;
			if(tokens.get(token).equals("(")) {sum=evaluate(token); token=tip(token);} 
			if(tokens.get(token).matches("-?\\d+")) {sum=Integer.parseInt(tokens.get(token));}
			if(k.VarVerify(tokens.get(token))){ sum=k.GetValue(tokens.get(token));}
			token++;
			if(tokens.get(token).equals("(")) {sum=sum/evaluate(token); token=tip(token);} 
			if(tokens.get(token).matches("-?\\d+")) {sum=sum/Integer.getInteger(tokens.get(token));}
			if(k.VarVerify(tokens.get(token))){ sum=sum/k.GetValue(tokens.get(token));}
			
					
			return sum; }
	
	
	private int SUB(int token) {
		int i=tip(token),sum=0;
		
		
			token++;
			if(tokens.get(token).equals("(")) {sum=evaluate(token); token=tip(token);} 
			if(tokens.get(token).matches("-?\\d+")) {sum=Integer.parseInt(tokens.get(token));}
			if(k.VarVerify(tokens.get(token))){ sum=k.GetValue(tokens.get(token));}
			token++;
			if(tokens.get(token).equals("(")) {sum=sum-evaluate(token); token=tip(token);} 
			if(tokens.get(token).matches("-?\\d+")) {sum=sum-Integer.parseInt(tokens.get(token));}
			if(k.VarVerify(tokens.get(token))){ sum=sum-k.GetValue(tokens.get(token));}
					
			return sum; }
	
	private int INPUT(int token) {
		 token++;
		 int in;
		 Scanner input=new Scanner(System.in);
		 if(k.VarVerify(tokens.get(token))) {
			 System.out.println("insert value"); if(input.hasNextInt()==false) { e.PrintErr("ERROR:you must type an integer", token); input.close();  return 0;}
			 else in=input.nextInt(); k.ChangeValue(tokens.get(token),in);}
		 else {System.out.println("insert value"); if(input.hasNextInt()==false) {e.PrintErr("ERROR:you must type an integer", token); input.close(); return 0;}
		 	   else in=input.nextInt(); k.NewVar(tokens.get(token),in);}
		 input.close();
		 return 1;
	}
	private int PRINT(int token) {
		token++;
		int var=0;
		if(k.VarVerify(tokens.get(token))) {var=k.GetValue(tokens.get(token));System.out.println(var);}
		if(tokens.get(token).equals("(")) {System.out.println(evaluate(token)); return 1;}
		if(tokens.get(token).matches("-?\\d+")) {System.out.println(tokens.get(token));}
		return 1;
	}
	private int WHILE(int token) {
		token++;
		int a=token;//punto alla condizione del while e la valuto
		token=tip(token+1);
		token++;
		int i=token; //puntatore alla prima istruzione del while
		while(evaluate(a)==1) {evaluate(i);} //valutazione ed esecuzione del block
		
		return 1;}
	
}
































