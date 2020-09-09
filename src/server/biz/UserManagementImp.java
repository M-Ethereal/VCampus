package server.biz;

import server.dao.UserDaoImp;
import vo.User;

import java.sql.SQLException;

public class UserManagementImp implements UserManagement{
    private UserDaoImp dao=new UserDaoImp();

    @Override
    public String findId(String userId) throws SQLException {
        User aim = dao.query(userId);
        return aim.getId();
    }

    @Override
    public boolean checkPwd(String id, String pwd, int userType) throws SQLException {
        User aim=dao.query(id);
        if(aim.getpwd().equals(pwd))
            return true;
        else
            return false;
    }

    @Override
    public boolean updateInfo(User user, int userType) {
        return false;
    }

    @Override
    public User getUserInfo(int userType, String id) {
        return null;
    }

//    public static void main(String[] args) throws SQLException {
//        UserManagement loginTest = new UserManagementImp();
//        boolean result=loginTest.checkPwd("09017210", "123", 0);
//			System.out.println(result);
//    }
}
