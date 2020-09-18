package client.socket;

import vo.Message;

import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    public Message sendRequestToServer (Message clientRequest ) {
        try
        {
            Socket socket = new Socket("localhost",19888);
            socket.setSoTimeout(10000);
            ObjectOutputStream request = new ObjectOutputStream(socket.getOutputStream());
            request.writeObject(clientRequest);
            request.flush();
            socket.shutdownOutput();

            ObjectInputStream response = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            Message message = (Message)response.readObject();
            response.close();
            socket.close();


            if(message!=null)
            {
                return message;
            }
        }
        catch (UnknownHostException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//
//        try
//        {
//            Socket socket=new Socket("localhost",19888);
//            OutputStream outputStream=socket.getOutputStream();//获取一个输出流，向服务端发送信息
//            PrintWriter printWriter=new PrintWriter(outputStream);//将输出流包装成打印流
//            printWriter.print("服务端你好，我是小魔龙");
//            printWriter.flush();
//            socket.shutdownOutput();//关闭输出流
//
//            InputStream inputStream=socket.getInputStream();//获取一个输入流，接收服务端的信息
//            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);//包装成字符流，提高效率
//            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//缓冲区
//            String info="";
//            String temp=null;//临时变量
//            while((temp=bufferedReader.readLine())!=null){
//                info+=temp;
//                System.out.println("客户端接收服务端发送信息："+info);
//            }
//
//            //关闭相对应的资源
//            bufferedReader.close();
//            inputStream.close();
//            printWriter.close();
//            outputStream.close();
//            socket.close();
//        }
//        catch (UnknownHostException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//			/*catch (ClassNotFoundException e) {
//				 //TODO Auto-generated catch block
//				e.printStackTrace();
//			}*/
//
//    }
}

