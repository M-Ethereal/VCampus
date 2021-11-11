package server.dao;

import vo.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GoodsDao {

    private DbHelper access = new DbHelper();
    private Connection con = access.connection;
    private PreparedStatement sql = null;
    private ResultSet res = null;

    public ArrayList<Goods> TagQuery(String Tag){//根据标签搜索商品
        try {
            sql=con.prepareStatement("select* from Product where gTag=?");
            sql.setString(1, Tag);
            res=sql.executeQuery();
            while(res.next()){
                return resToGoodsList();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Goods> NameQuery(String Name) {//根据名称信息搜索商品
        try {
            sql=con.prepareStatement("select* from Product where gname like ?");
            sql.setString(1, "%"+Name+"%");
            res=sql.executeQuery();
            while(res.next()){
                return resToGoodsList();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Goods> SellerQuery(String sID) {//根据名称信息搜索商品
        try {
            sql=con.prepareStatement("select* from Product where gsID=?");
            sql.setString(1, sID);
            res=sql.executeQuery();
            while(res.next()){
                return resToGoodsList();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Goods> AllQuery() {
        try {
            sql=con.prepareStatement("select* from Product");
            res=sql.executeQuery();
            while(res.next()){
                return resToGoodsList();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void InsertGoods(Goods good) {//插入商品
        try {
            sql=con.prepareStatement("insert into Product (gName, gPrice, gTag, gIntroduction, gStore, gsID) values(?,?,?,?,?,?)");
            sql.setString(1, good.getgName());
            sql.setString(2, good.getgPrice());
            sql.setString(3, good.getgTag());
            sql.setString(4, good.getgIntroduction());
            sql.setString(5, good.getgStore());
            sql.setString(6, good.getgsID());
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void DeleteGoods(Goods good) {//删除商品
        try {
            sql=con.prepareStatement("delete from Product where gName=?");
            sql.setString(1,good.getgName());
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void Update(Goods good){
        try {
            sql=con.prepareStatement("update Product set gStore=?where gName=?");
            sql.setString(1, good.getgStore());
            sql.setString(2, good.getgName());
            sql.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Goods> resToGoodsList(){
        try {
            ArrayList<Goods> list=new ArrayList<Goods>();
            do {
                Goods good=new Goods();
                good.setgName(res.getString("gName"));
                good.setgPrice(res.getString("gPrice"));
                good.setgTag(res.getString("gTag"));
                good.setgIntroduction(res.getString("gIntroduction"));
                good.setgStore(res.getString("gStore"));
                good.setgsID(res.getString("gsID"));
                list.add(good);
            }while(res.next());
            return list;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

