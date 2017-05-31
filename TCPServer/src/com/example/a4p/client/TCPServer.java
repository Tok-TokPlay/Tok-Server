package com.example.a4p.client;
/**
 * Created by ������ on 2017-05-24.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable {

	public static final int ServerPort = 9191;
	public static final String ServerIP = "165.194.17.109";
	ArrayList<Integer> userBeat = new ArrayList<>();
	private String musicKey = null;

	@Override
	public void run() {
		String receiveData;

		try {
			System.out.println("S: Connecting...");
			ServerSocket serverSocket = new ServerSocket(ServerPort);

			while (true) {
				Socket client = serverSocket.accept();
				System.out.println("S: Receiving...");
				try {
					// ���ź�
					InputStream is = client.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);

					Data d = (Data) ois.readObject();
					userBeat = (ArrayList<Integer>) d.getList();
					
					String sUserBeat ="";
					for(int i=0;i<userBeat.size();i++){
						sUserBeat+=userBeat.get(i).toString();
					}
					System.out.println(sUserBeat);
					if (userBeat.size() > 0) {
						// Ȯ�ο�
						System.out.println("TCPServer" + " Receive : " + sUserBeat);
						// ���۹��� ������ ó�� : batch ���� �����Ѵ�.
						String command = "C:\\Users\\4p\\Desktop\\Test\\Test.bat " +sUserBeat;
						Process proc = Runtime.getRuntime().exec(command);
						InputStreamReader isr = new InputStreamReader(proc.getInputStream());
						BufferedReader br = new BufferedReader(isr);

						if ((musicKey = br.readLine()) != null) {
							System.out.println(musicKey + "\n");
						}
						

					} else {

					}
				//	Thread.sleep(50);

					// �۽ź�
					System.out.println("����Ű��" + musicKey);

					OutputStream os = client.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);

					if ((receiveData = musicKey) != null) {

						oos.writeObject("��" + receiveData);
						oos.flush();
						System.out.println("Ŭ���̾�Ʈ���� �������ϴ�....");
					}
					is.close();
					ois.close();
					os.close();
					oos.close();

				} catch (Exception e) {
					System.out.println("S: Error");
					e.printStackTrace();
				} finally {
					client.close();
					System.out.println("S: Done.");
				}
			}
		} catch (Exception e) {
			System.out.println("S: Error");
			e.printStackTrace();
		}
	}

	public static void main(String[] arg) {
		Thread desktopServerThread = new Thread(new TCPServer());
		desktopServerThread.start();

	}
}
