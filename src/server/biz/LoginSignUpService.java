package server.biz;

import server.dao.*;
import server.exception.UserUnsignedException;
import server.exception.WrongPasswordException;
import vo.*;

import java.sql.SQLException;

public class LoginSignUpService {
    private TeacherDao daot = new TeacherDao();
    private StudentDao daos = new StudentDao();
    private AdminDao daoa = new AdminDao();
    private DoctorDao daod = new DoctorDao();
    private RetailerDao daor = new RetailerDao();


    public Object checkPwd(String id, String pwd,int userType) throws UserUnsignedException, SQLException, WrongPasswordException {
        System.out.println("Biz: 用户类型"+userType);
        if(userType==1) {
            Teacher aim=daot.query(id);
            if(aim==null||aim.getpwd()=="") throw new UserUnsignedException();
            else if(aim.getpwd().equals(pwd)) {
                System.out.println("Biz: 用户姓名" + aim.getName());
                return aim;
            }
            else throw new WrongPasswordException();
        }
        else if(userType==0){
            Student aim=daos.query(id);
            if(aim==null||aim.getpwd()=="") throw new UserUnsignedException();
            else if(aim.getpwd().equals(pwd)){
                return aim;
            }
            else throw new WrongPasswordException();
        }
        else if(userType==2){
            Doctor aim=daod.query(id);
            if(aim==null||aim.getpwd()=="") throw new UserUnsignedException();
            else if(aim.getpwd().equals(pwd)){
                return aim;
            }
            else throw new WrongPasswordException();
        }
        else if(userType==3){
            Retailer aim=daor.query(id);
            if(aim==null||aim.getpwd()=="") throw new UserUnsignedException();
            else if(aim.getpwd().equals(pwd)){
                System.out.println("Biz: 用户姓名" + aim.getName());
                return aim;
            }
            else throw new WrongPasswordException();
        }
        else if(userType==4){
            Admin aim=daoa.query(id);
            if(aim==null||aim.getpwd()=="") throw new UserUnsignedException();
            else if(aim.getpwd().equals(pwd)){
                return aim;
            }
            else throw new WrongPasswordException();
        }
        else {
            return null;
        }
        // TODO Auto-generated method stub
        //return false;
    }

    //管理员不能注册
    public int checkSignUp(String id, String pwd, int userType) throws SQLException {
        if(userType==1) {
            Teacher aim=daot.query(id);
            if(aim==null) return 0;
            else if(aim.getpwd()!=null) return 1;
            else {
                aim.setpwd(pwd);
                daot.update(aim);
                return 2;
            }
        }
        else if(userType==0){
            Student aim=daos.query(id);
            if(aim==null) return 0;
            else if(aim.getpwd()!=null) return 1;
            else {
                aim.setpwd(pwd);
                daos.update(aim);
                return 2;
            }
        }
        else if(userType==2){
            Doctor aim=daod.query(id);
            if(aim==null) return 0;
            else if(aim.getpwd()!=null) return 1;
            else {
                aim.setpwd(pwd);
                daod.update(aim);
                return 2;
            }
        }
        else if(userType==3){
            Retailer aim=daor.query(id);
            if(aim==null) return 0;
            else if(aim.getpwd()!=null) return 1;
            else {
                aim.setpwd(pwd);
                daor.update(aim);
                return 2;
            }
        }
        else {
            return 0;
        }
    }
}
