package server.dao;

import vo.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
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
            student.setAge(rs.getInt("uAge"));
            student.setSex(rs.getInt("uSex"));
            student.setRegister(rs.getString("uRegister"));
            student.setRenown(rs.getString("uRenown"));
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

    public ArrayList<Student> rsToStudentList()
    {
        try
        {
            ArrayList<Student> list = new ArrayList<Student>();
            do{
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
                student.setAge(rs.getInt("uAge"));
                student.setSex(rs.getInt("uSex"));
                student.setRenown(rs.getString("uRenown"));
                student.setRegister(rs.getString("uRegister"));
                student.setDormNum(rs.getString("dormNum"));
                student.setCardBalance(rs.getDouble("uMoney"));
                student.setCampusPosition(rs.getInt("campusPosition"));
                student.setPunishment(rs.getInt("punishmentType"));
                list.add(student);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Student> queryAll( )
    {
        ArrayList<Student> list = null;
        try{
            String sql = "select * from Student";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs == null)System.out.print("null");

            if(rs.next())
            {
                System.out.print("1");
                return rsToStudentList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Student query(String uID )
    {
        //Student star=new Student();
        try{
            String sql = "select * from Student where uID = '" + uID +"'";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next())
            {
                return rsToStudent();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Student> queryByMajor(Student ss )
    {
        Student star=new Student();
        try{
            String sql = "select * from Student where uMajor = '"+ss.getMajorId()+"'";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next()){
                return rsToStudentList();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }




    public void insert(Student ss)
    {
        try{
            String sql = "insert into Student(uID,uNumber,uName,uGPA,uCredit,uState," +
                    "uMajor,PhoneNumber,uSex,uRegister,uSRTP,uAge,dormNum,uMoney,uRenown,campusPosition,punishmentType,lendBooksNum,uPwd,userType) " +
                    "values('"+ss.getId()+"','"+ss.getNumber()+"','"+ss.getName()+"','"+ss.getGPA()+"','"+ss.getCredit()+"','"+ss.getState()+"','"+ss.getMajorId()+"','"+ss.getPhoneNmuber()+"','"+ss.getSex()+"','"+
                    ss.getRegister()+"','"+ss.getSTRP()+"','"+ss.getAge()+"','"+ss.getDormNum()+"','"+ss.getCardBalance()+"','"+ss.getRenown()+"','"+ss.getCampusPosition()+"','"+ss.getPunishment()+"','"+ss.getLendBooksNum()+"','"+ss.getpwd()+"','"+ss.getUserType()+"')";
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void update(Student ss)
    {
        try{
            String sql = "update Student set uMajor='"+ss.getMajorId()+"' ,lendBooksNum='"+ss.getLendBooksNum()+"' , uNumber='"+ss.getNumber()+"' ,uName='"+ss.getName()+"',uRegister='"+ss.getRegister()+"',uGPA='"+ss.getGPA()+"',uCredit='"+ss.getCredit()+"',PhoneNumber='"+ss.getPhoneNmuber()+
                    "',uState='"+ss.getState()+"', uSRTP='"+ss.getSTRP()+"',uAge='"+ss.getAge()+"',dormNum='"+ss.getDormNum()+"',uMoney='"+ss.getCardBalance()+"',uRenown='"+ss.getRenown()+"', campusPosition='"+ss.getCampusPosition()+"',punishmentType='"+ss.getPunishment()+
                    "'  where uID='"+ss.getId()+"' ";
            System.out.println(sql);
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



    public void delete(String uID)
    {
        try{
            String sql = "delete from Student where uID ='"+uID+"'";
            //sql.executeUpdate();
            stmt = access.connection.prepareStatement(sql);
            // stmt.setString(1, uID);
            stmt.executeUpdate();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args)
    {
    	StudentDao sd=new StudentDao();
    	ArrayList<Student> list=sd.queryAll();
    	System.out.print(list.size());
    }*/

}
