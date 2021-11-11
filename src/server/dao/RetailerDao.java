package server.dao;

import vo.Retailer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RetailerDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public Retailer rsToRetailer()
    {
        try
        {
            Retailer retailer = new Retailer();
            retailer.setId(rs.getString("reId"));
            retailer.setpwd(rs.getString("retPwd"));
            retailer.setUserType(3);
            retailer.setName(rs.getString("reName"));
            retailer.setJobType(rs.getInt("jobType"));
            retailer.setAge(rs.getInt("age"));
            retailer.setSex(rs.getInt("sex"));
            retailer.setRenown(rs.getString("renown"));
            retailer.setShopName(rs.getString("shopName"));
            retailer.setPosition(rs.getString("rePosition"));
            return retailer;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Retailer> rsToRetailerList()
    {
        try
        {
            ArrayList<Retailer> list = new ArrayList<Retailer>();
            do{
                Retailer retailer = new Retailer();
                retailer.setId(rs.getString("reId"));
                retailer.setpwd(rs.getString("retPwd"));
                retailer.setUserType(3);
                retailer.setName(rs.getString("reName"));
                retailer.setJobType(rs.getInt("jobType"));
                retailer.setAge(rs.getInt("age"));
                retailer.setSex(rs.getInt("sex"));
                retailer.setRenown(rs.getString("renown"));
                retailer.setShopName(rs.getString("shopName"));
                retailer.setPosition(rs.getString("rePosition"));
                list.add(retailer);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Retailer> queryAll( )
    {
        ArrayList<Retailer> list = null;
        try{
            String sql = "select * from Retailer";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs == null)System.out.print("null");
            if(rs.next())
            {
                return rsToRetailerList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Retailer query(String reID) throws SQLException {
        System.out.println("Dao: 用户id:" + reID);
        String sql= "SELECT * FROM Retailer where reId='"+ reID +"'";
        System.out.println("Dao: sql:" + sql);
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToRetailer();
        }
        return null;
    }

    public boolean delete(String reID) {
        try{
            String sql = "delete from Retailer where reId ='"+reID+"'";
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(Retailer retailer) throws SQLException {
        try
        {
            String sql = "INSERT INTO Retailer (reId, reName, retPwd, userType,"
                    +"age, sex, jobType, renown, shopName, rePosition) VALUES ( '"
                    + retailer.getId()
                    +"' , '"+retailer.getName()
                    +"' , '"+retailer.getpwd()
                    +"' , '"+ 3
                    +"' , '"+retailer.getAge()
                    +"' , '"+retailer.getSex()
                    +"' , '"+ 1
                    +"' , '"+retailer.getRenown()
                    +"' , '"+retailer.getShopName()
                    +"' ,'"+retailer.getPosition()+"')";
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Retailer retailer) {
        try
        {
            System.out.println(retailer.getName() + " " + retailer.getId() + " " + retailer.getAge());
            String sql = "UPDATE Retailer SET reName=?,retPwd=?,userType=?,"
                    + "age=?,sex=?,jobType=?,renown=?,"
                    + "shopName=?, rePosition=?"
                    + "WHERE reID=?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, retailer.getName());
            stmt.setString(2, retailer.getpwd());
            stmt.setInt(3, 3);
            stmt.setInt(4, retailer.getAge());
            stmt.setInt(5, retailer.getSex());
            stmt.setInt(6, retailer.getJobType());
            stmt.setString(7, retailer.getRenown());
            stmt.setString(8, retailer.getShopName());
            stmt.setString(9, retailer.getPosition());
            stmt.setString(10, retailer.getId());
            stmt.executeUpdate();

            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
