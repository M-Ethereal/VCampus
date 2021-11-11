package server.dao;

import server.exception.ConflictException;
import server.exception.OutOfLimitException;
import server.exception.RecordAlreadyExistException;
import server.exception.RecordNotFoundException;
import vo.Course;
import vo.Grade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private CourseDao cd = new CourseDao();

    //选课函数
    public boolean insert(Grade grade) throws RecordAlreadyExistException, OutOfLimitException, ConflictException {
        try
        {
            //查满课
            Course courseTemp = cd.queryByCID(grade.getCourseID());
            if (courseTemp.getEnrolledStudents() >= courseTemp.getMaximumStudents()) throw new OutOfLimitException();

            //查重选
            ArrayList<Grade> c = queryById(grade.getStuID());
            if(c!=null)//查重：有这门课，且有分数（也就是说不是重修
            {
                for(int i=0;i<c.size();i++) {
                    if(c.get(i).getCourseID()== grade.getCourseID() && c.get(i).getGrade()!=-1) throw new RecordAlreadyExistException();
                }
            }

            //冲突逻辑：对grade表中的所有cID查询course表，生成一个已有课程list;用grade比对，如果学期一致，则比较：若courseTime中有相同字段 且两门课的courseTerm不存在互相包含的关系
            //Boolean conflict_flag = false;
            ArrayList<Course> courseAlreadyHave = new ArrayList<Course>();
            if(c!=null)
            {
                for(int i=0;i<c.size();i++) {
                    courseAlreadyHave.add(cd.queryByCID(c.get(i).getCourseID()));
                }
            }
            Course courseToBeSelected = cd.queryByCID(grade.getCourseID());
            for(int j = 0; j<courseAlreadyHave.size(); j++){
                if (courseAlreadyHave.get(j).getCourseSemester().equals(courseToBeSelected.getCourseSemester()))
                {
                    String[] term_old = courseAlreadyHave.get(j).getCourseTerm().split("-");
                    String[] term_new = courseToBeSelected.getCourseTerm().split("-");
                    if (Integer.valueOf(term_old[1]) < Integer.valueOf(term_new[0]) && Integer.valueOf(term_old[0]) > Integer.valueOf(term_new[1])) break;
                    else
                    {
                        String[] str_old = courseAlreadyHave.get(j).getCourseTime().split(";");
                        String[] str_new = courseToBeSelected.getCourseTime().split(";");
                        for (int a = 0; a<str_old.length; a++)
                        {
                            for (int b = 0; b<str_new.length; b++)
                            {
                                if (str_old[a].equals(str_new[b])) throw new ConflictException();
                            }
                        }
                    }
                }
            }

            System.out.println("GradeDao:" + grade.getStuID() + grade.getCourseID() + grade.getStuName() + grade.getCourseName() + grade.getGrade());

//            String sql = "INSERT INTO Grade (stuID, courseID, stuName, courseName, grade) VALUES ( '"
//                    + grade.getStuID()
//                    +"' , '"+ grade.getCourseID()
//                    +"' , '"+ grade.getStuName()
//                    +"' , '"+ grade.getCourseName()
//                    +"' , '"+ grade.getGrade()
//                    +"' )";
            String sql = "INSERT INTO Grade VALUES ( '"
                    + grade.getStuID()
                    +"' , '"+ grade.getCourseID()
                    +"' , '"+ grade.getStuName()
                    +"' , '"+ grade.getCourseName()
                    +"' , '"+ grade.getGrade()
                    +"' , '"+ grade.getGradeType()
                    +"' )";
            System.out.println(sql);
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
            return true;
        }
        catch(RecordNotFoundException | OutOfLimitException | SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    //退课函数
    public boolean delete(Grade grade) {
        try{
            System.out.println("dao:"+grade.getStuID() + grade.getCourseID());
            String sql = "DELETE * FROM Grade WHERE stuID =? and courseID =?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, grade.getStuID());
            stmt.setString(2, grade.getCourseID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //登成绩
    public boolean update(Grade grade) {
        try
        {
            String sql = "UPDATE Grade SET stuName=?, courseName=?, grade=?, gradeType=? "
                    + "WHERE stuID=? and courseID=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, grade.getStuName());
            stmt.setString(2, grade.getCourseName());
            stmt.setInt(3, grade.getGrade());
            stmt.setInt( 4,grade.getGradeType());
            stmt.setString(5, grade.getStuID());
            stmt.setString(6, grade.getCourseID());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    //查选课，查成绩
    public ArrayList<Grade> queryById(String stuID) throws RecordNotFoundException {
        try{
            String sql= "SELECT * FROM Grade where stuID="+ "'"+ stuID +"'";
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next())
            {
                return rsToGradeList();
            }
            else throw new RecordNotFoundException();
        }
        catch (RecordNotFoundException | SQLException e)
        {
        }
        return null;
    }

    //老师-选了该门课的有哪些学生
    public ArrayList<Grade> queryByCourseID(String courseID) throws RecordNotFoundException {
        try{
            String sql= "SELECT * FROM Grade where courseID="+ "'"+ courseID +"'";
            System.out.println(sql);
            stmt = access.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next())
            {
                return rsToGradeList();
            }
            else throw new RecordNotFoundException();
        }
        catch (RecordNotFoundException | SQLException e)
        {
        }
        return null;
    }


    public Grade rsToGrade()
    {
        try
        {
            Grade grade = new Grade();
            grade.setStuID(rs.getString("stuID"));
            grade.setCourseID(rs.getString("courseID"));
            grade.setStuName(rs.getString("stuName"));
            grade.setCourseName(rs.getString("courseName"));
            grade.setGrade(rs.getInt("grade"));
            grade.setGradeType(rs.getInt("gradeType"));
            return grade;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Grade> rsToGradeList() throws SQLException {
        ArrayList<Grade>list = new ArrayList<Grade>();
        try{
            do{
                Grade grade = new Grade();
                grade.setStuID(rs.getString("stuID"));
                grade.setCourseID(rs.getString("courseID"));
                grade.setStuName(rs.getString("stuName"));
                grade.setCourseName(rs.getString("courseName"));
                grade.setGrade(rs.getInt("grade"));
                grade.setGradeType(rs.getInt("gradeType"));
                list.add(grade);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String[] args) throws ConflictException, RecordAlreadyExistException {
//        GradeDao gradeDao = new GradeDao();
//        Grade grade = new Grade();
//        grade.setStuID("09018210");
//        grade.setStuName("周魔龙");
//        grade.setCourseID("09018110-20-21-2");
//        grade.setCourseName("计算机图形学");
//        grade.setGrade(-1);
//        gradeDao.insert(grade);
//    }
}
