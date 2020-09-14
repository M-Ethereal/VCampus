package vo;

import java.io.Serializable;

public class Teacher extends User implements Serializable {
    private static final long serialVersionUID = 50000;
    private String name;
    private Integer age;
    private Integer sex;
    private Integer majorId;
    private Integer title;
    private Integer lendBooksNum;

    public Teacher()
    {
        this.setName("");
        this.setId("");
        this.setpwd("");
        this.setUserType(1);
        this.setAge(0);
        this.setSex(0);
        this.setMajorId(9);
        this.setTitle(0);//0-助教 1-讲师 2-副教授 3-教授
        this.setLendBooksNum(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Integer getLendBooksNum() {
        return lendBooksNum;
    }

    public void setLendBooksNum(Integer lendBooksNum) {
        this.lendBooksNum = lendBooksNum;
    }

    public boolean isUserType() {
        return userType;
    }
    private final boolean userType = true;
}
