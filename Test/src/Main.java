import java.util.ArrayList;

public class Main {
	public static void main(String[] args){
		String s=args[0];
		ArrayList<Integer> userBeat = new ArrayList<>();
		for(int i=0;i<s.length();i++){
		userBeat.add(Integer.parseInt(Character.toString(s.charAt(i))));
		}
		Test t= new Test(userBeat);
		
	}
}
