package vo;

import java.io.Serializable;

abstract public class User implements Serializable {
    private static final long serialVersionUID = 50000;
    private int id;
    private String pwd;
    private int userType;	//0-student  1-teacher  2-admin  3-worker

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getpwd() { return pwd; }
    public void setpwd(String pwd) { this.pwd = pwd; }

    public int getUserType() { return userType; }
    public void setUserType(int type) { this.userType = userType; }
}
