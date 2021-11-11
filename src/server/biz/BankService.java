package server.biz;


import server.dao.BankDao;
import server.dao.StudentDao;
import server.exception.OutOfLimitException;
import vo.BankRecord;
import vo.Student;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class BankService {

    //查询个人交易明细

    //扣费，也就是各种支付业务（校园网，学费）
    public static boolean bankPayWIFIA(BankRecord rec) throws OutOfLimitException {//手机流量包（15块）

        BankDao bd = new BankDao();
        StudentDao sd = new StudentDao();
        Student stu = new Student();
        stu = sd.query(rec.getuID());
        if(stu.getCardBalance()>15.0)
            stu.setCardBalance(stu.getCardBalance()-15.0);
        else throw new OutOfLimitException();
        sd.update(stu);
        bd.insert(rec);


        return true;
    }

    public static boolean bankPayWIFIB(BankRecord rec) throws OutOfLimitException {//校园网套餐（50块）

        BankDao bd = new BankDao();
        StudentDao sd = new StudentDao();
        Student stu = new Student();
        stu = sd.query(rec.getuID());
        if(stu.getCardBalance()>50.0)
            stu.setCardBalance(stu.getCardBalance()-50.0);
        else throw new OutOfLimitException();
        sd.update(stu);
        bd.insert(rec);


        return true;
    }

    public static boolean bankPayTuition(BankRecord rec) throws OutOfLimitException {//学费（300块）

        BankDao bd = new BankDao();
        StudentDao sd = new StudentDao();
        Student stu = new Student();
        stu = sd.query(rec.getuID());
        if(stu.getCardBalance()>300.0)
            stu.setCardBalance(stu.getCardBalance()-300.0);
        else throw new OutOfLimitException();
        sd.update(stu);
        bd.insert(rec);


        return true;
    }

    public static boolean bankInvestCard(BankRecord rec,int number){//充值

        BankDao bd = new BankDao();
        StudentDao sd = new StudentDao();
        Student stu = new Student();
        stu = sd.query(rec.getuID());
        stu.setCardBalance(stu.getCardBalance()+number);
        sd.update(stu);
        bd.insert(rec);

        return true;
    }


}
