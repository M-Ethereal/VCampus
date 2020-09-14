package vo;

import java.io.Serializable;

public class Retailer extends Worker implements Serializable {
    private static final long serialVersionUID = 50000;
    private String shopName;

    public Retailer() {
        this.setId("");
        this.setpwd("");
        this.setUserType(2);
        this.setName("");
        this.setAge(0);
        this.setSex(0);
        this.setJobType(1);//0-医生 1-商人
        this.setPosition("");
        this.setRenown("");
        this.setShopName("");
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}