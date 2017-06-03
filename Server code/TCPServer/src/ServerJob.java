/**
 * This class is for Server Administration.
 * 1. Initialize music Database table.
 * 2. Force output into correct way.
 * */

import java.util.Scanner;

public class ServerJob implements Runnable{
	private MusicDataBase musicDB;
	private final static int USER_TIMEOUT = 500;
	
	public ServerJob(MusicDataBase musicDB)	{
		this.musicDB = musicDB;
	}
	@Override
	public void run() {
		Scanner jobList = new Scanner(System.in);
		boolean jobFlag = true;
		while(jobFlag)	{
			// if jobList.nextLine() is 1 or 2 or others...
			switch (Integer.parseInt(jobList.nextLine())){
			case 1:
				// Initialize music DB table.
				musicDB.initTable();
				break;
			case 2:
				// Force it to correct way.
				
				
				
				break;
			case 3:
				// Stop get input.
				jobFlag = false;
				break;
			default:
				break;
			}
			try {
				Thread.sleep(USER_TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jobList.close();
	}
}
