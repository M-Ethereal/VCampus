package server.dao;

import vo.Student;
import vo.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public Teacher rsToTeacher()
    {
        try
        {
            Teacher teacher = new Teacher();
            teacher.setUserType(1);
            teacher.setpwd(rs.getString("teacherPwd"));
            teacher.setId(rs.getString("teacherID"));
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

    public ArrayList<Teacher> rsToTeacherList()
    {
        try
        {
            ArrayList<Teacher> list = new ArrayList<Teacher>();
            do{
                Teacher teacher=new Teacher();
                teacher.setUserType(1);
                teacher.setpwd(rs.getString("teacherPwd"));
                teacher.setId(rs.getString("teacherID"));
                teacher.setName(rs.getString("teacherName"));
                teacher.setLendBooksNum(rs.getInt("lendBooksNum"));
                teacher.setAge(rs.getInt("age"));
                teacher.setSex(rs.getInt("sex"));
                teacher.setTitle(rs.getInt("title"));
                teacher.setMajorId(rs.getInt("majorId"));
                list.add(teacher);
            } while (rs.next());
            return list;

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public boolean insert(Teacher teacher) throws SQLException {
        try
        {
            String sql = "INSERT INTO Teacher (teacherId, teacherName, teacherPwd, userType,"
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

    public boolean delete(String teacherID) {
        try{
            String sql = "delete from Teacher where teacherID ='"+teacherID+"'";
            stmt = access.connection.prepareStatement(sql);
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
            stmt=access.connection.prepareStatement(sql);
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public Teacher query(String TeacherID) throws SQLException {
        String sql= "SELECT * FROM Teacher where teacherId="+ "'"+ TeacherID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToTeacher();
        }
        return null;
    }

    public ArrayList<Teacher> queryAll( )
    {
        try{
            String sql = "select * from Teacher";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                return rsToTeacherList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
