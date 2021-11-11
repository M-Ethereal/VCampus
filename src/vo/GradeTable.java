package vo;

import java.io.Serializable;

//需求：学年seme 课程号cid 课程名name 课程性质（选修必修）courseType 学分credit 成绩grade 成绩性质gradeType
public class GradeTable implements Serializable {
    private static final long serialVersionUID = 50000;
    private String courseSemester;//学年
    private String courseID;//课程号
    private String courseName;//课程名
    private String courseType;//课程性质
    private String courseCredit;//学分
    private Integer grade;//成绩
    private Integer gradeType;//成绩性质
    private Boolean isGradeAdded;//有没有成绩

    public String getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(String courseSemester) {
        this.courseSemester = courseSemester;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGradeType() {
        return gradeType;
    }

    public void setGradeType(Integer gradeType) {
        this.gradeType = gradeType;
    }

    public Boolean getGradeAdded() {
        return isGradeAdded;
    }

    public void setGradeAdded(Boolean gradeAdded) {
        isGradeAdded = gradeAdded;
    }
}
