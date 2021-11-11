package server.dao;

import vo.Doctor;
import vo.MedicalAdvice;
import vo.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicalAdviceDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(MedicalAdvice ma) throws SQLException {
        try
        {
            String sql = "INSERT INTO MedicalAdvice (qId, queryDept, "
                    + "docId, docName, stuId, stuName, "
                    +"qQuestion, qAnswer, queryDate, queryTime, ansDate, ansTime, isRank) VALUES ( '"
                    + ma.getqID()
                    +"' , '"+ ma.getQueryDept()
                    +"' , '"+ ma.getDocId()
                    +"' , '"+ ma.getDocName()
                    +"' , '"+ ma.getStuId()
                    +"' , '"+ ma.getStuName()
                    +"' , '"+ ma.getqQuestion()
                    +"' , '"+ ma.getqAnswer()
                    +"' , '"+ ma.getQueryDate()
                    +"' , '"+ ma.getQueryTime()
                    +"' , '"+ ma.getAnsDate()
                    +"' , '"+ ma.getAnsTime()
                    +"' , '"+ ma.getRank()
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

    //医生的回答
    public boolean update(MedicalAdvice ma) {
        try
        {
            String sql = "UPDATE MedicalAdvice SET qAnswer=?, ansDate=?, ansTime=? ,isRank=? WHERE qID=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, ma.getqAnswer());
            stmt.setString(2, ma.getAnsDate());
            stmt.setString(3, ma.getAnsTime());
            stmt.setBoolean(4, false);
            stmt.setInt(5, ma.getqID());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<MedicalAdvice> queryByDocId(String docId) throws SQLException {
        String sql= "SELECT * FROM MedicalAdvice where docId="+ "'"+ docId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicalAdvicesList();
        }
        return null;
    }

    public ArrayList<MedicalAdvice> queryByStuId(String stuId) throws SQLException {
        String sql= "SELECT * FROM MedicalAdvice where stuId="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicalAdvicesList();
        }
        return null;
    }

    public ArrayList<MedicalAdvice> queryRelevant(String docId, String stuId) throws SQLException {
        String sql= "SELECT * FROM MedicalAdvice where docId="+ "'"+ docId +"'" + "AND stuId="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicalAdvicesList();
        }
        return null;
    }


    public MedicalAdvice rsToMedicalAdvice()
    {
        try
        {
            MedicalAdvice ma = new MedicalAdvice();
            ma.setqID(rs.getInt("qID"));
            ma.setQueryDept(rs.getString("queryDept"));
            ma.setDocId(rs.getString("docId"));
            ma.setDocName(rs.getString("docName"));
            ma.setStuId(rs.getString("stuId"));
            ma.setStuName(rs.getString("stuName"));
            ma.setqQuestion(rs.getString("qQuestion"));
            ma.setqAnswer(rs.getString("qAnswer"));
            ma.setQueryDate(rs.getString("queryDate"));
            ma.setQueryTime(rs.getString("queryTime"));
            ma.setAnsDate(rs.getString("ansDate"));
            ma.setAnsTime(rs.getString("ansTime"));
            ma.setRank(rs.getBoolean("isRank"));
            return ma;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<MedicalAdvice> rsToMedicalAdvicesList(){
        try {
            ArrayList<MedicalAdvice> list = new ArrayList<MedicalAdvice>();
            do{
                MedicalAdvice ma = new MedicalAdvice();
                ma.setqID(rs.getInt("qID"));
                ma.setQueryDept(rs.getString("queryDept"));
                ma.setDocId(rs.getString("docId"));
                ma.setDocName(rs.getString("docName"));
                ma.setStuId(rs.getString("stuId"));
                ma.setStuName(rs.getString("stuName"));
                ma.setqQuestion(rs.getString("qQuestion"));
                ma.setqAnswer(rs.getString("qAnswer"));
                ma.setQueryDate(rs.getString("queryDate"));
                ma.setQueryTime(rs.getString("queryTime"));
                ma.setAnsDate(rs.getString("ansDate"));
                ma.setAnsTime(rs.getString("ansTime"));
                ma.setRank(rs.getBoolean("isRank"));
                list.add(ma);

            } while (rs.next());
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
