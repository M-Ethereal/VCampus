package vo;

import java.io.Serializable;

public class Grade implements Serializable {
    private static final long serialVersionUID = 50000;
    private String stuID;
    private String courseID;
    private String stuName;
    private String courseName;
    private Integer grade;

    public Grade()
    {
        this.setStuID("");
        this.setCourseID("");
        this.setStuName("");
        this.setCourseName("");
        this.setGrade(0);
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
