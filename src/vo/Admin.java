package vo;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 50000;

    public Admin(){
        this.setId("");
        this.setpwd("");
        this.setUserType(3);
    }
}
