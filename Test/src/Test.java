import java.util.ArrayList;

public class Test {
	private ArrayList<Integer> s;
	
	public Test(ArrayList<Integer> str){
		s=str;
		System.out.println(returnKey());
	}
	public int returnKey(){
		return s.size();
	}
}
