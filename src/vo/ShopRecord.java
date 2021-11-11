package vo;

import java.io.Serializable;

public class ShopRecord implements Serializable { //商店购买记录
    private static final long serialVersionUID = 50000;

    private String dName,dsID,dbID,dDate,dNum;
    private boolean renown;

    public ShopRecord(){
        this.setdName("");
        this.setdsID("");
        this.setdbID("");
        this.setdDate("");
        this.setdNum("");
        this.setRenown(false);
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

    public boolean isRenown() {
        return renown;
    }

    public void setRenown(boolean renown) {
        this.renown = renown;
    }
}

