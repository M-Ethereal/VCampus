package server.dao;

import vo.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement sql = null;
    private Connection conn = access.connection;
    private ResultSet rs=null;
    public Student rsToStudent()
    {
        try
        {
            Student student = new Student();
            student.setUserType(0);
            student.setpwd(rs.getString("uPwd"));
            student.setId(rs.getString("uID"));
            student.setNumber(rs.getString("uNumber"));
            student.setName(rs.getString("uName"));
            student.setMajorId(rs.getInt("uMajor"));
            student.setGPA(rs.getDouble("uGPA"));
            student.setCredit(rs.getDouble("uCredit"));
            student.setSTRP(rs.getDouble("uSRTP"));
            student.setState(rs.getInt("uState"));
            student.setPhoneNmuber(rs.getString("PhoneNumber"));
            student.setLendBooksNum(rs.getInt("lendBooksNum"));
            student.setAge(rs.getInt("age"));
            student.setSex(rs.getInt("sex"));
            student.setRegister(rs.getString("uRegister"));
            student.setDormNum(rs.getString("dormNum"));
            student.setCardBalance(rs.getDouble("uMoney"));
            student.setCampusPosition(rs.getInt("campusPosition"));
            student.setPunishment(rs.getInt("punishmentType"));
            return student;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Student query(int uID )
    {
        Student star=new Student();
        try{
            String req = "select uName,uNumber,uGrade,uRegister,uCredit,uSex,PhoneNumber,uState from tbl_StudentArchive where uID = '"+uID+"'";
            sql = conn.prepareStatement(req);
            rs = sql.executeQuery(req);
            star=rsToStudent();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return star;
    }


    public void insert(String uName, String uNumber, int uID, String uRegister,int majorId,
                              double uGPA, double uCredit, int uSex, String  PhoneNumber, int uState,double uSRTP,int uAge,String DormNum,double uMoney,int uRenown,int campusPosition,int punishmentType)
    {
        try{
            sql=conn.prepareStatement("insert into tbl_StudentArchive(uID,uNumber,uName,uGPA,uCredit,uState,majorId,PhoneNumber,uSex,uRegister,uSRTP,uAge,DormNum,uMoney,uRenown,campusPosition,punishementType) values('"+uID+"','"+uNumber+"','"+uName+"','"+uGPA+"','"+uCredit+"','"+uState+"','"+majorId+"','"+PhoneNumber+"','"+uSex+"','"+uRegister+"','"+uSRTP+"','"+uAge+"','"+DormNum+"','"+uMoney+"','"+uRenown+"','"+campusPosition+"','"+punishmentType+"')");
            sql.executeUpdate();
            System.out.println("success!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateForTeacher(String uName, String uNumber, String uRegister,int uID,int majorId,
                              double uGPA, double uCredit, int uSex, String  PhoneNumber, int uState,double uSRTP,int uAge,String DormNum,double uMoney,int uRenown,int campusPosition,int punishmentType)
    {

        try{
            sql=conn.prepareStatement("update tbl_StudentArchive set majorID='"+majorId+"' ,  uNumber='"+uNumber+"' ,uName='"+uName+"',uRegister='"+uRegister+"',uGPA='"+uGPA+"',uCredit='"+uCredit+"',PhoneNumber='"+PhoneNumber+"',uState='"+uState+"', uSRTP='"+uSRTP+"',uAge='"+uAge+"',DormNum='"+DormNum+"',uMoney='"+uMoney+"',uRenown='"+uRenown+"', campusPosition='"+campusPosition+"',punishmentType='"+punishmentType+"'  where uID='"+uID+"' ");
            sql.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    
    public void updateForStudent(int uID,String  PhoneNumber,int majorId, int uAge,String DormNum, int campusPosition)
{

	try{
	sql=conn.prepareStatement("update tbl_StudentArchive set PhoneNumber='"+PhoneNumber+" ,majorId='"+majorId+"',DormNum='"+DormNum+"',campusPosition='"+campusPosition+"' where uID='"+uID+"' ");
	sql.executeUpdate();
	}catch (SQLException e)
	{
	e.printStackTrace();
	}
	
}

    public void delete(int uID)
    {
        try{
            sql=conn.prepareStatement("delete from tbl_StudentArchive where uID ='"+uID+"'");
            sql.executeUpdate();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
