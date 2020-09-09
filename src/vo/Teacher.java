package vo;

import java.io.Serializable;

public class Teacher extends User implements Serializable {
    private static final long serialVersionUID = 50000;

    public Teacher()
    {
        this.setId(0);
        this.setUserType(1);
        this.setpwd("");
    }


    public boolean isUserType() {
        return userType;
    }
    private final boolean userType = true;
}
