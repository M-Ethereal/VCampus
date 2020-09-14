package server.dao;

import vo.Doctor;
import vo.MedicalAdvice;
import vo.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalAdviceDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    public boolean insert(MedicalAdvice ma) throws SQLException {
        try
        {
            String sql = "INSERT INTO MedicalAdvice (qId, queryDpet, "
                    + "docId, docName, stuId, stuName"
                    +"qQuestion, qAnswer, queryDate, queryTime, ansDate, ansTime) VALUES ( '"
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

    public boolean update(MedicalAdvice ma) {
        try
        {
            String sql = "UPDATE MedicalAdvice SET qAnswer=?, ansDate=?, ansTime=? WHERE qID=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, ma.getqAnswer());
            stmt.setString(2, ma.getAnsDate());
            stmt.setString(3, ma.getAnsTime());
            stmt.setInt(4, ma.getqID());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public MedicalAdvice queryByDocId(String docId) throws SQLException {
        String sql= "SELECT * FROM MedicalAdvice where docId="+ "'"+ docId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicalAdvice();
        }
        return null;
    }

    public MedicalAdvice queryByStuId(String stuId) throws SQLException {
        String sql= "SELECT * FROM MedicalAdvice where stuId="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicalAdvice();
        }
        return null;
    }

    public MedicalAdvice queryRelevant(String docId, String stuId) throws SQLException {
        String sql= "SELECT * FROM MedicalAdvice where docId="+ "'"+ docId +"'" + "AND stuId="+ "'"+ stuId +"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToMedicalAdvice();
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
            return ma;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
