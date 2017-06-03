/**
 * @author Jin Hee Park, Hyun Joon Choi
 * @param IP, Port : for communication.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {
	// Port and IP can be changed. DO NOT ASSIGN IT CONSTANT.
	private TCPConfig configValue;
	
	// Database class for search music.
	private MusicDataBase database;
	
	// Constructor part
	// Do not make default constructor, because of target Database. 
	public TCPServer(MusicDataBase database)	{
		super();
		// Set Default values into configure class and database.
		setConfigValue(new TCPConfig("165.194.17.11", 8801, "DB\\"));
		this.setDatabase(database);
	}
	
	public TCPServer(String newIP, int newPort, String newDirectory, MusicDataBase database){
		super();
		// Set Input parameter values into configure class and database.
		setConfigValue(new TCPConfig(newIP, newPort, newDirectory));
		this.setDatabase(database);
	}
	
	@Override
	public void run() {
		try {       
			//Print now IP and Port.
			System.out.println("Server: Connecting with IP:[" + configValue.getIp() + "]:[" + configValue.getPort() + "]");
			//Make Connection with server.
			ServerSocket serverSocket = new ServerSocket(configValue.getPort());
			
			while (true) {
				// Make socket for one client.
				Socket client = serverSocket.accept();
				System.out.println("Server: Receiving...");
				
				try {
					InputStream is = client.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);

					// Read Input Socket Stream and change into String User beat.
					Data inputData = (Data)ois.readObject();
					String sUserBeat = inputData.toString();
					
					// command would "$SERVER_PATH\\Test.bat $NOW_PATH\\Search.jar + USER_BEAT".
					String command = getConfigValue().getDbDirectory() + "Test.bat" + System.getProperty("user.dir")+"Search.jar" + sUserBeat;
					Process proc = Runtime.getRuntime().exec(command);
					
					// get Input Stream with sub shell system.
					InputStreamReader isr = new InputStreamReader(proc.getInputStream());
					BufferedReader br = new BufferedReader(isr);
					
					// Read music key value from sub-shell.
					String musicKey = br.readLine();
					if (musicKey!= null) {
						String musicName = database.getMusicName(musicKey);
						String musicSinger = database.getMusicSinger(musicKey);
						
						// Music name for music key value is always exist.
						// Not need to check if this value is null.
						
						// Output to client ( Android ).
						OutputStream os = client.getOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(os);
						
						// Give music name and singer with parsing comment "^^".
						oos.writeObject(musicName + "^^" + musicSinger);
						
						// Close not using stream controller.
						os.close();
						oos.close();
					}
					// Close not using stream controller.
					is.close();
					ois.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				} 
				finally {
					client.close();
					serverSocket.close();
					System.out.println("Server: Give music information to client is done.");
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
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
