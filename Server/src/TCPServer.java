
/**
 * Created by 박진희 on 2017-05-24.
 */



import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {

    public static final int ServerPort = 9000;
    public static final String ServerIP = "165.194.17.210";



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
                	//수신부
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String str = in.readLine();
                    System.out.println("TCPServer"+"Receive : " + str);
                    
                    //송신부
                
                    OutputStream os = client.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                   
					while((receiveData=(String)in.readLine())!=null){
                    	if(receiveData.equals("$$")){
                    		break;
                    	}
                    	oos.writeObject("→"+receiveData);
                    	oos.flush();
                    }
                    in.close();
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

public static void main(String[] arg){
 Thread desktopServerThread = new Thread(new TCPServer());
    desktopServerThread.start();
 
}
}
