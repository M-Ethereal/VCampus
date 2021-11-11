package server.dao;

import vo.Place;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;


    public ArrayList<Place> queryByPos(String pos) throws SQLException {
        String sql= "SELECT * FROM Place where placePosition like '%"+ pos + "%'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToPlaceList();
        }
        return null;
    }

    public Place queryByName(String pName) throws SQLException {
        String sql= "SELECT * FROM Place where placeName= '"+ pName + "'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToPlace();
        }
        return null;
    }


    public Place rsToPlace()
    {
        try
        {
            Place place = new Place();
            place.setPlaceID(rs.getInt("placeID"));
            place.setPlaceName(rs.getString("placeName"));
            place.setPlacePosition(rs.getString("placePosition"));
            place.setpCampus(rs.getString("pCampus"));
            place.setPlaceType(rs.getInt("placeType"));
            place.setpMaximumAvailable(rs.getInt("pMaximumAvailable"));
            return place;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Place> rsToPlaceList(){
        try
        {
            ArrayList<Place> list = new ArrayList<Place>();
            do{
                Place place = new Place();
                place.setPlaceID(rs.getInt("placeID"));
                place.setPlaceName(rs.getString("placeName"));
                place.setPlacePosition(rs.getString("placePosition"));
                place.setpCampus(rs.getString("pCampus"));
                place.setPlaceType(rs.getInt("placeType"));
                place.setpMaximumAvailable(rs.getInt("pMaximumAvailable"));
                list.add(place);
            } while (rs.next());

            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
