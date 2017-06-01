package com.example.dkdk6.toktokplay.Activity;


/**
 * Created by 박진희 on 2017-05-24.
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

	public static final int ServerPort = 9797;
	public static final String ServerIP = "165.194.17.11";
	ArrayList<Integer> userBeat = new ArrayList<>();
	private String musicKey = null;
	private String musicInfo ="";

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
					// 수신부
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
						// 확인용
						System.out.println("TCPServer" + " Receive : " + sUserBeat);
						// 전송받은 데이터 처리 : batch 파일 실행한다.
						String command = "C:\\Users\\5p\\Desktop\\Tok-Server-hee\\Tok-Server-hee\\Test.bat " +sUserBeat;
						Process proc = Runtime.getRuntime().exec(command);
						InputStreamReader isr = new InputStreamReader(proc.getInputStream());
						BufferedReader br = new BufferedReader(isr);

						if ((musicKey = br.readLine()) != null) {
							JDBCexam j =new JDBCexam(musicKey);
							musicInfo=j.getRetuVal();
							
							System.out.println("music정보" +musicInfo+ "\n");
						}
						

					} else {

					}
				//	Thread.sleep(50);

					// 송신부

					OutputStream os = client.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);

					if ((receiveData = musicInfo) != null) {

						oos.writeObject("→" + receiveData);
						oos.flush();
						System.out.println("클라이언트에게 보냈습니다....");
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
