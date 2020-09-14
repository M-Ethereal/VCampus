package server.dao;

import vo.Retailer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetailerDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

//    public boolean insert(Retailer retailer) throws SQLException {
//        try
//        {
//            String sql = "INSERT INTO Teacher (teacherId, teacherName, teacherPwd, userType"
//                    +"age, sex, majorId, title, lendBooksNum) VALUES ( '"
//                    + teacher.getId()
//                    +"' , '"+teacher.getName()
//                    +"' , '"+teacher.getpwd()
//                    +"' , '"+ 1
//                    +"' , '"+teacher.getAge()
//                    +"' , '"+teacher.getSex()
//                    +"' , '"+teacher.getMajorId()
//                    +"' , '"+teacher.getTitle()
//                    +"' , '"+teacher.getLendBooksNum()
//                    +"' )";
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
//
//    public boolean delete(Teacher teacher) {
//        try{
//            String sql = "DELETE * FROM Teacher WHERE teacherId =?";
//            stmt = access.connection.prepareStatement(sql);
//            stmt.setString(1, teacher.getId());
//            stmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean update(Teacher teacher) {
//        try
//        {
//            String sql = "UPDATE Teacher SET teacherName=?,teacherPwd=?,userType=?,"
//                    + "age=?,sex=?,majorId=?,title=?,"
//                    + "lendBooksNum=?"
//                    + "WHERE teacherID=?";
//            stmt=access.connection.prepareStatement(sql);
//            stmt = access.connection.prepareStatement(sql);
//            stmt.setString(1, teacher.getName());
//            stmt.setString(2, teacher.getpwd());
//            stmt.setInt(3, 1);
//            stmt.setInt(4, teacher.getAge());
//            stmt.setInt(5, teacher.getSex());
//            stmt.setInt(6, teacher.getMajorId());
//            stmt.setInt(7, teacher.getTitle());
//            stmt.setInt(8, teacher.getLendBooksNum());
//            stmt.setString(9, teacher.getId());
//            stmt.executeUpdate();
//            return true;
//        }catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }


    public boolean updateRenown(Retailer retailer) throws SQLException {

        try{
            String sql = "UPDATE Retailer SET renown=? WHERE reId=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,retailer.getRenown());
            stmt.setString(2,retailer.getId());
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public Retailer queryById(String reID) throws SQLException {
        String sql= "SELECT * FROM Retailer where reId="+ "'"+ reID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToRetailer();
        }
        return null;
    }


    public Retailer rsToRetailer()
    {
        try
        {
            Retailer retailer = new Retailer();
            retailer.setId(rs.getString("reId"));
            retailer.setpwd(rs.getString("rePwd"));
            retailer.setUserType(3);
            retailer.setName(rs.getString("reName"));
            retailer.setJobType(rs.getInt("jobType"));
            retailer.setAge(rs.getInt("age"));
            retailer.setSex(rs.getInt("sex"));
            retailer.setRenown(rs.getString("renown"));
            retailer.setShopName(rs.getString("shopName"));
            retailer.setPosition(rs.getString("position"));
            return retailer;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
