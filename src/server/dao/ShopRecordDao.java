package server.dao;

import vo.ShopRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopRecordDao {

    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement sql = null;
    private ResultSet res = null;
    private PreparedStatement stmt = null;

    public ArrayList<ShopRecord> sRecordQuery(String sID) {//搜索卖家记录
        try {
            sql=con.prepareStatement("select* from ShopRecord where dsID=?");
            sql.setString(1,sID);
            res=sql.executeQuery();
            while(res.next()) {
                return resToRecordList();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ShopRecord> bRecordQuery(String bID) {//搜索买家记录
        try {
            sql=con.prepareStatement("select* from ShopRecord where dbID=?");
            sql.setString(1, bID);
            res=sql.executeQuery();
            while(res.next()) {
                return resToRecordList();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //打分的时候更新
    public boolean setRank(ShopRecord shopRecord) {
        try
        {
            String sql = "UPDATE ShopRecord SET isRank=? WHERE dName=? and dsID=? and dbID=? and dDate=? and dNum=?";
            System.out.println(sql);
            stmt = access.connection.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setString(2, shopRecord.getdName());
            stmt.setString(3, shopRecord.getdsID());
            stmt.setString(4,  shopRecord.getdbID());
            stmt.setString(5,  shopRecord.getdDate());
            stmt.setString(6,  shopRecord.getdNum());

            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void InsertRecord(ShopRecord rec) {//插入购买记录
        try {
            String sql = "INSERT INTO ShopRecord (dName, dsID, dbID, dDate, dNum, isRank) VALUES ( '"
                    + rec.getdName()
                    +"' , '"+ rec.getdsID()
                    +"' , '"+ rec.getdbID()
                    +"' , '"+ rec.getdDate()
                    +"' , '"+ rec.getdNum()
                    +"' , '"+ false
                    +"' )";

//            sql=con.prepareStatement("insert into ShopRecord values(?,?,?,?,?,?)");
//            sql.setString(1, "1");
//            sql.setString(2,rec.getdName());
//            sql.setString(3,rec.getdsID());
//            sql.setString(4,rec.getdbID());
//            sql.setString(5,rec.getdDate());
//            sql.setString(6,rec.getdNum());
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void DeletesRecord(ShopRecord rec) {
        try {
            sql=con.prepareStatement("delete from ShopRecord where dsID=?");
            sql.setString(1,rec.getdsID());
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void DeletebRecord(ShopRecord rec) {
        try {
            sql=con.prepareStatement("delete from ShopRecord where dbID=?");
            sql.setString(1,rec.getdbID());
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<ShopRecord> resToRecordList() {
        try {
            ArrayList<ShopRecord> list=new ArrayList<ShopRecord>();
            do {
                ShopRecord rec=new ShopRecord();
                rec.setdName(res.getString("dName"));
                rec.setdsID(res.getString("dsID"));
                rec.setdbID(res.getString("dbID"));
                rec.setdDate(res.getString("dDate"));
                rec.setdNum(res.getString("dNum"));
                rec.setRenown(res.getBoolean("isRank"));
                list.add(rec);
            }while(res.next());
            return list;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
