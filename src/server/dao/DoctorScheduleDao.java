package server.dao;

import vo.DoctorSchedule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorScheduleDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(DoctorSchedule ds) throws SQLException {
        try
        {
            String sql = "INSERT INTO DoctorSchedule (sId, docId, scheduleDate, scheduleInWeek, scheduleInDay"
                    + ds.getsID()
                    +"' , '"+ ds.getDocId()
                    +"' , '"+ ds.getScheduleDate()
                    +"' , '"+ ds.getScheduleInWeek()
                    +"' , '"+ ds.getScheduleInDay()
                    +" )";
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

//    public boolean delete(DoctorSchedule ds) {
//        try{
//            String sql = "DELETE * FROM DoctorSchedule WHERE docId =?";
//            stmt = access.connection.prepareStatement(sql);
//            stmt.setString(1, admin.getId());
//            stmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean update(DoctorSchedule ds) {
        try
        {
            String sql = "UPDATE DoctorSchedule SET scheduleInDay=?"
                    + "WHERE docID=? AND scheduleDate=? AND scheduleInWeek=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, ds.getScheduleInDay());
            stmt.setString(2, ds.getDocId());
            stmt.setString(3, ds.getScheduleDate());
            stmt.setString(4, ds.getScheduleInWeek());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public DoctorSchedule queryById(String docId) throws SQLException {
        String sql= "SELECT * FROM DoctorSchedule where docId="+ "'"+ docId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToDoctorSchedule();
        }
        return null;
    }


    public DoctorSchedule rsToDoctorSchedule()
    {
        try
        {
            DoctorSchedule ds = new DoctorSchedule();
            ds.setsID(rs.getInt("sID"));
            ds.setDocId(rs.getString("docId"));
            ds.setScheduleDate(rs.getString("scheduleDate"));
            ds.setScheduleInWeek(rs.getString("scheduleInWeek"));
            ds.setScheduleInDay(rs.getString("scheduleInDay"));
            return ds;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
