package server.dao;

import vo.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao {
    boolean insert(User user);
    boolean delete(User user);
    boolean update(User user);
    ArrayList<User> queryAll();
    User query(String userID) throws SQLException;
}
