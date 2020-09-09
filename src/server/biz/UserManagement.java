package server.biz;

import vo.User;

import java.sql.SQLException;

public interface UserManagement {
    public String findId(String userId) throws SQLException;
    public boolean checkPwd(String id,String pwd,int userType) throws SQLException;
    public boolean updateInfo(User user, int userType);
    public User getUserInfo(int userType,String id);
}
