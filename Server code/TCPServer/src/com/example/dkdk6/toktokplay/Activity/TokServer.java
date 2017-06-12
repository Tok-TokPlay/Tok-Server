/**
 * @author Hyun Joon Choi
 * This code act 3 function.
 * 1. Make music DB with given python code or bash / batch shell.
 * - Insert to Database.
 * 2. Check and Search music with user input.
 * 3. Communication with Android Client. Listen User input and Talk What is this music.
 * */
package com.example.dkdk6.toktokplay.Activity;

import java.sql.SQLException;

public class TokServer {
	private static TCPConfig serverConfig;
	private static MusicDataBase musicDB;

	public static void main(String[] arg) throws ClassNotFoundException, SQLException {
		if(arg.length == 3) {
			serverConfig = new TCPConfig(arg[0], Integer.parseInt(arg[1]), arg[2] + "/txt");
			musicDB = new MusicDataBase(arg[2] + "/music");
		}
		else {
			System.out.println("Please input in order with IP, Port, DB_Directory please...");
		}
		listenClient();
	}
	
	private static void listenClient(){
		// Listen and find music at DB.
		Thread listeningThread = new Thread(new TCPServer(serverConfig, musicDB));
		listeningThread.start();
	}
}
