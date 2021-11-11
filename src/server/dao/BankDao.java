package server.dao;

import vo.BankRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankDao {
    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public BankRecord rsToRecord()
    {
        try
        {
            BankRecord record = new BankRecord();
            record.setuID(rs.getString("uID"));
            record.setRecord(rs.getString("Record"));
            record.setAmount(rs.getDouble("Amount"));
            record.setDate(rs.getString("Date"));
            return record;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BankRecord> rsToRecordList()
    {
        try
        {
            ArrayList<BankRecord> list = new ArrayList<BankRecord>();
            do{
                BankRecord record = new BankRecord();
                record.setuID(rs.getString("uID"));
                record.setRecord(rs.getString("Record"));
                record.setAmount(rs.getDouble("Amount"));
                record.setDate(rs.getString("Date"));
                list.add(record);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BankRecord> query(String uID )
    {
        try{
            String sql = "select * from BankRecord where uID = '" + uID +"'";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next())
            {
                return rsToRecordList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public  ArrayList<BankRecord> queryByRecord(String uID,String record)
    {
        try{
            String sql = "select * from BankRecord where uID = '" + uID + "'and Record = '"+ record +"'";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next())
            {
                return rsToRecordList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public void insert(BankRecord rec)
    {
        try{
            String sql = "insert into BankRecord(uID,Record,Amount,Date)" +
                    "values('"+rec.getuID()+"','"+rec.getRecord()+"','"+rec.getAmount()+"','"+rec.getDate()+"')";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void update(BankRecord rec)
    {

        try{
            String sql = "update BankRecord set uID='"+rec.getuID()+"' ,Record='"+rec.getRecord()+"' , "
                    + "Amount='"+rec.getAmount()+"' ,Date='"+rec.getDate()+"'";
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void delete(String uID)
    {
        try{
            String sql = "delete from BankRecord where uID ='"+uID+"'";
            //sql.executeUpdate();
            stmt = access.connection.prepareStatement(sql);
            // stmt.setString(1, uID);
            stmt.executeUpdate();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

//    public static void main(String args[])
//	{
//    	queryByRecord("213167254","支付学费");
//
//	}
//

}
