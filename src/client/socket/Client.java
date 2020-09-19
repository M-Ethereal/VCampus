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

}

