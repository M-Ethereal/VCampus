package server.dao;

import vo.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs=null;

//    public boolean insert(Admin admin) throws SQLException {
//        try
//        {
//            String sql = "INSERT INTO Admin (adminId, adminPwd, userType"
//                    + admin.getId()
//                    +"' , '"+admin.getpwd()
//                    +"' , '"+ 4
//                    +" )";
//            stmt = access.connection.prepareStatement(sql);
//            stmt.executeUpdate();
//            return true;
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    public boolean delete(Admin admin) {
//        try{
//            String sql = "DELETE * FROM Admin WHERE adminId =?";
//            stmt = access.connection.prepareStatement(sql);
//            stmt.setString(1, admin.getId());
//            stmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean update(Admin admin) {
//        try
//        {
//            String sql = "UPDATE Admin SET adminPwd=?,userType=?"
//                    + "WHERE adminID=?";
//            stmt=access.connection.prepareStatement(sql);
//            stmt = access.connection.prepareStatement(sql);
//            stmt.setString(1, admin.getId());
//            stmt.setString(2, admin.getpwd());
//            stmt.setInt(3, 1);
//            stmt.executeUpdate();
//            return true;
//        }catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public Admin query(String AdminID) throws SQLException {
        String sql= "SELECT * FROM Admin where adminId="+ "'"+ AdminID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToAdmin();
        }
        return null;
    }


    public Admin rsToAdmin()
    {
        try
        {
            Admin admin = new Admin();
            admin.setUserType(4);
            admin.setpwd(rs.getString("adminPwd"));
            admin.setId(rs.getString("adminId"));
            return admin;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
