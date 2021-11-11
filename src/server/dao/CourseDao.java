package server.dao;

import server.exception.ClassroomConflictException;
import server.exception.ConflictException;
import server.exception.RecordNotFoundException;
import vo.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;


    public ArrayList<Course> queryThisTermCourseList(String stuNum, String time) throws SQLException// 09018  19-20-1
    {
        String str = stuNum.substring(0,5);
        String sql = "select * from Course where courseNumber like '%"+ str + "%" + time +"%'or courseNumber like '%00000%" + time + "%'";
        System.out.println(sql);
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToCoursesList();
        }
        return null;
    }

    public ArrayList<Course> queryThisTermCourseList_true(String seme) throws SQLException//  19-20-1
    {
        String sql = "select * from Course where courseSemester =" +  "'"+ seme +"'";
        System.out.println(sql);
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToCoursesList();
        }
        return null;
    }

    //按照课程号查询，返回对应的课程信息
    public Course queryByCID(String courseID) throws SQLException// 09017  19-20-1
    {
        String sql = "select * from Course where courseNumber ="+"'"+courseID+"'";
        System.out.println(sql);
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToCourse();
        }
        return null;
    }

    //选课的时候要改
    public boolean updateChooseCourse( String CourseNumber ) {
        try
        {
            Course courseTemp = queryByCID(CourseNumber);
            Integer newES = courseTemp.getEnrolledStudents()+1;
            String sql = "UPDATE Course SET enrolledStudents=?"
                    + "WHERE CourseNumber=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setInt(1, newES);
            stmt.setString(2, CourseNumber);

            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    //退课的时候要改
    public boolean updateGiveUpCourse( String CourseNumber ) {
        try
        {
            Course courseTemp = queryByCID(CourseNumber);
            Integer newES = courseTemp.getEnrolledStudents()-1;
            String sql = "UPDATE Course SET enrolledStudents=?"
                    + "WHERE CourseNumber=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setInt(1, newES);
            stmt.setString(2, CourseNumber);

            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }



//****************************************teacher老师**********************************************

    //查老师课表
    public ArrayList<Course> queryThisTermCourseList_Teacher(String teacherName, String time) throws RecordNotFoundException, SQLException// 09018  19-20-1
    {
        String sql = "select * from Course where courseNumber like '%"+ time +"%' and courseLecturer='" + teacherName + "'";
        System.out.println(sql);
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToCoursesList();
        }
        else throw new RecordNotFoundException();
    }

//****************************************admin教务**********************************************

    //加一门课：
    //
    // 冲突逻辑：
    // 1 查询所需学期中的所有课(course查学期)。如果与某门课同一时间上课，不能在同一间教室
    // 2 （上述list，用所有的courseid查course表生成一个courselist），查新增课的任课老师，如果不在其中，则直接通过；
    // 若在其中，则对老师执行学生的选课逻辑：同一个老师不能在同一时间上两门课
    public Boolean insert(Course courseToBeInserted) throws ClassroomConflictException, ConflictException, SQLException {//返回冲突课程
        try
        {
            String seme = courseToBeInserted.getCourseSemester();
            ArrayList<Course> coursesThisTerm = queryThisTermCourseList_true(seme);

            //1
            //判断与某门课上课时间冲突：
            //学期错开：一定不冲突，跳过
            //else 查每周上课时间，如果有相同字段，则冲突，进入下一级判断
            //如果教室相同，则教室冲突

            for (int i=0; i < coursesThisTerm.size(); i++){

                String[] term_old = coursesThisTerm.get(i).getCourseTerm().split("-");
                String[] term_new = courseToBeInserted.getCourseTerm().split("-");
                if (Integer.valueOf(term_old[1]) < Integer.valueOf(term_new[0]) && Integer.valueOf(term_old[0]) > Integer.valueOf(term_new[1])) continue;
                else {
                    String[] str_old = coursesThisTerm.get(i).getCourseTime().split(";");
                    String[] str_new = courseToBeInserted.getCourseTime().split(";");
                    //查相同字段
                    for (int a = 0; a<str_old.length; a++)
                    {
                        for (int b = 0; b<str_new.length; b++)
                        {
                            if (str_old[a].equals(str_new[b])) {
                                if (coursesThisTerm.get(i).getCoursePlace().equals(courseToBeInserted.getCoursePlace())) throw new ClassroomConflictException();
                                if (coursesThisTerm.get(i).getCourseLecturer().equals(courseToBeInserted.getCourseLecturer())) throw new ConflictException();

                            }
                        }
                    }
                }
            }


            String sql = "INSERT INTO Course (courseNumber, courseName, courseSemester, courseLecturer, coursePlace, courseTerm, courseTime, maximumStudents, enrolledStudents, courseType, courseCredit, examTime, examPlace, isExam, gradeAdded) VALUES ( '"
                    + courseToBeInserted.getCourseNumber()
                    +"' , '"+ courseToBeInserted.getCourseName()
                    +"' , '"+ courseToBeInserted.getCourseSemester()
                    +"' , '"+ courseToBeInserted.getCourseLecturer()
                    +"' , '"+ courseToBeInserted.getCoursePlace()
                    +"' , '"+ courseToBeInserted.getCourseTerm()
                    +"' , '"+ courseToBeInserted.getCourseTime()
                    +"' , '"+ courseToBeInserted.getMaximumStudents()
                    +"' , '"+ courseToBeInserted.getEnrolledStudents()
                    +"' , '"+ courseToBeInserted.getCourseType()
                    +"' , '"+ courseToBeInserted.getCourseCredit()
                    +"' , '"+ courseToBeInserted.getExamTime()
                    +"' , '"+ courseToBeInserted.getExamPlace()
                    +"' , '"+ courseToBeInserted.isExam()
                    +"' , '"+ courseToBeInserted.isGradeAdded()
                    +"' )";
            System.out.println(sql);
            stmt = access.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
//        catch(ClassroomConflictException | ConflictException | SQLException e)
//        {
//            e.printStackTrace();
//        }
        finally {

        }
        return null;
    }


    //发布考试
    public boolean update(Course course) {
        try
        {
            String sql = "UPDATE Course SET courseName=?,courseSemester=?,courseLecturer=?, coursePlace=?,courseTerm=?,courseTime=?,"
                    + "maximumStudents=?,enrolledStudents=?,courseType=?,"
                    + "courseCredit=?, examTime=?, examPlace=?, isExam=?, gradeAdded=? "
                    + "WHERE CourseNumber=?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseSemester());
            stmt.setString(3, course.getCourseLecturer());
            stmt.setString(4, course.getCoursePlace());
            stmt.setString(5, course.getCourseTerm());
            stmt.setString(6, course.getCourseTime());
            stmt.setInt(7, course.getMaximumStudents());
            stmt.setInt(8, course.getEnrolledStudents());
            stmt.setString(9, course.getCourseType());
            stmt.setString(10, course.getCourseCredit());
            stmt.setString(11, course.getExamTime());
            stmt.setString(12, course.getExamPlace());
            stmt.setBoolean(13, course.isExam());
            stmt.setBoolean(14, course.isGradeAdded());
            stmt.setString(15, course.getCourseNumber());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }



    private Course rsToCourse()
    {
        try {
            Course course = new Course();
            course.setCourseNumber(rs.getString("courseNumber"));
            course.setCourseName(rs.getString("courseName"));
            course.setCourseSemester(rs.getString("courseSemester"));
            course.setCourseLecturer(rs.getString("courseLecturer"));
            course.setCoursePlace(rs.getString("coursePlace"));
            course.setCourseTerm(rs.getString("courseTerm"));
            course.setCourseTime(rs.getString("courseTime"));
            course.setCourseCredit(rs.getString("courseCredit"));
            course.setCourseType(rs.getString("courseType"));
            course.setMaximumStudents(rs.getInt("maximumStudents"));
            course.setEnrolledStudents(rs.getInt("enrolledStudents"));
            course.setExam(rs.getBoolean("isExam"));
            course.setGradeAdded(rs.getBoolean("gradeAdded"));
            course.setExamTime(rs.getString("examTime"));
            course.setExamPlace(rs.getString("examPlace"));
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Course> rsToCoursesList()
    {
        try
        {
            ArrayList<Course> list = new ArrayList<Course>();
            do{
                Course course = new Course();
                course.setCourseNumber(rs.getString("courseNumber"));
                course.setCourseName(rs.getString("courseName"));
                course.setCourseSemester(rs.getString("courseSemester"));
                course.setCourseLecturer(rs.getString("courseLecturer"));
                course.setCoursePlace(rs.getString("coursePlace"));
                course.setCourseTerm(rs.getString("courseTerm"));
                course.setCourseTime(rs.getString("courseTime"));
                course.setCourseCredit(rs.getString("courseCredit"));
                course.setCourseType(rs.getString("courseType"));
                course.setMaximumStudents(rs.getInt("maximumStudents"));
                course.setEnrolledStudents(rs.getInt("enrolledStudents"));
                course.setExam(rs.getBoolean("isExam"));
                course.setGradeAdded(rs.getBoolean("gradeAdded"));
                course.setExamTime(rs.getString("examTime"));
                course.setExamPlace(rs.getString("examPlace"));
                list.add(course);
            } while (rs.next());
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
