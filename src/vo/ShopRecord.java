package vo;

import java.io.Serializable;

public class ShopRecord implements Serializable { //商店购买记录
    private static final long serialVersionUID = 50000;
    public String dID,dName,dsID,dbID,dDate,dNum;
    public boolean dDelivery;
    public void setdID(String dID) {
        this.dID=dID;
    }
    public String getdID() {
        return dID;
    }
    public void setdName(String dName) {
        this.dName=dName;
    }
    public String getdName() {
        return dName;
    }
    public void setdsID(String dsID) {
        this.dsID=dsID;
    }
    public String getdsID() {
        return dsID;
    }
    public void setdbID(String dbID) {
        this.dbID=dbID;
    }
    public String getdbID() {
        return dbID;
    }
    public void setdDelivery(boolean dDelivery) {
        this.dDelivery=dDelivery;
    }
    public boolean getdDelivery() {
        return dDelivery;
    }
    public void setdDate(String dDate) {
        this.dDate=dDate;
    }
    public String getdDate() {
        return dDate;
    }
    public void setdNum(String dNum) {
        this.dNum=dNum;
    }
    public String getdNum() {
        return dNum;
    }
}
