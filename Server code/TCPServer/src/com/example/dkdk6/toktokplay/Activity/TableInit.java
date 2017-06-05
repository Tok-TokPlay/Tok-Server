package com.example.dkdk6.toktokplay.Activity;

import java.io.*; 
import java.util.*; 

public class TableInit { 
	byte musicKeyBuf[] = new byte[3];	 //musicKey value (3bytes)
	byte musicBuf[] = new byte[31];		 //music value 	  (30bytes but +1 for null value in advance)
	byte singerBuf[] = new byte[31];	 //singer value	  (30bytes but +1 for null value in advance)
	
	

	public void readMusicHeader(){ 
		
		//Firstly, drop the existing table
		DBTableInit DropTable = new DBTableInit();
		
		//Find the all mp3 file in the directory
		File path = new File("C:\\Users\\5p\\Desktop\\musicFile");
		final String fatternName = ".mp3";
		
		String fileList[] = path.list(new FilenameFilter() {
			 
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(fatternName);     //read only .mp3 file 
		    }
		 
		});
		
		//read all mp3 header file in the directory
		for(int i=0; i<fileList.length; i++){
			File f = new File("C:\\Users\\5p\\Desktop\\musicFile\\" + fileList[i]);	//set the music file directory in here
			RandomAccessFile rf;
			try {
				rf = new RandomAccessFile(f , "r");
				try {
					rf.seek(rf.length()-128);
				} catch (IOException e) {
					e.printStackTrace();
				} 
				try {
					rf.read( musicKeyBuf ,0 ,3 );
					rf.read( musicBuf ,0 ,30 );
					rf.read( singerBuf ,0 ,30 );
				} catch (IOException e) {
					e.printStackTrace();
				} 
				//modify the music and singer value for Insert to DB
				try{
					String musicTemp = new String(musicBuf, 0, musicBuf.length);
					musicTemp = musicTemp.replaceAll("\0", "");
					StringTokenizer musicTemp1 = new StringTokenizer(musicTemp, "(");
					String music = musicTemp1.nextToken();
				
					String singer = new String(singerBuf, 0, singerBuf.length);
					singer = singer.replaceAll("\0", "");
					
					//Insert to DB
					DBTableInsert TableInsert = new DBTableInsert(music, singer);
					
				} catch (Exception e){
					System.out.println("Null mp3 header");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public TableInit()throws Exception{ 
		readMusicHeader(); 
	}	 
}