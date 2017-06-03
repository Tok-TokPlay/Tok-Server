package com.example.a4p.client;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    ArrayList<Integer> userBeat = new ArrayList<>();
    public ArrayList<Integer> getList(){
        return userBeat;
    }
    public void setList(ArrayList<Integer> list){
        this.userBeat=list;
    }
}


