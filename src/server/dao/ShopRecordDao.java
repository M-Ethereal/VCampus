package server.dao;

import java.sql.*;

public class ShopRecordDao {

    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement sql = null;
    private ResultSet res = null;

    public void sRecordQuery(String ID) {//搜索卖家记录
        try {
            sql=con.prepareStatement("select* from tbl_Record where dsID=?");
            sql.setString(1, ID);
            res=sql.executeQuery();
            while(res.next()) {
                String name=res.getString("dName");//名称
                String bID=res.getString("dbID");//买家ID
                boolean delivery=res.getBoolean("dDelivery");//是否配送
                String date=res.getString("dDate");//交易时间
                String num=res.getString("dNum");//交易数量
                System.out.println(name+" "+bID+" "+delivery+" "+date+" "+num);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void bRecordQuery(String ID) {//搜索买家记录
        try {
            sql=con.prepareStatement("select* from tbl_Record where dbID=?");
            sql.setString(1, ID);
            res=sql.executeQuery();
            while(res.next()) {
                String name=res.getString("dName");//名称
                String sID=res.getString("dsID");//卖家ID
                boolean delivery=res.getBoolean("dDelivery");//是否配送
                String date=res.getString("dDate");//交易时间
                String num=res.getString("dNum");//交易数量
                System.out.println(name+" "+sID+" "+delivery+" "+date+" "+num);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void InsertRecord(String name,String sID,String bID,
                             boolean delivery,String date,String num) {//插入购买记录
        try {
            sql=con.prepareStatement("insert into tbl_Record values(?,?,?,?,?,?,?)");
            sql.setString(1, "1");
            sql.setString(2,name);
            sql.setString(3,sID);
            sql.setString(4,bID);
            sql.setBoolean(5,delivery);
            sql.setString(6, date);
            sql.setString(7, num);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void DeletesRecord(String ID) {
        try {
            sql=con.prepareStatement("delete from tbl_Record where dsID=?");
            sql.setString(1,ID);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void DeletebRecord(String ID) {
        try {
            sql=con.prepareStatement("delete from tbl_Record where dbID=?");
            sql.setString(1,ID);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
