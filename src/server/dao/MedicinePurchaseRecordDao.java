package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicinePurchaseRecordDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(Teacher teacher) throws SQLException {
        try
        {
            String sql = "INSERT INTO Teacher (teacherId, teacherName, teacherPwd, userType"
                    +"age, sex, majorId, title, lendBooksNum) VALUES ( '"
                    + teacher.getId()
                    +"' , '"+teacher.getName()
                    +"' , '"+teacher.getpwd()
                    +"' , '"+ 1
                    +"' , '"+teacher.getAge()
                    +"' , '"+teacher.getSex()
                    +"' , '"+teacher.getMajorId()
                    +"' , '"+teacher.getTitle()
                    +"' , '"+teacher.getLendBooksNum()
                    +"' )";
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

    public boolean delete(Teacher teacher) {
        try{
            String sql = "DELETE * FROM Teacher WHERE teacherId =?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, teacher.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Teacher teacher) {
        try
        {
            String sql = "UPDATE Teacher SET teacherName=?,teacherPwd=?,userType=?,"
                    + "age=?,sex=?,majorId=?,title=?,"
                    + "lendBooksNum=?"
                    + "WHERE teacherID=?";
            stmt=access.connection.prepareStatement(sql);
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getpwd());
            stmt.setInt(3, 1);
            stmt.setInt(4, teacher.getAge());
            stmt.setInt(5, teacher.getSex());
            stmt.setInt(6, teacher.getMajorId());
            stmt.setInt(7, teacher.getTitle());
            stmt.setInt(8, teacher.getLendBooksNum());
            stmt.setString(9, teacher.getId());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public Teacher queryById(String TeacherID) throws SQLException {
        String sql= "SELECT * FROM Teacher where userId="+ "'"+ TeacherID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToTeacher();
        }
        return null;
    }


    public Teacher rsToTeacher()
    {
        try
        {
            Teacher teacher = new Teacher();
            teacher.setUserType(1);
            teacher.setpwd(rs.getString("teacherPwd"));
            teacher.setId(rs.getString("teacherId"));
            teacher.setName(rs.getString("teacherName"));
            teacher.setLendBooksNum(rs.getInt("lendBooksNum"));
            teacher.setAge(rs.getInt("age"));
            teacher.setSex(rs.getInt("sex"));
            teacher.setTitle(rs.getInt("title"));
            teacher.setMajorId(rs.getInt("majorId"));
            return teacher;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
