package com.example.dkdk6.toktokplay.Activity;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
	private static final long serialVersionUID = 878194L;
	//serialVersionUID is for check serialize ID.
	//This may help flexible checking to Old and new version.
	private ArrayList<Integer> list = new ArrayList<>();
	
	public ArrayList<Integer> getList(){
        return list;
    }
	
    public void setList(ArrayList<Integer> list){
        this.list=list;
    }
    
    @Override
    public String toString()	{
		//Change Data list into String.
    	String returnValue = "";
    	for(int i = 0; i < this.getList().size(); i++){
    		returnValue = returnValue.concat(getList().get(i).toString());
    	}
    	return returnValue;
    }
}