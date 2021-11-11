package server.dao;

import vo.Place;
import vo.PlaceAppointmentRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceAppointmentRecordDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private PlaceDao placeDao = new PlaceDao();

    //预约
    public boolean insert(PlaceAppointmentRecord placeAppointmentRecord) throws SQLException {
        try
        {
            //Place place = placeDao.queryByName(placeAppointmentRecord.getPlaceName());

            String sql = "INSERT INTO PlaceAppointmentRecord (apDate, startTime, endTime, placeId, placeName, stuId, stuName"
                    +") VALUES ( '"
                    + placeAppointmentRecord.getStartDate()
                    +"' , '"+ placeAppointmentRecord.getStartTime()
                    +"' , '"+ placeAppointmentRecord.getEndTime()
                    +"' , '"+ placeAppointmentRecord.getPlaceId()
                    +"' , '"+ placeAppointmentRecord.getPlaceName()
                    +"' , '"+ placeAppointmentRecord.getStuId()
                    +"' , '"+ placeAppointmentRecord.getStuName()
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

    //取消预约
    public boolean delete(PlaceAppointmentRecord placeAppointmentRecord) {
        try{
            String sql = "DELETE * FROM PlaceAppointmentRecord WHERE apDate =? and startTime =? and endTime =? and placeName =? and stuId =?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, placeAppointmentRecord.getStartDate());
            stmt.setString(2, placeAppointmentRecord.getStartTime());
            stmt.setString(3, placeAppointmentRecord.getEndTime());
            stmt.setString(4, placeAppointmentRecord.getPlaceName());
            stmt.setString(5, placeAppointmentRecord.getStuId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   //按照学生id查询
    public ArrayList<PlaceAppointmentRecord> queryByStuId(String stuId) throws SQLException {
        String sql= "SELECT * FROM PlaceAppointmentRecord where stuId="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToPlaceAPList();
        }
        return null;
    }

    //按照场馆名查询
    public ArrayList<PlaceAppointmentRecord> queryByPlaceName(String placeName) throws SQLException {
        String sql= "SELECT * FROM PlaceAppointmentRecord where placeName="+ "'"+ placeName +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToPlaceAPList();
        }
        return null;
    }

    public ArrayList<PlaceAppointmentRecord> rsToPlaceAPList(){
        try
        {
            ArrayList<PlaceAppointmentRecord> list = new ArrayList<PlaceAppointmentRecord>();
            do{
                PlaceAppointmentRecord placeAppointmentRecord = new PlaceAppointmentRecord();
                placeAppointmentRecord.setStartDate(rs.getString("apDate"));
                placeAppointmentRecord.setStartTime(rs.getString("startTime"));
                placeAppointmentRecord.setEndTime(rs.getString("endTime"));
                placeAppointmentRecord.setPlaceId(rs.getString("placeId"));
                placeAppointmentRecord.setPlaceName(rs.getString("placeName"));
                placeAppointmentRecord.setStuId(rs.getString("stuId"));
                placeAppointmentRecord.setStuName(rs.getString("stuName"));
                list.add(placeAppointmentRecord);
            } while (rs.next());

            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
