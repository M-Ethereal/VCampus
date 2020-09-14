package server.biz;

import server.dao.*;
import vo.*;

import java.util.Random;

public class UserManagementImp implements UserManagement{
    private TeacherDao daot = new TeacherDao();
    private StudentDao daos = new StudentDao();
    private AdminDao daoa = new AdminDao();
    private DoctorDao daod = new DoctorDao();
    private RetailerDao daor = new RetailerDao();

    @Override
    public boolean checkPwd(int id, String pwd,int userType)
    {
        if(userType==1) {
            Teacher aim=daot.query(id);
            if(aim.getPwd().equals(pwd))
                return true;
            else
                return false;
        }
        else if(userType==0){
            Student aim=daos.query(id);
            if(aim.getPwd().equals(pwd))
                return true;
            else
                return false;
        }
        else if(userType==2){
            Doctor aim=daod.query(id);
            if(aim.getPwd().equals(pwd))
                return true;
            else
                return false;
        }
        else if(userType==3){
            Retailer aim=daor.query(id);
            if(aim.getPwd().equals(pwd))
                return true;
            else
                return false;
        }
        else if(userType==4){
            /**
             * 此处添加admin信息
             */
            Admin aim=daoa.query(id);
            if(aim.getPwd().equals(pwd))
                return true;
            else
                return false;
        }
        else {
            return false;
        }
        // TODO Auto-generated method stub
        //return false;
    }

    @Override
    public boolean updateInfo(User user,int userType) {
        if(userType==1)
        {
            Teacher aim=(Teacher)user;
            daot.update(aim);
            return true;
        }
        else if(userType==0){
            Student aim=(Student)user;
            daos.update(aim);
            return true;
        }
        else if(userType==2){
            Doctor aim=(Doctor) user;
            daod.update(aim);
            return true;
        }
        else if(userType==3){
            Retailer aim=(Retailer) user;
            daor.update(aim);
            return true;
        }
        else if(userType==4)
        {
            Admin aim=(Admin)user;
            daoa.update(aim);
            return true;
        }
        //添加admin信息
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User getUserInfo(int userType, int id) {
        if(userType==1) {
            Teacher aim=daot.query(id);
            return aim;
        }
        else if(userType==0){
            Student aim=daos.query(id);
            return aim;
        }
        else if(userType==2){
            Doctor aim=daod.query(id);
            return aim;
        }
        else if(userType==3){
            Retailer aim=daor.query(id);
            return aim;
        }
        else {
            /**
             * admin
             */
            return null;
        }
        // TODO Auto-generated method stub
        //return null;
    }

    @Override
    public boolean newUser(String nickName,String pwd,int userType) {
        int i;
        if(userType==1) {
            if(daot.queryByNickName(nickName)!=null)
                throw new RecordAlreadyExistException();
            else {
                Teacher aim=new Teacher();
                aim.setNickName(nickName);
                aim.setPwd(pwd);
                do{
                    Random r = new Random();
                    int number = r.nextInt(10000000);
                    String id = "090"+number;
                    i = Integer.parseInt(id);
                }
                while(daot.query(i)!=null);
                aim.setId(i);
                aim.setUserType(1);
                daot.insert(aim);
            }
            return true;
        }
        else if(userType==0){
            if(daos.queryByNickName(nickName)!=null)
                throw new RecordAlreadyExistException();
            else {
                Student aim=new Student();
                aim.setNickName(nickName);
                aim.setPwd(pwd);
                aim.setUserType(0);
                do{
                    Random r = new Random();
                    int number = r.nextInt(10000000);
                    String id = "213"+number;
                    i = Integer.parseInt(id);
                }
                while(daos.query(i)!=null);
                aim.setId(i);
                daos.insert(aim);
            }
            return true;
        }
        else {
            /**
             * add admin part
             */
            return false;
        }
    }

    @Override
    public boolean newAdmin(int id, String pwd) {
        return false;
    }
}
