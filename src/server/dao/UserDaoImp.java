package server.dao;

import vo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImp implements UserDao{

    private DbHelper access=new DbHelper();
    private PreparedStatement stmt = null;
    private ResultSet rs=null;

    @Override
    public boolean insert(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public ArrayList<User> queryAll() {
        return null;
    }

    @Override
    public User query(String userID) throws SQLException {
        String sql= "SELECT * FROM User where userId="+ "'"+ userID+"'";
        stmt = access.connection.prepareStatement(sql);
        rs = stmt.executeQuery();

        if(rs.next())
        {
            return rsToUser();
        }
        return null;
    }

    public User rsToUser()
    {
        try
        {
            User user = new User(rs.getString("userId"),rs.getString("userPwd"));
            return user;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
