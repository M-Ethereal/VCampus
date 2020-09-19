package server.dao;

import vo.Doctor;

import javax.print.Doc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

//    public boolean insert(Doctor doctor) throws SQLException {
//        try
//        {
//            String sql = "INSERT INTO Doctor (docId, docPwd, userType"
//                    +"docName, age, sex, renown, dept, position, jobType) VALUES ( '"
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
    public boolean updateRenown(Doctor doctor) throws SQLException {

        try{
            String sql = "UPDATE Doctor SET renown=? WHERE docId=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,doctor.getRenown());
            stmt.setString(2,doctor.getId());
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Doctor> queryAll() throws SQLException {
        String sql = "select * from Doctor";
        stmt = stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToDoctorList();
        }
        return null;
    }

    public Doctor queryById(String docID) throws SQLException {
        String sql= "SELECT * FROM Doctor where docId="+ "'"+ docID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToDoctor();
        }
        return null;
    }


    public Doctor rsToDoctor()
    {
        try
        {
            Doctor doctor = new Doctor();
            doctor.setId(rs.getString("docId"));
            doctor.setpwd(rs.getString("docPwd"));
            doctor.setUserType(2);
            doctor.setName(rs.getString("docName"));
            doctor.setJobType(rs.getInt("jobType"));
            doctor.setAge(rs.getInt("age"));
            doctor.setSex(rs.getInt("sex"));
            doctor.setRenown(rs.getString("renown"));
            doctor.setDept(rs.getString("dept"));
            doctor.setPosition(rs.getString("position"));
            return doctor;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Doctor> rsToDoctorList()
    {
        try
        {
            ArrayList<Doctor> list = new ArrayList<Doctor>();
            do{
                Doctor doctor = new Doctor();
                doctor.setId(rs.getString("docId"));
                doctor.setpwd(rs.getString("docPwd"));
                doctor.setUserType(2);
                doctor.setName(rs.getString("docName"));
                doctor.setJobType(rs.getInt("jobType"));
                doctor.setAge(rs.getInt("age"));
                doctor.setSex(rs.getInt("sex"));
                doctor.setRenown(rs.getString("renown"));
                doctor.setDept(rs.getString("dept"));
                doctor.setPosition(rs.getString("position"));
                list.add(doctor);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
