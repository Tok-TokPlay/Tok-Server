/**
 * @author Jin Hee Park, Hyun Joon Choi
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable {
	// Port and IP can be changed. DO NOT ASSIGN IT CONSTANT.
	private TCPConfig configValue;
	
	// User beat and other value must be private, because of prevent data collapse.
	private ArrayList<Integer> userBeat = new ArrayList<>();
	private String musicKey = null;
	private String musicInfo ="";
	
	public TCPServer()	{
		// Basic Constructor : Do nothing.
		super();
		// Set Default values into configure class.
		setConfigValue(new TCPConfig("165.194.17.11", 8801, ""));
	}
	public TCPServer(String newIP, int newPort, String newDirectory){
		setConfigValue(new TCPConfig(newIP, newPort, newDirectory));
	}
	
	@Override
	public void run() {
		String receiveData;

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

					Data d = (Data) ois.readObject();
					userBeat = (ArrayList<Integer>) d.getList();
					
					String sUserBeat ="";
					for(int i=0;i<userBeat.size();i++){
						sUserBeat+=userBeat.get(i).toString();
					}
					
					if (userBeat.size() > 0) {
						System.out.println("TCPServer" + " Receive : " + sUserBeat);
						String command = "C:\\Users\\5p\\Desktop\\Tok-Server-hee\\Tok-Server-hee\\Test.bat " +sUserBeat;
						Process proc = Runtime.getRuntime().exec(command);
						InputStreamReader isr = new InputStreamReader(proc.getInputStream());
						BufferedReader br = new BufferedReader(isr);

						if ((musicKey = br.readLine()) != null) {
							JDBCexam j =new JDBCexam(musicKey);
							musicInfo=j.getRetuVal();
							
							System.out.println("music����" +musicInfo+ "\n");
						}
						

					} 
					else {

					}
				//	Thread.sleep(50);

					OutputStream os = client.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);

					if ((receiveData = musicInfo) != null) {

						oos.writeObject("��" + receiveData);
						oos.flush();
						System.out.println("Ŭ���̾�Ʈ���� ���½��ϴ�....");
					}
					is.close();
					ois.close();
					os.close();
					oos.close();

				} 
				catch (Exception e) {
					System.out.println("S: Error");
					e.printStackTrace();
				} 
				finally {
					client.close();
					serverSocket.close();
					System.out.println("S: Done.");
				}
			}
		} 
		catch (Exception e) {
			System.out.println("S: Error");
			e.printStackTrace();
		}
	}

	public static void main(String[] arg) {
		Thread desktopServerThread = new Thread(new TCPServer());
		desktopServerThread.start();
	}
	
	public TCPConfig getConfigValue() {
		return configValue;
	}
	public void setConfigValue(TCPConfig configValue) {
		this.configValue = configValue;
	}
}
