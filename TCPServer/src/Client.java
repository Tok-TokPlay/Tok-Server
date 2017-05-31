import java.io.*;
import java.net.*;

class Client
{
   Socket client = null;
   String ipAddress; //������ ��û�� Server�� IP �ּҸ� ������ ����
   static final int port = 9000; //������ ��û�� Server�� port ��ȣ�� �����ϰ� ����
 
   BufferedReader read;
 
   //�Է¿� Stream
   InputStream is;
   ObjectInputStream ois;

   //��¿� Stream
   OutputStream os;
   ObjectOutputStream oos;
 
   String sendData;
   String receiveData;

   Client(String ip)
   {
      ipAddress = ip; //�������� IP Address�� ipAddress �ɹ������� ����

      try
      {
         System.out.println("***** Clinet�� Server�� ������ �����մϴ� *****");

         //*** ������ Server�� IP Address�� port ��ȣ ������ �ִ� Socket ���� ***//
         client = new Socket(ipAddress,port);
         //*** Client Socket�� �����Ǹ�, Server�� accept()�� ����ȴ� ***//


         //*** ����� ��Ʈ�� �޾Ƽ� system.in �� �־��ش�. ***//
         read = new BufferedReader(new InputStreamReader(System.in));

         //*** Server�� message�� �۽��ϱ� ���� ��� Stream ***//
         os = client.getOutputStream();
         oos = new ObjectOutputStream(os);

         //*** Server�� ���� message�� ���Źޱ� ���� �Է�  Stream ***//
         is = client.getInputStream();
         ois = new ObjectInputStream(is);

       

         //*** Ű����κ��� message�� �Է� �޾� Server�� ������ �� �ٽ� �޾Ƽ� ��� ***//
         while ((sendData = read.readLine()) != null)
         {
            oos.writeObject(sendData); //ObjectOutputStream.writeObject() ȣ��
            oos.flush(); //������ �����͸� ȿ�������� �����ϱ� ���� method

            if (sendData.equals("$$")) //"quit" �Է½� ����
            {
               break;
            }
            receiveData = (String)ois.readObject(); //ObjectInputStream.readObject() ȣ��

            System.out.println(client.getInetAddress()+"�� message ECHO: "+receiveData);
            System.out.print("�Է� ��");
         }
         is.close();
         ois.close();
         os.close();
         oos.close();
         client.close(); //Socket �ݱ�
      }
      catch (Exception e)
      {
         System.out.println("��� Error !!");
         System.exit(0);
      }
   }
   public static void main(String[] args) 
   {
      new Client("165.194.17.210"); //Server Program�� ����Ǵ� ��ǻ���� IP Address�� �Է�
   }
}

