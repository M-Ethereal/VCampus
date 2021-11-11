package server.dao;

import vo.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

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
            doctor.setPosition(rs.getString("docPosition"));
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
                doctor.setPosition(rs.getString("DocPosition"));
                list.add(doctor);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Doctor query(String docID) throws SQLException {
        String sql= "SELECT * FROM Doctor where docID='"+ docID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToDoctor();
        }
        return null;
    }

    public ArrayList<Doctor> queryAll( )
    {
        try{
            String sql = "select * from Doctor";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs == null)System.out.print("null");
            if(rs.next())
            {
                return rsToDoctorList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(String docID) {
        try{
            String sql = "delete from Doctor where docID ='"+docID+"'";
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(Doctor doctor) throws SQLException {
        try
        {
            String sql = "INSERT INTO Doctor (docID, docName, docPwd, userType,"
                    +"age, sex, jobType, renown, dept, docPosition) VALUES ( '"
                    + doctor.getId()
                    +"' , '"+doctor.getName()
                    +"' , '"+doctor.getpwd()
                    +"' , '"+ 2
                    +"' , '"+doctor.getAge()
                    +"' , '"+doctor.getSex()
                    +"' , '"+ 0
                    +"' , '"+doctor.getRenown()
                    +"' , '"+doctor.getDept()
                    +"' ,'"+doctor.getPosition()+"')";
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

    public boolean update(Doctor doctor) {
        try
        {
            String sql = "UPDATE Doctor SET docName=?,docPwd=?,userType=?, age=?, sex=?, jobType=?, renown=?, dept=?, docPosition=? WHERE docId=?";
            System.out.println(sql);
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,  doctor.getName());
            stmt.setString(2,  doctor.getpwd());
            stmt.setInt(3, 2);
            stmt.setInt(4,  doctor.getAge());
            stmt.setInt(5,  doctor.getSex());
            stmt.setInt(6,  doctor.getJobType());
            stmt.setString(7,  doctor.getRenown());
            stmt.setString(8,  doctor.getDept());
            stmt.setString(9,  doctor.getPosition());
            stmt.setString(10,  doctor.getId());
            stmt.executeUpdate();

            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
