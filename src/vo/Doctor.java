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
        this.setRenown("");//49/10
        this.setDept("");//0-内科 1-外科 2-皮肤科 3-耳鼻喉科 4-口腔 5-眼科 6-心理咨询
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

}
