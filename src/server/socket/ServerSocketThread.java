package server.socket;

import vo.Message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerSocketThread implements Runnable{
    private Socket clientSocket;

    ServerSocketThread(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public synchronized void run() {

        try{
            ObjectInputStream message = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            Message object = (Message)message.readObject();
            System.out.println("已与客户端建立连接，当前客户端ip为：" + clientSocket.getInetAddress().getHostAddress());
            System.out.println(object.getMessageType());
            Message serverResponse = new Message();
            switch (object.getMessageType())
            {

            }
        }
//        catch(){
//
//        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
