package vo;

import java.io.Serializable;

public class Doctor extends Worker implements Serializable {
    private static final long serialVersionUID = 50000;
    private String dept;

    public Doctor() {
        this.setId("");
        this.setpwd("");
        this.setUserType(2);
        this.setName("");
        this.setAge(0);
        this.setSex(0);
        this.setJobType(1);//0-医生 1-商人
        this.setPosition("");
        this.setRenown("");
        this.setDept("");
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
