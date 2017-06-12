/**
 * @author Jin Hee Park, Hyun Joon Choi
 * @param IP, Port : for communication.
 */
package com.example.dkdk6.toktokplay.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.dkdk6.toktokplay.Activity.timeseries.TimeSeries;

public class TCPServer implements Runnable {
	// Port and IP can be changed. DO NOT ASSIGN IT CONSTANT.
	private TCPConfig configValue;

	// Database class for search music.
	private static MusicDataBase database;

	private final static int USER_TIMEOUT = 500;

	// Constructor part
	// Do not make default constructor, because of target Database.

	public TCPServer(String newIP, int newPort, String newDirectory,
			MusicDataBase database) {
		super();
		// Set Input parameter values into configure class and database.
		setConfigValue(new TCPConfig(newIP, newPort, newDirectory));
		this.setDatabase(database);
	}

	public TCPServer(TCPConfig serverConfig, MusicDataBase database) {
		super();
		// Set Input parameter values into configure class and database.
		setConfigValue(serverConfig);
		this.setDatabase(database);
	}

	@Override
	public void run() {
		try {
			new TableInit(database.getMusicPath());
			// Print now IP and Port.
			System.out
					.println("Server: Connecting with IP:["
							+ configValue.getIp() + "]:["
							+ configValue.getPort() + "]");
			// Make Connection with server.
			ServerSocket serverSocket = new ServerSocket(configValue.getPort());

			while (true) {
				System.out.println("Server: Waiting with IP:["
						+ configValue.getIp() + "]:[" + configValue.getPort()
						+ "]");
				// Make socket for one client.
				Socket client = serverSocket.accept();
				System.out.println("Server: Receiving from port..."
						+ client.getPort());

				try {
					InputStream is = client.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);

					// Read Input Socket Stream and change into String User
					// beat.
					Data inputData = (Data) ois.readObject();
					String sUserBeat = inputData.toString();

					// command would
					// "$SERVER_PATH\\Test.bat $NOW_PATH\\Search.jar + USER_BEAT".
					System.out
							.println("Server: Search is processing from port..."
									+ client.getPort());
					// Comparing compare = new
					// Comparing(configValue.getDbDirectory(),sUserBeat);

					// get Input Stream with sub shell system.

					// Read music key value from sub-shell.
					System.gc();
					String WeightedUserBeat = ChangeUserBeat(sUserBeat);
					System.out.println(WeightedUserBeat);
/*
					FileWriter fw = new FileWriter(
							"C:\\Users\\4F\\Desktop\\musicFile\\tx\\G-DRAGON-04-\ufffd\ufffd\ufffd\ufffd(\ufffd\ufffd\ufffd) (Untitled, 2014).txt");
					for (int i = 0; i < WeightedUserBeat.length(); i++) {
						fw.write(WeightedUserBeat.charAt(i));
						fw.write("\r\n");
					}
					fw.close();

*/
					// Get File names...

					File dbDirectoryPath = new File(this.getConfigValue().getDbDirectory());

					File[] fileList = dbDirectoryPath.listFiles();
					
					int fileNumber = 0;
					
					for (int i = 0; i < fileList.length; i++) {
						if (fileList[i].isFile()) {
							fileNumber++;
						}
					}
					// Save file for user beat

					File beatFile = new File(this.getConfigValue().getDbDirectory() + "\\userBeat\\" +  "userBeat.txt");
					File beatDir = new File(this.getConfigValue().getDbDirectory() + "\\userBeat\\");
					
					if(beatDir.exists() == false)	{
						beatDir.mkdirs();
					}
					if(beatFile.exists())	{
						// delete exist file.
						beatFile.delete();
					}
					beatFile.createNewFile();
					// Create new one.
					
					FileWriter resultWriter = new FileWriter(beatFile, true) ;
					
					// Write value and finish act.
					resultWriter.write(String.valueOf(WeightedUserBeat));
					resultWriter.flush();
					resultWriter.close();
					
					// And make jobs for files.
					ProcessJob[] jobList = new ProcessJob[fileNumber];
					
					fileNumber=0;
					for (int i = 0; i < fileList.length; i++) {
						if (fileList[i].isFile()) {
							jobList[fileNumber] = new ProcessJob(fileList[i].getName(), 
									this.getConfigValue().getDbDirectory() + "\\userBeat\\" +  "userBeat.txt", 
									this.getConfigValue().getDbDirectory());
							fileNumber++;
						}
					}

					MultiProcessing processor = new MultiProcessing(jobList, 10);
					processor.multiProcessStart();

					String[] musicKey = processor.getMusicKey();
					
					System.out.println();
					System.out.println("resultQueue    fileNameQueue bellow_______________________________");
					
					for (int i = 0; i < processor.resultQueue.size(); i++)	{
						System.out.println(processor.resultQueue.get(i) + "   " + processor.fileNameQueue.get(i));
					}
					
					System.out.println();
					for(int i = 0; i < musicKey.length; i++)	{
						System.out.println(musicKey[i]);
					}
					System.gc();
					if (musicKey != null) {
						String musicInfo = "3";
						for (int i = 0; i < 3; i++) {
							String musicInfoIndex = database
									.getMusicInfo(musicKey[i]);
							musicInfo = musicInfo + ":" + musicInfoIndex;
						}
						// Music name for music key value is always exist.
						// Not need to check if this value is null.

						// Output to client ( Android ).
						OutputStream os = client.getOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(os);

						// Give music name and singer with parsing comment ":".
						System.out.println(musicInfo);
						oos.writeObject(musicInfo);
						// Close not using stream controller.
						os.close();
						oos.close();
					}
					// Close not using stream controller.
					is.close();
					ois.close();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					client.close();
					System.out
							.println("Server: Give music information to client is done from port..."
									+ client.getPort());
				}
				// Input user`s request 0.5 seconds per once.
				Thread.sleep(USER_TIMEOUT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String ChangeUserBeat(String userBeat) {
		String weightedBeat = "";

		for (int i = 0; i < userBeat.length(); i++) {

			weightedBeat += (Integer.parseInt(("" + userBeat.charAt(i))) * 9);

		}
		return weightedBeat;
	}

	public TCPConfig getConfigValue() {
		return configValue;
	}

	public void setConfigValue(TCPConfig configValue) {
		this.configValue = configValue;
	}

	public MusicDataBase getDatabase() {
		return database;
	}

	public void setDatabase(MusicDataBase database) {
		this.database = database;
	}
}