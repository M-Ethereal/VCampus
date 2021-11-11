package server.dao;

import server.exception.RecordAlreadyExistException;
import vo.DoctorSchedule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorScheduleDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(DoctorSchedule ds) throws RecordAlreadyExistException{
        try
        {
            System.out.println(ds.getDocId() + " " +ds.getScheduleDate() + " " + ds.getScheduleInWeek() + " " + ds.getScheduleInDay());
            if(queryById(ds.getDocId()).size() != 0)
            {
                ArrayList<DoctorSchedule> temp = new ArrayList<DoctorSchedule>();
                temp = queryById(ds.getDocId());
                for (int i = 0; i < temp.size(); i++){
                    DoctorSchedule dsTemp = temp.get(i);
                    if(ds.getScheduleDate().equals(dsTemp.getScheduleDate()) && ds.getScheduleInWeek().equals(dsTemp.getScheduleInWeek()) && ds.getScheduleInDay().equals(dsTemp.getScheduleInDay())) throw new RecordAlreadyExistException();
                }
            }
            String sql = "INSERT INTO DoctorSchedule (docId, scheduleDate, scheduleInWeek, scheduleInDay) VALUES ( '"
                    + ds.getDocId()
                    +"' , '"+ ds.getScheduleDate()
                    +"' , '"+ ds.getScheduleInWeek()
                    +"' , '"+ ds.getScheduleInDay()
                    +"' )";
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch(RecordAlreadyExistException e)
        {
            System.out.println("已经有这条记录");
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(DoctorSchedule ds) {
        try{
            String sql = "DELETE * FROM DoctorSchedule WHERE docId =? and scheduleDate=? and scheduleInWeek=? and scheduleInDay=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, ds.getDocId());
            stmt.setString(2, ds.getScheduleDate());
            stmt.setString(3, ds.getScheduleInWeek());
            stmt.setString(4, ds.getScheduleInDay());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public ArrayList<DoctorSchedule> queryById(String docId) throws SQLException {
        String sql= "SELECT * FROM DoctorSchedule where docId="+ "'"+ docId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToDoctorScheduleList();
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


    public ArrayList<DoctorSchedule> rsToDoctorScheduleList()
    {
        try
        {
            ArrayList<DoctorSchedule> list = new ArrayList<DoctorSchedule>();
            do{
                DoctorSchedule ds = new DoctorSchedule();
                ds.setsID(rs.getInt("sID"));
                ds.setDocId(rs.getString("docId"));
                ds.setScheduleDate(rs.getString("scheduleDate"));
                ds.setScheduleInWeek(rs.getString("scheduleInWeek"));
                ds.setScheduleInDay(rs.getString("scheduleInDay"));
                list.add(ds);
            } while (rs.next());

            return list;

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
