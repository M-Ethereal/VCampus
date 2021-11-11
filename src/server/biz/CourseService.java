package server.biz;

import server.dao.CourseDao;
import server.dao.GradeDao;
import server.exception.ConflictException;
import server.exception.OutOfLimitException;
import server.exception.RecordAlreadyExistException;
import server.exception.RecordNotFoundException;
import vo.Course;
import vo.Grade;
import vo.GradeTable;
import vo.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class CourseService {
    private CourseDao courseDao = new CourseDao();
    private GradeDao gradeDao = new GradeDao();

    //查询x学生x学期的所有课程，返回课程信息列表
    public ArrayList<Course> myTimeTable(Student student, String seme) throws RecordNotFoundException, SQLException {
        ArrayList<Course> myCourses = new ArrayList<Course>();
        ArrayList<Grade> myChoice = gradeDao.queryById(student.getId());

        for (int i = 0; i < myChoice.size(); i++)
        {
            Course temp = new Course();
            temp = courseDao.queryByCID(myChoice.get(i).getCourseID());
            if(temp.getCourseSemester().equals(seme)) myCourses.add(temp);
        }
        return myCourses;
    }

    //选课：更新grade表，同时要更新课程表中的选课人数
    public void courseChoose (Grade grade) throws ConflictException, RecordAlreadyExistException, OutOfLimitException {
        gradeDao.insert(grade);
        courseDao.updateChooseCourse(grade.getCourseID());
    }
    //退课：更新grade表，同时要更新课程表中的选课人数
    public void giveupChoose (Grade grade){
        gradeDao.delete(grade);
        courseDao.updateGiveUpCourse(grade.getCourseID());
    }


    //查询成绩，返回ArrayList<GradeTable>
    public ArrayList<GradeTable> queryGradeTable (String stuID) throws RecordNotFoundException, SQLException {
        ArrayList<Grade> myGrades = gradeDao.queryById(stuID);
        System.out.println(myGrades.get(0).getCourseName() + " " + myGrades.get(0).getGrade()+ " "+ myGrades.get(1).getCourseName() + " "+ myGrades.get(1).getGrade()+ " " + myGrades.get(3).getCourseName() +" "+ myGrades.get(3).getGrade() );
        ArrayList<GradeTable> gradeTables = new ArrayList<GradeTable>();

        for (int i = 0; i < myGrades.size(); i++){
            GradeTable gradeTable = new GradeTable();
            String CID = myGrades.get(i).getCourseID();
            Course courseTemp = courseDao.queryByCID(CID);
            System.out.println(courseTemp.getCourseLecturer());
            gradeTable.setCourseSemester(courseTemp.getCourseSemester());
            gradeTable.setCourseID(courseTemp.getCourseNumber());
            gradeTable.setCourseName(courseTemp.getCourseName());
            gradeTable.setCourseType(courseTemp.getCourseType());
            gradeTable.setCourseCredit(courseTemp.getCourseCredit());
            gradeTable.setGrade(myGrades.get(i).getGrade());
            gradeTable.setGradeType(myGrades.get(i).getGradeType());
            gradeTable.setGradeAdded(courseTemp.isGradeAdded());
            gradeTables.add(gradeTable);
        }

        return gradeTables;
    }

    //登成绩
    public void AddGrade(Grade grade) throws SQLException {
        Course course = courseDao.queryByCID(grade.getCourseID());
        course.setGradeAdded(true);
        courseDao.update(course);
        gradeDao.update(grade);
    }

}
