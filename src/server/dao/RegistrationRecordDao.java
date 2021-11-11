package server.dao;

import vo.RegistrationRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationRecordDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(RegistrationRecord rr) throws SQLException {
        try
        {
            String sql = "INSERT INTO RegistrationRecord (apID, apDate, apTime, docID, docName, stuId, stuName, "
                    +"apCost, apDept, apType) VALUES ( '"
                    + rr.getApID()
                    +"' , '"+ rr.getApDate()
                    +"' , '"+ rr.getApTime()
                    +"' , '"+ rr.getDocId()
                    +"' , '"+ rr.getDocName()
                    +"' , '"+ rr.getStuId()
                    +"' , '"+ rr.getStuName()
                    +"' , '"+ rr.getApCost()
                    +"' , '"+ rr.getApDept()
                    +"' , '"+ rr.getApType()
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

    public ArrayList<RegistrationRecord> queryByDocId(String docId) throws SQLException {
        String sql= "SELECT * FROM RegistrationRecord where docId="+ "'"+ docId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToRegistrationRecordList();
        }
        return null;
    }

    public ArrayList<RegistrationRecord> queryByStuId(String stuId) throws SQLException {
        String sql= "SELECT * FROM RegistrationRecord where stuId="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToRegistrationRecordList();
        }
        return null;
    }


    public ArrayList<RegistrationRecord> rsToRegistrationRecordList(){
        try {
            ArrayList<RegistrationRecord> list = new ArrayList<RegistrationRecord>();
            do {
                RegistrationRecord rr = new RegistrationRecord();
                rr.setApID(rs.getInt("apID"));
                rr.setApDate(rs.getString("apDate"));
                rr.setApTime(rs.getString("apTime"));
                rr.setDocId(rs.getString("docId"));
                rr.setDocName(rs.getString("docName"));
                rr.setStuId(rs.getString("stuId"));
                rr.setStuName(rs.getString("stuName"));
                rr.setApCost(rs.getDouble("apCost"));
                rr.setApDept(rs.getString("apDept"));
                rr.setApType(rs.getString("apType"));
                list.add(rr);
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
