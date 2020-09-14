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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Select one specific course by modifying the student count of this course and adding this course to the selected
     * courses of the specific student who wants to select the course now.
     * @param course Course message object. Should contain the course information and the ECard number.
     */
    public void selectCourse(Course course) {
        boolean conflict = false;

        try {
            stmt = access.connection.prepareStatement("select courseTime, courseSemester from Courses where courseNumber = ?");
            stmt.setString(1, course.getCourseNumber());
            ResultSet timeInfoRes = stmt.executeQuery();
            if (timeInfoRes.next()) {
                course.setCourseTime(timeInfoRes.getString("courseTime"));
                course.setCourseSemester(timeInfoRes.getString("courseSemester"));
            }

            Person per = new Person();
            per.setECardNumber(course.getECardNumber());
            getCoursesSelected(per);
            ArrayList<Course> coursesSelected = per.getCourses();

            for (Course c : coursesSelected
            ) {
                if (isConflicted(c, course)) {
                    conflict = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conflict = true;
        }

        //Proceed only if no conflict happens.
        if(!conflict) {
            try {
                String sql = "select * from Courses where courseNumber = ?";
                stmt = access.connection.prepareStatement(sql);
                stmt.setString(1, course.getCourseNumber());
                ResultSet courseRes = stmt.executeQuery();

                if (courseRes.next()) {
                    if ((courseRes.getInt("maximumStudents") -
                            courseRes.getInt("enrolledStudents")) > 0) {
                        try {
                            sql = "insert into CoursesSelected values(?,?,?)";
                            stmt = access.connection.prepareStatement(sql);
                            stmt.setString(1, course.getECardNumber());
                            stmt.setString(2, course.getCourseNumber());
                            stmt.setInt(3,-1);
                            stmt.executeUpdate();

                            sql = "update Courses set enrolledStudents = ? where courseNumber = ?";
                            stmt = access.connection.prepareStatement(sql);
                            stmt.setInt(1, courseRes.getInt("enrolledStudents") + 1);
                            stmt.setString(2, course.getCourseNumber());
                            stmt.executeUpdate();
                            course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
                        }
                    } else {
                        course.setType(Message.MESSAGE_TYPE.TYPE_COURSE_STUDENTS_FULL);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
            }
        }
        else {
            course.setType(Message.MESSAGE_TYPE.TYPE_COURSE_CONFLICT);
        }
    }

    /**
     * Delete a course selected by the student.
     * @param course Course Object that contains at least ECardNumber and CourseNumber.
     */
    public void deselectCourse(Course course) {
        try {
            String sql = "delete from CoursesSelected where courseNumber = ? and ECardNumber = ?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,course.getCourseNumber());
            stmt.setString(2,course.getECardNumber());
            stmt.executeUpdate();

            sql = "select * from Courses where courseNumber = ?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1, course.getCourseNumber());
            ResultSet courseRes = stmt.executeQuery();
            if(courseRes.next()) {
                sql = "update Courses set enrolledStudents = ? where courseNumber = ?";
                int num = courseRes.getInt("enrolledStudents") - 1;
                stmt = access.connection.prepareStatement(sql);
                stmt.setInt(1, num);
                stmt.setString(2, course.getCourseNumber());
                stmt.executeUpdate();
                course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /**
     * Get all the courses taken by designated student at some semester.
     * @param student Person, namely the student.
     * @param semester Specify which semester to be retrieved.
     */
    public void getCoursesSelected(Person student, String semester) {
        String sql = "select * from Courses where exists (select * from CoursesSelected, Users where " +
                "Courses.courseNumber = CoursesSelected.courseNumber and CoursesSelected.ECardNumber " +
                "= Users.ECardNumber and Users.ECardNumber = ?) and Courses.courseSemester = ?";
        setStudentCoursesList(sql,student,semester);
    }

    /**
     * Get all the courses taken by designated student.
     * @param student Person, namely the student.
     */
    public void getCoursesSelected(Person student) {
        String sql = "select * from Courses where exists (select * from CoursesSelected, Users where " +
                "Courses.courseNumber = CoursesSelected.courseNumber and CoursesSelected.ECardNumber " +
                "= Users.ECardNumber and Users.ECardNumber = ?) ";
        setStudentCoursesList(sql,student,"");
    }

    public void getCoursesSelectedWithoutGrades(Person student) {
        String sql = "select * from Courses where not exists (select * from CoursesSelected, Users where " +
                "Courses.courseNumber = CoursesSelected.courseNumber and CoursesSelected.ECardNumber " +
                "= Users.ECardNumber and Users.ECardNumber = ? and CoursesSelected.grade > -1) ";
        setStudentCoursesList(sql,student,"");
    }

    /**
     * Get all the courses available to (not full) and not selected by this student this semester.
     * @param student Person object. Should contain ECardNumber.
     */
    public void getCoursesAvailable(Person student, String semester) {
        String sql = "select * from Courses where not exists (select * from CoursesSelected, Users where " +
                "Courses.courseNumber = CoursesSelected.courseNumber and CoursesSelected.ECardNumber " +
                "= Users.ECardNumber and Users.ECardNumber = ?) and Courses.courseSemester = ?";
        setStudentCoursesList(sql,student,semester);
        ArrayList<Course> cAvailable = student.getCourses();
        getCoursesSelected(student,semester);
        ArrayList<Course> cSelected = student.getCourses();
        if(!cSelected.isEmpty()) {
            for (Course cA : cAvailable) {
                for (Course cS : cSelected) {
                    if (isConflicted(cS,cA)) {
                        cA.setConflict(true);
                        break;
                    }
                }
            }
        }
        student.setCourses(cAvailable);
    }

    public void getCoursesAvailable(Person student) {
        String sql = "select * from Courses where not exists (select * from CoursesSelected, Users where " +
                "Courses.courseNumber = CoursesSelected.courseNumber and CoursesSelected.ECardNumber " +
                "= Users.ECardNumber and Users.ECardNumber = ?)";
        setStudentCoursesList(sql,student,"");
        ArrayList<Course> cAvailable = student.getCourses();
        getCoursesSelected(student);
        ArrayList<Course> cSelected = student.getCourses();
        if(!cSelected.isEmpty()) {
            for (Course cA : cAvailable) {
                for (Course cS : cSelected) {
                    if (isConflicted(cS,cA)) {
                        cA.setConflict(true);
                        break;
                    }
                }
            }
        }
        student.setCourses(cAvailable);
    }

    /**
     * Get the grades of the student.
     * @param student Person object.
     */
    public void getGrades(Person student) {
        ArrayList<Course> cs = new ArrayList<Course>();
        String sql = "SELECT Courses.*, CoursesSelected.* FROM Courses INNER JOIN CoursesSelected ON " +
                "(CoursesSelected.courseNumber = Courses.courseNumber and CoursesSelected.EcardNumber = ? and " +
                "CoursesSelected.grade > -1)";
        try {
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,student.getECardNumber());
            ResultSet gradesRes = stmt.executeQuery();
            while (gradesRes.next()){
                Course c = new Course(gradesRes.getString("courseNumber"),
                        gradesRes.getString("courseName"), gradesRes.getString("courseSemester"),
                        gradesRes.getString("courseLecturer"),
                        gradesRes.getString("courseCredit"),
                        gradesRes.getString("courseType"),gradesRes.getInt("grade"));
                cs.add(c);
            }
            student.setCourses(cs);
            student.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            student.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /**
     * Query course exam info. Can be used after having a course list.
     * @param course Should contain course number.
     */
    public void getExamInfo(Course course) {
        String sql = "SELECT * FROM Courses WHERE CourseNumber = ?";
        try {
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,course.getCourseNumber());
            ResultSet cRes = stmt.executeQuery();
            if(cRes.next()) {
                course.setExamTime(cRes.getString("examTime"));
                course.setExamPlace(cRes.getString("examPlace"));
            }
            course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
            e.printStackTrace();
        }
    }

    public void getStudentExamsInfo(Person student) {
        String sql = "select * from Courses where exists (select * from CoursesSelected, Users where " +
                "Courses.courseNumber = CoursesSelected.courseNumber and CoursesSelected.ECardNumber " +
                "= Users.ECardNumber and Courses.isExam and Courses.examTime is not null and Users.ECardNumber = ?) ";
        try {
            ArrayList<Course> cs = new ArrayList<Course>();
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,student.getECardNumber());
            ResultSet coursesRes = stmt.executeQuery();
            while (coursesRes.next()) {
                if(!(coursesRes.getString("examTime").isEmpty())) {
                    cs.add(new Course(coursesRes.getString("courseNumber"),
                            coursesRes.getString("courseName"),
                            coursesRes.getString("courseSemester"),
                            coursesRes.getString("courseLecturer"),
                            coursesRes.getString("courseCredit"),
                            coursesRes.getString("courseType"),
                            coursesRes.getString("examTime"),
                            coursesRes.getString("examPlace")));
                }
            }
            student.setCourses(cs);
            student.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            student.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

     /*
    THE FOLLOWING METHODS ARE FOR LECTURERS
     */

    /**
     * Get the enrolled students of a course.
     * @param lecturer Person Object. Must contain ECardNumber (of the teacher) and course number(Please store it in the
     *               last element of the Person.courses list.)
     *               When this action is performed, a list of courses that contain all the information you need will be
     *               stored into the Person.courses list (the original courses list information will be overwritten).
     */
    public void getEnrolledStudents(Person lecturer) {
        String sql = "SELECT Courses.*, Users.ECardNumber, Users.userName, CoursesSelected.grade" +
                " FROM Courses, CoursesSelected, Users WHERE " +
                "Courses.courseNumber = CoursesSelected.courseNumber AND CoursesSelected.ECardNumber" +
                "= Users.ECardNumber AND Courses.courseNumber = ?";
        int l = lecturer.getCourses().size();
        String cN = lecturer.getCourses().get( l - 1 ).getCourseNumber();
        ArrayList<Course> cs = new ArrayList<Course>();

        try{
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,cN);
            ResultSet cRes = stmt.executeQuery();
            while (cRes.next()) {
                Course c = new Course(cRes.getString("courseNumber"),cRes.getString("courseName"),
                        cRes.getString("courseSemester"),cRes.getString("courseLecturer"),
                        cRes.getString("coursePlace"),cRes.getString("courseTime"),
                        cRes.getString("courseCredit"),cRes.getString("courseType"),
                        cRes.getInt("maximumStudents"),cRes.getInt("enrolledStudents"),
                        cRes.getBoolean("isExam"));
                c.setECardNumber(cRes.getString("ECardNumber"));
                c.setStudentName(cRes.getString("userName"));
                c.setCourseGrade(cRes.getInt("grade"));
                cs.add(c);
            }
            lecturer.setCourses(cs);
            lecturer.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            lecturer.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /**
     * Get the courses of the designated lecturer.
     * @param lecturer Person object. Should contain ECardNumber of the lecturer. If the last item of Person.courses has
     *               courseSemester info that is not empty string, the semester info will be included in the sql.
     */
    public void getLecturerCourses(Person lecturer) {
        ArrayList<Course> cs = new ArrayList<Course>();
        String sql;
        try{
            if (lecturer.getCourses().isEmpty()){
                sql = "select * from Courses where lecturerECardNumber = ?";
                stmt = access.connection.prepareStatement(sql);
                stmt.setString(1,lecturer.getECardNumber());
            }
            else {
                String semester = lecturer.getCourses().get(lecturer.getCourses().size() - 1).getCourseSemester();
                sql = "select * from Courses where lecturerECardNumber = ? and courseSemester = ?";
                stmt = access.connection.prepareStatement(sql);
                stmt.setString(1, lecturer.getECardNumber());
                stmt.setString(2, semester);
            }

            ResultSet cRes = stmt.executeQuery();
            while (cRes.next()) {
                cs.add(new Course(cRes.getString("courseNumber"),cRes.getString("courseName"),
                        cRes.getString("courseSemester"), cRes.getString("courseLecturer"),
                        cRes.getString("coursePlace"),cRes.getString("courseTime"),
                        cRes.getString("courseCredit"),cRes.getString("courseType"),
                        cRes.getInt("maximumStudents"),cRes.getInt("enrolledStudents"),
                        cRes.getBoolean("isExam"),cRes.getBoolean("gradeAdded")));
            }
            lecturer.setCourses(cs);
            lecturer.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            lecturer.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }

    }

    /**
     * Give me a list of courses and I'll set all the grades in it to the database.
     * @param lecturer Person object. It's list of courses should not be empty.
     */
    public void gradesInput(Person lecturer) {
        try {
            for (Course c : lecturer.getCourses()) {
                if (!c.getECardNumber().isEmpty()) {
                    setGrade(c);
                    String sql = "UPDATE Courses SET gradeAdded = 1 WHERE courseNumber = ?";
                    stmt = access.connection.prepareStatement(sql);
                    stmt.setString(1,c.getCourseNumber());
                    stmt.executeUpdate();
                }
            }
            lecturer.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        }catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            lecturer.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /*
    THE FOLLOWING METHODS ARE FOR ADMINISTRATORS
     */

    /**
     * Set grade for one course of one student.
     * @param course Course Object. Should contain grade, course number and ECard Number.
     */
    public void setGrade(Course course) {
        String sql = "UPDATE CoursesSelected SET grade = ? WHERE courseNumber = ? and ECardNumber = ?";
        try{
            stmt = access.connection.prepareStatement(sql);
            stmt.setInt(1,course.getCourseGrade());
            stmt.setString(2,course.getCourseNumber());
            stmt.setString(3,course.getECardNumber());
            stmt.executeUpdate();
            course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /**
     * Add a new course to the database.
     * @param course Course object. Should contain all the information, with the ECardNumber being the lecturer's.
     */
    public void addCourse(Course course) {
        try{
            String sql = "insert into Courses (courseNumber, courseName, courseSemester, lecturerECardNumber," +
                    "coursePlace, courseTime, maximumStudents, enrolledStudents, courseCredit, courseType," +
                    "courseLecturer, isExam, examPlace, examTime, gradeAdded ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,course.getCourseNumber());
            stmt.setString(2,course.getCourseName());
            stmt.setString(3,course.getCourseSemester());
            stmt.setString(4,course.getLecturerECardNumber());
            stmt.setString(5,course.getCoursePlace());
            stmt.setString(6,course.getCourseTime());
            stmt.setInt(7,course.getMaximumStudents());
            stmt.setInt(8,course.getEnrolledStudents());
            stmt.setString(9,course.getCourseCredit());
            stmt.setString(10,course.getCourseType());
            stmt.setString(11,course.getCourseLecturer());
            stmt.setBoolean(12,course.isExam());
            stmt.setString(13,"");
            stmt.setString(14, "");
            stmt.setInt(15, 0);
            stmt.executeUpdate();
            course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /**
     * Change course info.
     * @param course Contains all the info except grade and exam info.
     */
    public void changeCourseInfo(Course course) {
        try{
            String sql = "update Courses set courseName = ?, courseSemester = ?, lecturerECardNumber = ?," +
                    "coursePlace = ?, courseTime = ?, maximumStudents = ?, enrolledStudents = ?, " +
                    "courseCredit = ?, courseType = ?,courseLecturer = ?, isExam = ?, gradeAdded = ? " +
                    "where  courseNumber = ?";
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,course.getCourseName());
            stmt.setString(2,course.getCourseSemester());
            stmt.setString(3,course.getLecturerECardNumber());
            stmt.setString(4,course.getCoursePlace());
            stmt.setString(5,course.getCourseTime());
            stmt.setInt(6,course.getMaximumStudents());
            stmt.setInt(7,course.getEnrolledStudents());
            stmt.setString(8,course.getCourseCredit());
            stmt.setString(9,course.getCourseType());
            stmt.setString(10,course.getCourseLecturer());
            stmt.setBoolean(11,course.isExam());
            stmt.setBoolean(12,course.isGradeAdded());
            stmt.setString(13,course.getCourseNumber());
            stmt.executeUpdate();
            course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
        }
    }

    /**
     * Query courses that are examinable, in order to set exam info.
     * @param admin Should contain semester info. in the tail of admin.courses
     */
    public void getCoursesForExam(Person admin) {
        String sql = "SELECT * FROM Courses WHERE isExam";
        try{

            ArrayList<Course> cs = new ArrayList<Course>();
            stmt = access.connection.prepareStatement(sql);
            ResultSet coursesRes = stmt.executeQuery();
            while (coursesRes.next()) {
                cs.add(new Course(coursesRes.getString("courseNumber"),
                        coursesRes.getString("courseName"),
                        coursesRes.getString("courseSemester"),
                        coursesRes.getString("courseLecturer"),
                        coursesRes.getString("examTime"),
                        coursesRes.getString("examPlace")
                ));
            }
            admin.setCourses(cs);
            admin.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);

        } catch (Exception e) {
            admin.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
            e.printStackTrace();
        }
    }

    public void getAllCourses(Person admin) {
        String sql = "SELECT * FROM Courses";
        try{
            ArrayList<Course> cs = new ArrayList<Course>();
            stmt = access.connection.prepareStatement(sql);
            ResultSet coursesRes = stmt.executeQuery();
            while (coursesRes.next()) {
                cs.add(new Course(coursesRes.getString("courseNumber"),
                        coursesRes.getString("courseName"),
                        coursesRes.getString("courseSemester"),
                        coursesRes.getString("courseLecturer"),
                        coursesRes.getString("lecturerECardNumber"),
                        coursesRes.getString("coursePlace"),
                        coursesRes.getString("courseTime"),
                        coursesRes.getString("courseCredit"),
                        coursesRes.getString("courseType"),
                        coursesRes.getInt("maximumStudents"),
                        coursesRes.getInt("enrolledStudents"),
                        coursesRes.getBoolean("isExam"),
                        coursesRes.getBoolean("gradeAdded")));
            }
            admin.setCourses(cs);
            admin.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
        } catch (Exception e) {
            admin.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
            e.printStackTrace();
        }
    }

    /**
     * Set exam time for a course.
     * @param course Should have course number and exam time and place info.
     */
    public void addExam(Course course) {
        String sql = "UPDATE Courses SET examTime = ?, examPlace = ?  WHERE courseNumber = ?";
        try{
            stmt = access.connection.prepareStatement(sql);
            stmt.setString(1,course.getExamTime());
            stmt.setString(2,course.getExamPlace());
            stmt.setString(3,course.getCourseNumber());
            if(stmt.executeUpdate() != 0) {
                course.setType(Message.MESSAGE_TYPE.TYPE_SUCCESS);
            }
            else {
                course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
            }

        } catch (SQLException e) {
            course.setType(Message.MESSAGE_TYPE.TYPE_FAIL);
            e.printStackTrace();
        }
    }

    /**
     * Set exam info. in batches
     * @param admin List should not be empty.
     */
    public void examsInput(Person admin) {
        for (Course c : admin.getCourses()) {
            addExam(c);
            admin.setType(c.getType());
        }
    }


    public Course rsToCourse()
    {
        try
        {
            Course course = new Course();
            course.setUserType(1);
            course.setpwd(rs.getString("teacherPwd"));
            course.setId(rs.getString("teacherId"));
            course.setName(rs.getString("teacherName"));
            course.setLendBooksNum(rs.getInt("lendBooksNum"));
            course.setAge(rs.getInt("age"));
            course.setSex(rs.getInt("sex"));
            course.setTitle(rs.getInt("title"));
            course.setMajorId(rs.getInt("majorId"));
            return course;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
