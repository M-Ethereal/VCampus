package vo;


import java.io.Serializable;

public class BankRecord implements Serializable {
    private static final long serialVersionUID = 50000;

    // private int ID;//自动编号
    private String uID;//一卡通号
    private String record;//使用详情（商品支付、充值。。。）
    private double amount;//具体数额
    private String date;//使用时间

    public BankRecord() {
        uID = "";
        record = "";
        amount = 0;
        date = "";
    }
    public BankRecord(String i,String r,double a,String d) {
        this.setuID(i);
        this.setRecord(r);
        this.setAmount(a);
        this.setDate(d);
    }

	/*public void setID(int a) {
		 this.ID = a;
	}
	 public int getID() {
		 return this.ID;
	 }*/

    public void setuID(String a) {
        this.uID = a;
    }
    public String getuID() {
        return this.uID;
    }

    public void setRecord(String a) {
        this.record = a;
    }
    public String getRecord() {
        return this.record;
    }

    public void setAmount(double a) {
        this.amount=a;
    }
    public double getAmount() {
        return this.amount;
    }

    public void setDate(String d) {
        this.date=d;
    }
    public String getDate() {
        return this.date;
    }
}

