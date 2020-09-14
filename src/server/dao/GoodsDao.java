package server.dao;

import java.sql.*;

public class GoodsDao {

    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement sql = null;
    private ResultSet res = null;

    public void TagQuery(String Tag){//根据标签搜索商品
        try {
            sql=con.prepareStatement("select* from tbl_Shop where gTag=?");
            sql.setString(1, Tag);
            res=sql.executeQuery();
            while(res.next()){
                String name=res.getString("gName");//名称
                String price=res.getString("gPrice");//价格
                String tag=res.getString("gTag");//标签
                String introduction=res.getString("gIntroduction");//介绍
                String store=res.getString("gStore");//货存
                System.out.println(name+" "+price+" "+tag+" "+introduction+" "+store);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void NameQuery(String Name) {//根据名称信息搜索商品
        try {
            sql=con.prepareStatement("select* from tbl_Shop where gname like ?");
            sql.setString(1, "%"+Name+"%");
            res=sql.executeQuery();
            while(res.next()){
                String name=res.getString("gName");
                String price=res.getString("gPrice");
                String tag=res.getString("gTag");
                String introduction=res.getString("gIntroduction");
                String store=res.getString("gStore");
                System.out.println(name+" "+price+" "+tag+" "+introduction+" "+store);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void InsertGoods(String name,String price,String tag,
                            String introduction,String store) {//插入商品
        try {
            sql=con.prepareStatement("insert into tbl_Shop values(?,?,?,?,?,?)");
            sql.setString(1, "1");
            sql.setString(2,name);
            sql.setString(3,price);
            sql.setString(4,tag);
            sql.setString(5,introduction);
            sql.setString(6, store);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void DeleteGoods(String name) {//删除商品
        try {
            sql=con.prepareStatement("delete from tbl_Shop where gName=?");
            sql.setString(1,name);
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void UpdateGoods(String id,String g,String u){//g:替换前的；u:替换后的ֵ
        try {
            if(g=="gName") {
                sql=con.prepareStatement("update tbl_Shop set gName=?where gID="+id);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gPrice") {
                sql=con.prepareStatement("update tbl_Shop set gPrice=?where gID="+id);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gTag") {
                sql=con.prepareStatement("update tbl_Shop set gTag=?where gID="+id);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gIntroduction") {
                sql=con.prepareStatement("update tbl_Shop set gIntroduction=?where gID"+id);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else if(g=="gStore") {
                sql=con.prepareStatement("update tbl_Shop set gStore=?where gID"+id);
                sql.setString(1, u);
                sql.executeUpdate();
            }
            else
                System.out.println("Wrong！");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
