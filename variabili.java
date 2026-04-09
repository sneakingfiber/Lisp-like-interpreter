import java.util.HashMap;

public class variabili {
	public HashMap<String,Integer> variables= new HashMap<String, Integer>();
	public Errors e= new Errors();
	variabili(){
		//HashMap<String, Integer> variables = new HashMap<String, Integer>();
	}
	variabili(HashMap<String, Integer> x){
		//HashMap<String, Integer> variables = new HashMap<String, Integer>();
		variables=x;
	}
	
	public void NewVar(String namevar,int value){
		variables.put(namevar, value);
	}
	public boolean VarVerify(String namevar) {
			if(variables.get(namevar)==null) return false;
			else return true;
	}
	public void ChangeValue(String namevar,int value) {
		if(VarVerify(namevar)==true) {variables.put(namevar,value);}
		else e.PrintErr("Error:variable doesn't exists",0);;
	}
	public int GetValue(String namevar) {
		if(VarVerify(namevar)==true) {
			return variables.get(namevar);
		}
		else e.PrintErr("Error occured while taking the value of this variable", 0); 
			return 0;
		
	}

}
