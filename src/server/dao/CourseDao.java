package server.dao;

import vo.Course;
import vo.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDao {
    private DbHelper access = new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;


    

    private ArrayList<Course> rsToCoursesList()
    {
        try{
            ArrayList<Course> cs = new ArrayList<Course>();
            while(rs.next()) {
                String courseNumber = rs.getString("courseNumber");
                String courseName = rs.getString("courseName");
                String courseLecturer = rs.getString("courseLecturer");
                String courseSemester = rs.getString("courseSemester");
                String coursePlace = rs.getString("coursePlace");
                String courseTime = rs.getString("courseTime");
                String courseType = rs.getString("courseType");
                String courseCredit = rs.getString("courseCredit");
                int maxStuds = rs.getInt("maximumStudents");
                int erdStuds = rs.getInt("enrolledStudents");
                boolean isExam = rs.getBoolean("isExam");
                Course c = new Course(courseNumber,courseName,courseSemester,courseLecturer,coursePlace,courseTime,
                        courseCredit,courseType,maxStuds,erdStuds,isExam);
                boolean gradeAdded = rs.getBoolean("gradeAdded");
                c.setGradeAdded(gradeAdded);
                cs.add(c);
                return cs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
