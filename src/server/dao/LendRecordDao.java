package server.dao;

import vo.LendRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LendRecordDao{
    private DbHelper access=new DbHelper();
    //private Connection con=null;
    private PreparedStatement stmt=null;
    //	private Statement sqll=null;
    private ResultSet res=null;


    //借书记录的增 如未归还设置recordEndDate为空
    public void Insert(LendRecord lend)
    {
        try
        {
            String sql="insert into LendRecord values(?,?,?,?,?,?,?,?)";
            stmt=access.connection.prepareStatement(sql);


            stmt.setInt(1, 1);
            stmt.setString(2, lend.getRecordID());//recordID
            stmt.setString(3, lend.getRecordStudentID());//recordStudentID
            stmt.setString(4,lend.getRecordBookID());//recordBookID

            stmt.setString(5, lend.getRecordStartDate());//recordStartDate
            stmt.setString(6, lend.getRecordEndDate());//recordEndDate

            stmt.setBoolean(7, lend.getIsReturn());//isReturn
            stmt.setString(8, lend.getRecordBookName());
            stmt.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    //改 只能改状态和还书日期 两个一起改 还书的同时状态改变 按下还书按钮获取当前日期
    public LendRecord UpDate(LendRecord lend)
    {

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        DateFormat date = DateFormat.getDateInstance();
        String str = date.format(now);
        try
        {
            String sql = "update LendRecord set RecordEndDate =?where RecordID="+lend.getRecordID();
            stmt=access.connection.prepareStatement(sql);

            stmt.setString(1, str);
            stmt.executeUpdate();

            sql="update LendRecord set IsReturn =?where RecordID="+lend.getRecordID();
            stmt=access.connection.prepareStatement(sql);

            stmt.setBoolean(1,true);
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lend;

    }
    //通过RecordID来查询
    public ArrayList<LendRecord> SearchByRecordID(String recordID)
    {
        ArrayList<LendRecord> arr = new ArrayList();
        try
        {
            String sql="select * from LendRecord where RecordID = ?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, recordID);
            res=stmt.executeQuery();
            while(res.next())
            {
                LendRecord lendRecord=new LendRecord();
                lendRecord.setRecordID(res.getString(2));
                lendRecord.setRecordStudentID(res.getString(3));
                lendRecord.setRecordBookID(res.getString(4));
                lendRecord.setRecordStartDate(res.getString(5));
                lendRecord.setRecordEndDate(res.getString(6));
                lendRecord.setIsReturn(res.getBoolean(7));
                lendRecord.setRecordBookName(res.getString(8));
                arr.add(lendRecord);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;

    }

    //通过RecordStudentID来查询 通过借阅人的一卡通号来查找该人所有记录
    public ArrayList<LendRecord> SearchByRecordStudentID(String recordStudentID)
    {
        ArrayList<LendRecord> arr = new ArrayList();
        try
        {
            String sql="select * from LendRecord where RecordStudentID = ?";
            stmt=access.connection.prepareStatement(sql);


            stmt.setString(1, recordStudentID);
            res=stmt.executeQuery();
            while(res.next())
            {
                LendRecord lendRecord=new LendRecord();
                lendRecord.setRecordID(res.getString(2));
                lendRecord.setRecordStudentID(res.getString(3));
                lendRecord.setRecordBookID(res.getString(4));
                lendRecord.setRecordStartDate(res.getString(5));
                lendRecord.setRecordEndDate(res.getString(6));
                lendRecord.setIsReturn(res.getBoolean(7));
                lendRecord.setRecordBookName(res.getString(8));
                arr.add(lendRecord);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;

    }
    public ArrayList<LendRecord> SearchByRecordStudentIDIsReturn(String recordStudentID)
    {

        ArrayList<LendRecord> arr = new ArrayList();
        try
        {
            String sql="select * from LendRecord where RecordStudentID = ?";
            stmt=access.connection.prepareStatement(sql);

            stmt.setString(1, recordStudentID);
            res=stmt.executeQuery();
            while(res.next())
            {
                if(res.getBoolean(7)==true)
                {
                    LendRecord lendRecord=new LendRecord();
                    lendRecord.setRecordID(res.getString(2));
                    lendRecord.setRecordStudentID(res.getString(3));
                    lendRecord.setRecordBookID(res.getString(4));
                    lendRecord.setRecordStartDate(res.getString(5));
                    lendRecord.setRecordEndDate(res.getString(6));
                    lendRecord.setIsReturn(res.getBoolean(7));
                    lendRecord.setRecordBookName(res.getString(8));
                    arr.add(lendRecord);
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }
    public ArrayList<LendRecord> SearchByRecordStudentIDNotReturn(String recordStudentID)
    {
        ArrayList<LendRecord> arr = new ArrayList();
        try
        {
            String sql="select * from LendRecord where RecordStudentID = ?";
            stmt=access.connection.prepareStatement(sql);



            stmt.setString(1, recordStudentID);
            res=stmt.executeQuery();
            while(res.next())
            {
                if(res.getBoolean(7)==false)
                {
                    LendRecord lendRecord=new LendRecord();
                    lendRecord.setRecordID(res.getString(2));
                    lendRecord.setRecordStudentID(res.getString(3));
                    lendRecord.setRecordBookID(res.getString(4));
                    lendRecord.setRecordStartDate(res.getString(5));
                    lendRecord.setRecordEndDate(res.getString(6));
                    lendRecord.setIsReturn(res.getBoolean(7));
                    lendRecord.setRecordBookName(res.getString(8));
                    arr.add(lendRecord);
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;

    }
    //通过RecordBookID来查询 通过书的唯一ID来查 查此书的所有借阅记录
    public ArrayList<LendRecord> SearchByRecordBookID(String recordBookID)
    {

        ArrayList<LendRecord> arr = new ArrayList();
        try
        {
            String sql="select * from LendRecord where RecordBookID = ?";
            stmt=access.connection.prepareStatement(sql);

            stmt.setString(1, recordBookID);
            res=stmt.executeQuery();
            while(res.next())
            {
                LendRecord lendRecord=new LendRecord();
                lendRecord.setRecordID(res.getString(2));
                lendRecord.setRecordStudentID(res.getString(3));
                lendRecord.setRecordBookID(res.getString(4));
                lendRecord.setRecordStartDate(res.getString(5));
                lendRecord.setRecordEndDate(res.getString(6));
                lendRecord.setIsReturn(res.getBoolean(7));
                lendRecord.setRecordBookName(res.getString(8));
                arr.add(lendRecord);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }
    public String getRecordStudetID(String recordID)
    {

        try
        {
            String sql="select * from LendRecord where RecordID = ?";
            stmt=access.connection.prepareStatement(sql);

            stmt.setString(1, recordID);
            res=stmt.executeQuery();
            while(res.next())
                return res.getString(3);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public String getRecordBookID(String recordID)
    {


        try
        {
            String sql="select * from LendRecord where RecordID = ?";
            stmt=access.connection.prepareStatement(sql);

            stmt.setString(1, recordID);
            res=stmt.executeQuery();
            while(res.next())
                return res.getString(4);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LendRecord> returnAll()
    {

        ArrayList<LendRecord> arr = new ArrayList();
        try
        {
            String sql="select * from LendRecord where RecordID like ?";
            stmt=access.connection.prepareStatement(sql);

            String recordBookID="";
            stmt.setString(1, "%"+recordBookID+"%");
            res=stmt.executeQuery();
            while(res.next())
            {
                LendRecord lendRecord=new LendRecord();
                lendRecord.setRecordID(res.getString(2));
                lendRecord.setRecordStudentID(res.getString(3));
                lendRecord.setRecordBookID(res.getString(4));
                lendRecord.setRecordStartDate(res.getString(5));
                lendRecord.setRecordEndDate(res.getString(6));
                lendRecord.setIsReturn(res.getBoolean(7));
                lendRecord.setRecordBookName(res.getString(8));
                arr.add(lendRecord);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }

}




