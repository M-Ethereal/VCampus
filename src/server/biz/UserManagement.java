package server.biz;

import vo.User;

import java.sql.SQLException;

public interface UserManagement {
    public boolean checkPwd(int id,String pwd,int userType);
    public boolean updateInfo(User user,int userType);
    public User getUserInfo(int userType,int id);
    public boolean newUser(String nickName,String pwd,int userType);
    public boolean newAdmin(int id,String pwd);
}
