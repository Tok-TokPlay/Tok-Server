import java.io.*;
import java.net.*;

class Client
{
   Socket client = null;
   String ipAddress; //접속을 요청할 Server의 IP 주소를 저장할 변수
   static final int port = 9000; //접속을 요청할 Server의 port 번호와 동일하게 지정
 
   BufferedReader read;
 
   //입력용 Stream
   InputStream is;
   ObjectInputStream ois;

   //출력용 Stream
   OutputStream os;
   ObjectOutputStream oos;
 
   String sendData;
   String receiveData;

   Client(String ip)
   {
      ipAddress = ip; //생성자의 IP Address를 ipAddress 맴버변수에 저장

      try
      {
         System.out.println("***** Clinet가 Server로 접속을 시작합니다 *****");

         //*** 접속할 Server의 IP Address와 port 번호 정보가 있는 Socket 생성 ***//
         client = new Socket(ipAddress,port);
         //*** Client Socket이 생성되면, Server의 accept()가 수행된다 ***//


         //*** 사용자 비트를 받아서 system.in 에 넣어준다. ***//
         read = new BufferedReader(new InputStreamReader(System.in));

         //*** Server로 message를 송신하기 위한 출력 Stream ***//
         os = client.getOutputStream();
         oos = new ObjectOutputStream(os);

         //*** Server로 보낸 message를 수신받기 위한 입력  Stream ***//
         is = client.getInputStream();
         ois = new ObjectInputStream(is);

       

         //*** 키보드로부터 message를 입력 받아 Server로 전송한 후 다시 받아서 출력 ***//
         while ((sendData = read.readLine()) != null)
         {
            oos.writeObject(sendData); //ObjectOutputStream.writeObject() 호출
            oos.flush(); //버퍼의 데이터를 효율적으로 전송하기 위한 method

            if (sendData.equals("$$")) //"quit" 입력시 종료
            {
               break;
            }
            receiveData = (String)ois.readObject(); //ObjectInputStream.readObject() 호출

            System.out.println(client.getInetAddress()+"의 message ECHO: "+receiveData);
            System.out.print("입력 →");
         }
         is.close();
         ois.close();
         os.close();
         oos.close();
         client.close(); //Socket 닫기
      }
      catch (Exception e)
      {
         System.out.println("통신 Error !!");
         System.exit(0);
      }
   }
   public static void main(String[] args) 
   {
      new Client("165.194.17.210"); //Server Program이 실행되는 컴퓨터의 IP Address를 입력
   }
}

