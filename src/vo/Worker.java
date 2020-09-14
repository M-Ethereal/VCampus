package vo;

import java.io.Serializable;

public class Worker extends User implements Serializable {
    private static final long serialVersionUID = 50000;
    private String name;
    private Integer age;
    private Integer sex;
    private String  renown;//信誉等级
    private Integer jobType;
    private String position;//这个数据类型应该是String？Integer

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

    public String getRenown() {
        return renown;
    }

    public void setRenown(String renown) {
        this.renown = renown;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
