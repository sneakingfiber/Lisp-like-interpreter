import java.util.ArrayList;
import java.util.Arrays;

public class Parser implements tokenizer{
	
	private variabili k= new variabili();
	private Execution v=new Execution(k); //serve esclusivamente per il metodo evaluate della classe execution
	public Errors e=new Errors();
	private ArrayList<String> logic = new ArrayList<String>(Arrays.asList("LT","GT","AND","OR","NOT","TRUE"));
	
	Parser(variabili a){
		//fornisco la lista delle variabili a questa classe
		k=a;
	}

	public int Sintax_check (int j) {
		j=0;
		if(tokens.size()==0)e.PrintErr("ERROR:Empty Program", 0);
		
		for(int i=j;i<tokens.size();i++)
			if(tokens.get(j).equals("(")) {
			j=RecursiveParse(i);
			i=j;
			if(tokens.get(i).equals(")")==false && i!=tokens.size()) { e.PrintErr("ERROR: at token", 0); return 0;}
			}
			//System.out.println("Code compiled successfully");
			return 1;}
	
	private int RecursiveParse(int j) {
		int b=0;
		for(int a=j;a<(tokens.size()-1);a++) {
			
			if(tokens.get(a).equals("(")==false) {continue;}
			if(tokens.get(a).equals("(")){
				
				b=keyword(a);
				if(b==0) {e.PrintErr("ERROR OCCURED", b); return 0;}
				else a=b;}
			
			}
			return b;}
	
	
	private int keyword(int token) {
		token++;
		int b;
		
		String word=tokens.get(token);
		switch(word) {
		case("BLOCK"):  b=this.BLOCK(token); if(b==0) {return 0;} else { return b;}
		case("IF"): b=this.IF(token); if(b==0) {return 0;} else {return b;}
		case("SET"): b=this.SET(token); if(b==0) {return 0;} else { return b;}
		case("WHILE"): b=this.WHILE(token); if(b==0) {return 0;} else { return b;}
		case("PRINT"): b=this.PRINT(token); if(b==0) {return 0;} else { return b;}
		case("INPUT"): b=this.INPUT(token); if(b==0) {return 0;} else { return b;}
		case("GT"):  b=this.GT(token); if(b==0) {return 0;} else { return b;}
		case("LT"): b=this.GT(token); if(b==0) {return 0;} else { return b;}
		case("OR"): b=this.AND(token); if(b==0) {return 0;} else { return b;}
		case("NOT"): b=this.NOT(token); if(b==0) {return 0;} else { return b;}
		case("AND"): b=this.AND(token); if(b==0) {return 0;} else { return b;}
		case("ADD"): b=this.ADD(token); if(b==0) {return 0;} else { return b;}
		case("SUB"): b=this.ADD(token); if(b==0) {return 0;} else { return b;}
		case("DIV"): b=this.ADD(token); if(b==0) {return 0;} else { return b;}
		case("MUL"): b=this.ADD(token); if(b==0) {return 0;} else { return b;}
		case("EQ"): b=this.GT(token); if(b==0) {return 0;} else { return b;}
		default: return 0;}
	}

		private int BLOCK(int token) {
			token++;
			int b=0;
			if(tokens.get(token).equals(")")) {e.PrintErr("ERROR:expected '(' at token:", token);}
			if(tokens.get(token).equals("(")) {b=this.RecursiveParse(token);token=b;}
			if(b==0) {e.PrintErr("ERROR in BLOCK", token);}
		
		    if(tokens.get(token).equals(")")) {return token;}
			else {e.PrintErr("ERROR: expected ) at token", token); return 0;}
			}
		
		
		private int IF(int token) {
			int b=0,cnt=0;
			if(tokens.get(token+1).equals(")")) {e.PrintErr("ERROR:expected '(' at token:", token);}
			token++;

			while(tokens.get(token).equals(")")==false) {
				if(tokens.get(token).equals("(")) {b=this.RecursiveParse(token);token=b+1;cnt++;continue;} 

				else {e.PrintErr("ERROR in IF statement at token: ", token);return 0;}}
			return token;}

			
		private int SET(int token) {
			token++;
			int b=0;
			if(tokens.get(token).matches("-?\\d+")) { e.PrintErr("ERROR:variable expected at token:", token);}
			if(k.VarVerify(tokens.get(token))) {
				token++;
				if(tokens.get(token).equals("(")) {k.ChangeValue(tokens.get(token-1), 0); b=RecursiveParse(token); token=b+1; 
					if(tokens.get(token).equals(")")) return token; else{e.PrintErr("ERROR statement not closed", token); return 0;}}
				if(tokens.get(token).matches("-?\\d+")) {k.ChangeValue(tokens.get(token-1), Integer.parseInt(tokens.get(token))); token++; 
					if(tokens.get(token).equals(")")) return token; else{e.PrintErr("ERROR statement not closed", token); return 0;}}
				if(k.VarVerify(tokens.get(token))){k.ChangeValue(tokens.get(token-1), k.GetValue(tokens.get(token)));}
					else {e.PrintErr("ERROR:this variable doesen't exist", token);} token++;
						if(tokens.get(token).equals(")")) return token; else{e.PrintErr("ERROR statement not closed", token); return 0;}}
			//Ora se la variabile non esiste
			else {
				token++;
				if(tokens.get(token).equals("(")) {k.NewVar(tokens.get(token-1), 0); b=RecursiveParse(token); token=b+1; 
					if(tokens.get(token).equals(")")) return token; else{e.PrintErr("ERROR statement not closed", token); return 0;}}
				if(tokens.get(token).matches("-?\\d+")) {k.NewVar(tokens.get(token-1), Integer.parseInt(tokens.get(token)));token++;
					if(tokens.get(token).equals(")")) return token; else{e.PrintErr("ERROR statement not closed", token); return 0;}}
				if(k.VarVerify(tokens.get(token))){k.NewVar(tokens.get(token-1), k.GetValue(tokens.get(token)));}
				else {e.PrintErr("ERROR:this variable doesn't exist", token);}token++;
					if(tokens.get(token).equals(")")) return token; else{e.PrintErr("ERROR statement not closed", token); return 0;}}
		}
		
		
		private int PRINT(int token) {
			int b;
			token++;
			if(tokens.get(token).equals(")")) {e.PrintErr("ERROR:variable/number expected at token:", token); return 0;}
			if(tokens.get(token).matches("-?\\d+")) {return token;}
			if(tokens.get(token).equals("(")) {b=RecursiveParse(token);token=b+1;if(tokens.get(token).equals(")"))return token; else {e.PrintErr("ERROR:')' closing statement expected at token:", token); return 0;}}
			else {if(k.VarVerify(tokens.get(token))==true) return token; else{e.PrintErr("ERROR:variable not declared at token:", token); return 0;}}
		}
			
