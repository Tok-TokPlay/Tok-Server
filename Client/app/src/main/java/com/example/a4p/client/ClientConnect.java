package com.example.a4p.client;

/**
 * Created by 4p on 2017-05-31.
 */
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ClientConnect
{
    ArrayList<Integer> userBeat;
    Socket client = null;
    String ipAddress; //접속을 요청할 Server의 IP 주소를 저장할 변수
    static final int port = 9191; //접속을 요청할 Server의 port 번호와 동일하게 지정

    BufferedReader read;

    //입력용 Stream
    InputStream is;
    ObjectInputStream ois;

    //출력용 Stream
    OutputStream os;
    ObjectOutputStream oos;

    String sendData;
    String receiveData;

    ClientConnect(String ip, ArrayList<Integer> beat)
    {
        ipAddress = ip; //생성자의 IP Address를 ipAddress 맴버변수에 저장
        userBeat = beat;

        new Thread(){
            public void run(){
                try
                {
                    System.out.println("***** Clinet가 Server로 접속을 시작합니다 *****");

                    //*** 접속할 Server의 IP Address와 port 번호 정보가 있는 Socket 생성 ***//
                    client = new Socket(ipAddress,port);


                    //*** Client Socket이 생성되면, Server의 accept()가 수행된다 ***//


                    //*** 사용자 비트를 받아서 system.in 에 넣어준다. ***//
                    //read = new BufferedReader(userBeat.);

                    //*** Server로 message를 송신하기 위한 출력 Stream ***//
                    os = client.getOutputStream();
                    oos = new ObjectOutputStream(os);

                    //*** Server로 보낸 message를 수신받기 위한 입력  Stream ***//





                    ///arraylist를 직렬화해서 서버에 보낸다.
                        Data d = new Data();
                        d.setList(userBeat);
                        oos.reset();

                        oos.writeObject(d);
                        oos.flush();

                    is = client.getInputStream();
                    ois = new ObjectInputStream(is);
                    Log.v("test2","22");//ObjectOutputStream.writeObject() 호출
                    receiveData = (String)ois.readObject(); //ObjectInputStream.readObject() 호출
                    System.out.println(client.getInetAddress()+"의 message ECHO: "+receiveData);
                    System.out.print("입력 →");

                    os.close();
                    oos.close();
                    is.close();
                    ois.close();
                    client.close(); //Socket 닫기

                }
                catch (Exception e)
                {
                    System.out.println(e+"통신 Error !!");
                    System.exit(0);
                }
            }
        }.start();

    }
}


