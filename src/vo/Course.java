package vo;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 50000;

    private String courseNumber;
    private String courseName;
    private String courseSemester;
    private String courseLecturer;
    private String coursePlace;
    private String courseTerm;
    private String courseTime;
    private String courseCredit;
    private String courseType;
    private int maximumStudents;
    private int enrolledStudents;
    private boolean isExam;
    private boolean gradeAdded;
    private String examTime;
    private String examPlace;

    public Course(){
        this.courseNumber = "";
        this.courseName = "";
        this.courseSemester = "";
        this.courseLecturer = "";
        this.coursePlace = "";
        this.courseTerm = "";
        this.courseTime = "";
        this.courseCredit = "";
        this.courseType = "";
        this.maximumStudents = 0;
        this.enrolledStudents = 0;
        this.isExam = false;
        this.gradeAdded = false;
        this.examTime = "";
        this.examPlace = "";
    }


    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseLecturer = courseLecturer;
    }

    public Course(String semester) {
        this.setCourseSemester(semester);
    }

    public Course(String courseNumber, String courseName, String courseSemester,
                  String courseLecturer, String coursePlace, String courseTime,
                  String courseCredit, String courseType, int maximumStudents,
                  int enrolledStudents, boolean isExam) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseLecturer = courseLecturer;
        this.coursePlace = coursePlace;
        this.courseTime = courseTime;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.maximumStudents = maximumStudents;
        this.enrolledStudents = enrolledStudents;
        this.isExam = isExam;
    }

//    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer,
//                  String courseCredit, String courseType, int courseGrade) {
//        this.courseNumber = courseNumber;
//        this.courseName = courseName;
//        this.courseSemester = courseSemester;
//        this.courseLecturer = courseLecturer;
//        this.courseCredit = courseCredit;
//        this.courseType = courseType;
//        this.courseGrade = courseGrade;
//    }

    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer,
                  String coursePlace, String courseTerm, String courseTime, String courseCredit, String courseType,
                  int maximumStudents, int enrolledStudents, boolean isExam, boolean gradeAdded) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseLecturer = courseLecturer;
        this.courseTerm = courseTerm;
        this.coursePlace = coursePlace;
        this.courseTime = courseTime;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.maximumStudents = maximumStudents;
        this.enrolledStudents = enrolledStudents;
        this.isExam = isExam;
        this.gradeAdded = gradeAdded;
    }

    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer,
                  String coursePlace, String courseTerm, String courseTime, String courseCredit, String courseType,
                  int maximumStudents, int enrolledStudents, boolean isExam) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseLecturer = courseLecturer;
        this.courseTerm = courseTerm;
        this.coursePlace = coursePlace;
        this.courseTime = courseTime;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.maximumStudents = maximumStudents;
        this.enrolledStudents = enrolledStudents;
        this.isExam = isExam;
    }

//    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer,
//                  String lecturerECardNumber,
//                  String coursePlace, String courseTime, String courseCredit, String courseType,
//                  int maximumStudents, int enrolledStudents, boolean isExam, boolean gradeAdded) {
//        this.courseNumber = courseNumber;
//        this.courseName = courseName;
//        this.courseSemester = courseSemester;
//        this.courseLecturer = courseLecturer;
//        this.lecturerECardNumber = lecturerECardNumber;
//        this.coursePlace = coursePlace;
//        this.courseTime = courseTime;
//        this.courseCredit = courseCredit;
//        this.courseType = courseType;
//        this.maximumStudents = maximumStudents;
//        this.enrolledStudents = enrolledStudents;
//        this.isExam = isExam;
//        this.gradeAdded = gradeAdded;
//    }

    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer,
                  String courseCredit, String courseType, String examTime, String examPlace) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseLecturer = courseLecturer;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.examTime = examTime;
        this.examPlace = examPlace;
    }

    public Course(String courseNumber, String examTime, String examPlace) {
        this.courseNumber = courseNumber;
        this.examTime = examTime;
        this.examPlace = examPlace;
    }

    public Course(String courseNumber, String courseName, String courseSemester, String courseLecturer, String examTime, String examPlace) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseLecturer = courseLecturer;
        this.examTime = examTime;
        this.examPlace = examPlace;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(String courseSemester) {
        this.courseSemester = courseSemester;
    }

    public String getCourseLecturer() {
        return courseLecturer;
    }

    public void setCourseLecturer(String courseLecturer) {
        this.courseLecturer = courseLecturer;
    }

    public String getCoursePlace() {
        return coursePlace;
    }

    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getMaximumStudents() {
        return maximumStudents;
    }

    public void setMaximumStudents(int maximumStudents) {
        this.maximumStudents = maximumStudents;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }


    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    public boolean isGradeAdded() {
        return gradeAdded;
    }

    public void setGradeAdded(boolean gradeAdded) {
        this.gradeAdded = gradeAdded;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace;
    }

}
