package server.socket;

import java.net.ServerSocket;
import java.net.Socket;

import server.biz.*;

public class Server implements Runnable{
    //private ArrayList<ContactInfo> onlineUsers;
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try
        {
            ServerSocket serverSocket = new ServerSocket(19888);
            System.out.println("服务端已启动，等待客户端连接..");

            while(true)
            {
                Socket socket = serverSocket.accept();
                ServerSocketThread n = new ServerSocketThread(socket);
                Thread t = new Thread(n);
                t.start();
                Thread.sleep(200);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }


}
