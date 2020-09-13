package server.dao;
//实现数据库的连接
//dao层的包只需直接调用一次DbHelper即可
import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {
    public Connection connection;
    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//加载ucanaccess驱动
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    DbHelper()
    {
        try {
            connection = DriverManager.getConnection("jdbc:ucanaccess://./db/database.accdb","","");
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Connection getAccessConnection(String user, String pwd)
    {
        try {
            //获取Access数据库连接(Connection)
            this.connection = DriverManager.getConnection("jdbc:ucanaccess://./db/database.accdb", user, pwd);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return this.connection;
    }
    public static void main(String[] args) {
        DbHelper dbHelper =new DbHelper();
        Connection connection= dbHelper.getAccessConnection("", "");
    }
}
