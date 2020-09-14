package vo;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 50000;
    private String id;
    private String pwd;
    private int userType;	//0-student  1-teacher  2-doctor  3-retailer  4-admin

//    //test
//    public User(){
//        this.setId("");
//        this.setpwd("");
//        this.setUserType(0);
//    }
//
//    public User(String userId, String userPwd) {
//        this.setId(userId);
//        this.setpwd(userPwd);
//        this.setUserType(0);
//    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getpwd() { return pwd; }
    public void setpwd(String pwd) { this.pwd = pwd; }

    public int getUserType() { return userType; }
    public void setUserType(int type) { this.userType = userType; }
}
