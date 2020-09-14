package server.dao;

import java.sql.*;
import vo.ShopRecord;

public class ShopRecordDao {

    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement sql = null;
    private ResultSet res = null;

    public ShopRecord sRecordQuery(ShopRecord rec) {//搜索卖家记录
        try {
            sql=con.prepareStatement("select* from ShopRecord where dsID=?");
            sql.setString(1, rec.dsID);
            res=sql.executeQuery();
            while(res.next()) {
                return resToRecord();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
		return null;
    }
    public ShopRecord bRecordQuery(ShopRecord rec) {//搜索买家记录
        try {
            sql=con.prepareStatement("select* from ShopRecord where dbID=?");
            sql.setString(1, rec.dbID);
            res=sql.executeQuery();
            while(res.next()) {
                return resToRecord();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
		return null;
    }
    public void InsertRecord(ShopRecord rec) {//插入购买记录
        try {
            sql=con.prepareStatement("insert into ShopRecord values(?,?,?,?,?,?,?)");
            sql.setString(1, "1");
            sql.setString(2,rec.dName);
            sql.setString(3,rec.dsID);
            sql.setString(4,rec.dbID);
            sql.setBoolean(5,rec.dDelivery);
            sql.setString(6,rec.dDate);
            sql.setString(7,rec.dNum);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void DeletesRecord(ShopRecord rec) {
        try {
            sql=con.prepareStatement("delete from ShopRecord where dsID=?");
            sql.setString(1,rec.dsID);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void DeletebRecord(ShopRecord rec) {
        try {
            sql=con.prepareStatement("delete from ShopRecord where dbID=?");
            sql.setString(1,rec.dbID);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public ShopRecord resToRecord() {
    	try {
    		ShopRecord rec=new ShopRecord();
    		rec.setdName(res.getString("dName"));
    		rec.setdsID(res.getString("dsID"));
    		rec.setdID(res.getString("dbID"));
    		rec.setdDate(res.getString("dDate"));
    		rec.setdDelivery(res.getBoolean("dDelivery"));
    		rec.setdNum(res.getString("dNum"));
    		rec.setdID(res.getString("dID"));
    	}catch(Exception e) {
            e.printStackTrace();
        }
		return null;
    }
}
