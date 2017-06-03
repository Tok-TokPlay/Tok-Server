/**
 * @author Hyun Joon Choi
 * This code act 3 function.
 * 1. Make music DB with given python code or bash / batch shell.
 * - Insert to Database.
 * 2. Check and Search music with user input.
 * 3. Communication with Android Client. Listen User input and Talk What is this music.
 * */
public class TokServer {
	private static TCPConfig serverConfig;
	private static MusicDataBase musicDB;

	public static int main(String[] arg) {
		if(arg.length == 3) {
			serverConfig = new TCPConfig(arg[0], Integer.parseInt(arg[1]), arg[2]);
		}
		else {
			System.out.println("Please input in order with IP, Port, DB_Directory please...");
			return 0;
		}
		
		return 0;
	}
	
	private void listenClient(){
		// Listen and find music at DB.
		Thread desktopServerThread = new Thread(new TCPServer(serverConfig, musicDB));
		desktopServerThread.start();
	}
	
	private void addMusic(String musicName, String musicSinger, String fileName)	{
		musicDB.addMusic(musicName, musicSinger, fileName);
	}
}
