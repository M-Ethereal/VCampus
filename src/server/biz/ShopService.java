package server.biz;

import server.dao.GoodsDao;
import server.dao.RetailerDao;
import server.dao.ShopRecordDao;
import server.dao.StudentDao;
import server.exception.InsufficientBalanceException;
import server.exception.OutOfLimitException;
import vo.Goods;
import vo.Retailer;
import vo.ShopRecord;
import vo.Student;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
public class ShopService {
//	Student stu = new Student();
	StudentDao studao = new StudentDao();
	RetailerDao retailerDao = new RetailerDao();
	ShopRecordDao shopRecordDao = new ShopRecordDao();
    public void Purchase(Goods good,int num, String stuId) throws OutOfLimitException, InsufficientBalanceException
	{
			GoodsDao dao=new GoodsDao();
			ShopRecordDao recdao=new ShopRecordDao();

			int store=Integer.valueOf(good.getgStore());
			if (store >= num) good.setgStore(String.valueOf(store-num));
			else throw new OutOfLimitException();

			dao.Update(good);
			ShopRecord rec=new ShopRecord();
			rec.setdbID(stuId);
			Date now = new Date(); 
			DateFormat date = DateFormat.getDateTimeInstance();  
			String d= date.format(now);
			rec.setdDate(d);
			rec.setdName(good.getgName());
			rec.setdNum(String.valueOf(num));
			rec.setdsID(good.getgsID());
			recdao.InsertRecord(rec);

			Student student = studao.query(stuId);
			if (student.getCardBalance() < Double.valueOf(good.getgPrice())*num) throw new InsufficientBalanceException();
			else student.setCardBalance(student.getCardBalance()-Double.valueOf(good.getgPrice())*num);

			studao.update(student);
			String s=good.getgsID().substring(0, 2);
			if(s.equals("21")) {
				Student g=new StudentDao().query(good.getgsID());
				double balance2=g.getCardBalance()+Double.valueOf(good.getgPrice())*num;
				
			    g.setCardBalance(balance2);
			    studao.update(g);
			}	
		}
    public void Score(ShopRecord shopRecord, String renown) throws SQLException {
    	if (shopRecord.getdsID().contains("bz")){
			Retailer retailer = retailerDao.query(shopRecord.getdsID());
			Double rank_new = Double.valueOf(renown);
			String[] reSplit = retailer.getRenown().split("/");
			Double totalPoints = Double.valueOf(reSplit[0]);
			Double totalPerson = Double.valueOf(reSplit[1]);
			totalPerson = totalPerson+1;
			totalPoints = totalPoints + rank_new;
			String newRenown = String.valueOf(totalPoints) + "/" + String.valueOf(totalPerson);
			retailer.setRenown(newRenown);
			retailerDao.update(retailer);
			shopRecordDao.setRank(shopRecord);
		}
    	else {
			Student ss = studao.query(shopRecord.getdsID());
			Double rank_new = Double.valueOf(renown);
			String[] reSplit = ss.getRenown().split("/");
			Double totalPoints = Double.valueOf(reSplit[0]);
			Double totalPerson = Double.valueOf(reSplit[1]);
			totalPerson = totalPerson+1;
			totalPoints = totalPoints + rank_new;
			String newRenown = String.valueOf(totalPoints) + "/" + String.valueOf(totalPerson);
			ss.setRenown(newRenown);
			studao.update(ss);
			shopRecordDao.setRank(shopRecord);
		}
    }
   
}
