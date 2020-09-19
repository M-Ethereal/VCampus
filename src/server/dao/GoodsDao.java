package server.dao;

import java.sql.*;
import vo.Goods;

public class GoodsDao {

    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement sql = null;
    private ResultSet res = null;

    public Goods TagQuery(Goods good){//根据标签搜索商品
        try {
            sql=con.prepareStatement("select* from Product where gTag=?");
            sql.setString(1, good.gTag);
            res=sql.executeQuery();
            while(res.next()){
            	return resToGoods();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
		return null;
    }
    public Goods NameQuery(Goods good) {//根据名称信息搜索商品
        try {
            sql=con.prepareStatement("select* from Product where gname like ?");
            sql.setString(1, "%"+good.gName+"%");
            res=sql.executeQuery();
            while(res.next()){
                return resToGoods();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
		return null;
    }
    public void InsertGoods(Goods good) {//插入商品
        try {
            sql=con.prepareStatement("insert into Product values(?,?,?,?,?,?)");
            sql.setString(1, "1");
            sql.setString(2,good.gName);
            sql.setString(3,good.gPrice);
            sql.setString(4,good.gTag);
            sql.setString(5,good.gIntroduction);
            sql.setString(6,good.gStore);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void DeleteGoods(Goods good) {//删除商品
        try {
            sql=con.prepareStatement("delete from Product where gName=?");
            sql.setString(1,good.gName);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void UpdateGoods(String name,String g,String u){//g:要替换的；u:替换后的ֵ
        try {
            if(g=="gName") {
                sql=con.prepareStatement("update Product set gName=?where gName="+name);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gPrice") {
                sql=con.prepareStatement("update Product set gPrice=?where gName="+name);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gTag") {
                sql=con.prepareStatement("update Product set gTag=?where gName="+name);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gIntroduction") {
                sql=con.prepareStatement("update Product set gIntroduction=?where gName="+name);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gStore") {
                sql=con.prepareStatement("update Product set gStore=?where gName="+name);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else
                System.out.println("Wrong！");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public Goods resToGoods(){
    	try {
    		Goods good=new Goods();
    		good.setgID(res.getString("gID"));
    		good.setgName(res.getString("gName"));
    		good.setgPrice(res.getString("gPrice"));
    		good.setgTag(res.getString("gTag"));
    		good.setgIntroduction(res.getString("gIntroduction"));
    		good.setgStore(res.getString("gStore"));
    		return good;
    	}catch(Exception e) {
            e.printStackTrace();
        }
    	return null;
    }
    public static void main(String []args) {
    	Goods good=new Goods();
    	good.setgID("1");
    	good.setgName("无线鼠标");
    	good.setgPrice("100");
    	good.setgTag("电子设备");
    	good.setgIntroduction("蓝牙可充电");
    	good.setgStore("10");
    	GoodsDao d=new GoodsDao();
    	//d.InsertGoods(good);
    	d.UpdateGoods("蒙牛纯牛奶", "gPrice", "50");//异常！！！
    	//d.TagQuery(good);
    	//d.DeleteGoods(good);
    }
}

