package server.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;

public class LendRecordDao{
    static Connection con=null;
    static PreparedStatement sql=null;
    static Statement sqll=null;
    static ResultSet res=null;
    public Connection getConnectionn()
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        String path="D://Database2.accdb";
        try
        {
            con=DriverManager.getConnection("jdbc:ucanaccess://"+path,"","");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("success");
        return con;
    }

    //借书记录的增 如未归还设置recordEndDate为空
    public static void Insert(String recordID,String recordStudentID,String recordBookID,String recordStartDate,
                              String recordEndDate,boolean isReturn)
    {
        LendRecordCon c=new LendRecordCon();
        con=c.getConnectionn();
        try
        {
            sql=con.prepareStatement("insert into LendRecord values(?,?,?,?,?,?,?)");
            sql.setInt(1, 1);
            sql.setString(2, recordID);
            sql.setString(3, recordStudentID);
            sql.setString(4,recordBookID);

            sql.setString(5, recordStartDate);
            sql.setString(6, recordEndDate);

            sql.setBoolean(7, isReturn);

            sql.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }


    //改 只能改状态和还书日期 两个一起改 还书的同时状态改变 按下还书按钮获取当前日期
    public static void UpDateByReturn(String recordID)
    {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        DateFormat date = DateFormat.getDateInstance();
        String str = date.format(now);

        LendRecordCon c=new LendRecordCon();
        con=c.getConnectionn();
        try
        {
            sql=con.prepareStatement("update LendRecord set RecordEndDate =?where RecordID="+recordID);
            sql.setString(1,str);
            sql=con.prepareStatement("update LendRecord set IsReturn =?where RecordID="+recordID);
            sql.setBoolean(1,true);
            sql.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }


    //通过RecordStudentID来查询 通过借阅人的一卡通号来查找该人所有记录
    public static void SearchByRecordStudentID(String recordStudentID)
    {
        LendRecordCon c=new LendRecordCon();
        con=c.getConnectionn();
        try
        {
            String s="select * from LendRecord where RecordStudentID = ?";
            sql=con.prepareStatement(s);
            sql.setString(1, recordStudentID);
            res=sql.executeQuery();
            while(res.next())
                System.out.println("RecordID="+res.getString(2)+" RecordStudentID="+res.getString(3)+
                        " RecordBookID="+res.getString("RecordBookID"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    //通过RecordBookID来查询 通过书的唯一ID来查 查此书的所有借阅记录
    public static void SearchByRecordBookID(String recordBookID)
    {
        LendRecordCon c=new LendRecordCon();
        con=c.getConnectionn();
        try
        {
            String s="select * from LendRecord where RecordBookID = ?";
            sql=con.prepareStatement(s);
            sql.setString(1, recordBookID);
            res=sql.executeQuery();
            while(res.next())
                System.out.println("RecordID="+res.getString(2)+" RecordStudentID="+res.getString(3)+
                        " RecordBookID="+recordBookID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}


