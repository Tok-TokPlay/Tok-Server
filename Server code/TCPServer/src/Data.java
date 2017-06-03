import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
	private static final long serialVersionUID = 878194L;
	//serialVersionUID is for check serialize ID.
	//This may help flexible checking to Old and new version.
	private ArrayList<Integer> userBeat = new ArrayList<>();
	
	public ArrayList<Integer> getList(){
        return userBeat;
    }
    public void setList(ArrayList<Integer> list){
        this.userBeat=list;
    }
}