		private int INPUT(int token) {
			token++;
			if(tokens.get(token).matches("-?\\d+")) {e.PrintErr("ERROR:variable expected at token:", token); return 0;}
			if(tokens.get(token).equals(")")||tokens.get(token).equals("(")) {e.PrintErr("ERROR:variable expected at token:", token);}
			if(k.VarVerify(tokens.get(token))) {token++; if(tokens.get(token).equals(")")) return token; else e.PrintErr("ERROR at token", token); return 0;}
			else{k.NewVar(tokens.get(token), 0);token++; if(tokens.get(token).equals(")")) return token; else e.PrintErr("ERROR AT TOKEN:", token); return 0;}
			//Se trovo una variabile che non esiste la alloco con valore 0 da cambiare con changevalue() su Execution durante l'esecuzione;
		}
		private int WHILE(int token) {
			token++;
			int b=0,cnt=0;
			if(tokens.get(token).equals(")")) {e.PrintErr("ERROR:condition expected at token:", token); return 0;}

			if(tokens.get(token).equals("(")) b=RecursiveParse(token);
			if(b==0) {e.PrintErr("ERROR at token:", token); return 0;}
			token=b;
			
		
			if(tokens.get(token).equals(")")) return token;
			else {e.PrintErr("ERROR:')' expected at token:", token);return 0;}
		}
		private int GT(int token) {
			int b=0;
			token++;
			
			for(int i=0;i<2;i++) {
				
				if(tokens.get(token).equals(")")) {e.PrintErr("ERROR:variable/number expected att token:", token); return 0;}
				if(tokens.get(token).matches("-?\\d+")) { token++; continue;}
				if(k.VarVerify(tokens.get(token))) {token++; continue;}
				if(tokens.get(token).equals("(")) {b=RecursiveParse(token); token=b+1; 
				if(b==0) {e.PrintErr("ERROR at token:", token); return 0;}continue;}
				else {e.PrintErr("ERROR:variable not declared at token:", token);return 0;
				}}
			if(tokens.get(token).equals(")")) return token;
			else {e.PrintErr("ERROR:')' expected at token:", token); return 0;}
				}
		private int ADD(int token) {
			token++;
			int b=0,cnt=0;
			if(tokens.get(token).equals(")")) {e.PrintErr("ERROR:variable or statement expected at token:", token);}
			while(tokens.get(token).equals(")")==false) {
				
			if(tokens.get(token).equals(")")) break;
			if(tokens.get(token).matches("-?\\d+")) {token++; cnt++; continue;}
			if(k.VarVerify(tokens.get(token))) {token++; cnt++; continue;}
			if(tokens.get(token).equals("(")) {b=RecursiveParse(token);token=b+1; cnt++; continue;}
			else{ e.PrintErr("ERROR variable not declared at token: ",token); return 0;}}
			//controllo che il contatore degli operandi ne abbia trovati almeno due
			if(cnt<2) {e.PrintErr("ERROR:Missing Operand", token);}
			return token;}
			
		private int NOT(int token) {
			token ++;
			int b=0;
			if(tokens.get(token).equals(")")) {e.PrintErr("ERROR: statement expected at token:", token);}
			while(tokens.get(token).equals(")")==false) {
				if(tokens.get(token).equals("(")) {b=RecursiveParse(token);token=b+1; continue;}
				else {e.PrintErr("ERROR: statement expected at token:", token);}
			}
			return token;
		}
		
		private int AND(int token) {
			token++;
			int b=0;
			if(tokens.get(token).equals(")")) {e.PrintErr("ERROR:variable or statement expected at token:", token);}
			while(tokens.get(token).equals(")")==false) {
				if(tokens.get(token).equals("(")) {b=RecursiveParse(token);token=b+1; continue;}
				else {e.PrintErr("ERROR:statement expected at token:", token); return 0;}}
			return token;
		}
		
}		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

