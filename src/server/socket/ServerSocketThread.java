package server.socket;

//import server.biz.UserManagementImp;
import server.dao.DoctorDao;
import util.MessageType;
import vo.Doctor;
import vo.Message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketThread implements Runnable{
    private Socket clientSocket;
    //private UserManagementImp dao = new UserManagementImp();
    private DoctorDao docDao = new DoctorDao();

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
            //System.out.println("1");
            switch (object.getMessageType())
            {
//                case Constants.userLogin:
//                    try
//                    {
//                        Login lg = (Login)object.getData();
//                        //System.out.println("2:"+lg.getId()+lg.getPwd());
//                        if(dao.checkPwd(lg.getId(), lg.getPwd(), lg.getUserType()))
//                        {
//                            //System.out.println("3");
//                            serverResponse.setLastOperState(true);
//                            serverResponse.setMessageType(Constants.operFeedback);
//                        }
//                        else
//                        {
//                            serverResponse.setLastOperState(false);
//                            serverResponse.setErrorMessage("Password is wrong");
//                            serverResponse.setMessageType(Constants.operFeedback);
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    } finally {
//                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
//                        response.writeObject(serverResponse);
//                    }
//                    break;
                case MessageType.doc_query_all:
                    try
                    {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Doctor> docs = docDao.queryAll();

                        if(docs!=null) serverResponse.setData(docs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
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
