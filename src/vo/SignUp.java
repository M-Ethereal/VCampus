package vo;

import java.io.Serializable;

public class SignUp implements Serializable {
    private static final long serialVersionUID = 50000;
    private String id;
    private String pwd;
    private int userType;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public int getUserType() {
        return userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }
}