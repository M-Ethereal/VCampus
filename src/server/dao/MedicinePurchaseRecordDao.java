package server.dao;

import vo.MedicinePurchaseRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicinePurchaseRecordDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(MedicinePurchaseRecord mp) throws SQLException {
        try
        {
            String sql = "INSERT INTO MedicinePurchaseRecord (purchaseId, purchaseStuId, purchaseMedId, "
                    + "purchaseDate, purchaseTime, purchaseCost, purchaseNum) VALUES ( '"
                    + mp.getPurchaseID()
                    +"' , '"+ mp.getPurchaseStuId()
                    +"' , '"+ mp.getPurchaseMedId()
                    +"' , '"+ mp.getPurchaseDate()
                    +"' , '"+ mp.getPurchaseTime()
                    +"' , '"+ mp.getPurchaseCost()
                    +"' , '"+ mp.getPurchaseNum()
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

    public ArrayList<MedicinePurchaseRecord> queryByStu(String stuId) throws SQLException {
        String sql= "SELECT * FROM MedicinePurchaseRecord where purchaseID="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicineList();
        }
        return null;
    }


    public ArrayList<MedicinePurchaseRecord> rsToMedicineList(){
        try {
            ArrayList<MedicinePurchaseRecord> list=new ArrayList<MedicinePurchaseRecord>();
            do {
                MedicinePurchaseRecord mp = new MedicinePurchaseRecord();
                mp.setPurchaseID(rs.getInt("purchaseID"));
                mp.setPurchaseStuId(rs.getString("purchaseStuId"));
                mp.setPurchaseMedId(rs.getString("purchaseMedId"));
                mp.setPurchaseDate(rs.getString("purchaseDate"));
                mp.setPurchaseTime(rs.getString("purchaseTime"));
                mp.setPurchaseCost(rs.getDouble("purchaseCost"));
                mp.setPurchaseNum(rs.getInt("purchaseNum"));
                list.add(mp);
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
