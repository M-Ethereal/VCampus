package server.dao;

import vo.Medicine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(Medicine medicine) throws SQLException {
        try
        {
            String sql = "INSERT INTO Medicine (mID, mName, mPrice, mTag, mSpecification, mStore, mIntroduction"
                    +"VALUES ( '"
                    + medicine.getmID()
                    +"' , '"+ medicine.getmName()
                    +"' , '"+ medicine.getmPrice()
                    +"' , '"+ medicine.getmTag()
                    +"' , '"+ medicine.getmSpecification()
                    +"' , '"+ medicine.getmStore()
                    +"' , '"+ medicine.getmIntroduction()
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

    public boolean delete(Medicine medicine) {
        try{
            String sql = "DELETE * FROM Medicine WHERE mID =?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setInt(1, medicine.getmID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Medicine medicine) {
        try
        {
            String sql = "UPDATE Medicine SET mName=?, mPrice=?, mTag=?, mSpecification=?, mStore=?, mIntroduction=?,"
                    + "WHERE mID=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, medicine.getmName());
            stmt.setDouble(2, medicine.getmPrice());
            stmt.setString(3, medicine.getmTag());
            stmt.setString(4, medicine.getmSpecification());
            stmt.setInt(5, medicine.getmStore());
            stmt.setString(6, medicine.getmIntroduction());
            stmt.setInt(7, medicine.getmID());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Medicine> queryById(String mID) throws SQLException {
        String sql= "SELECT * FROM Medicine where mID="+ "'"+ mID +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicineList();
        }
        return null;
    }

    public ArrayList<Medicine> queryAll() throws SQLException {
        String sql= "SELECT * FROM Medicine";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicineList();
        }
        return null;
    }


    public ArrayList<Medicine> rsToMedicineList(){
        try {
            ArrayList<Medicine> list=new ArrayList<Medicine>();
            do {
                Medicine m = new Medicine();
                m.setmID(rs.getInt("mID"));
                m.setmName(rs.getString("mName"));
                m.setmPrice(rs.getDouble("mPrice"));
                m.setmTag(rs.getString("mTag"));
                m.setmSpecification(rs.getString("mSpecification"));
                m.setmStore(rs.getInt("mStore"));
                m.setmIntroduction(rs.getString("mIntroduction"));
                list.add(m);
            }while(rs.next());
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
