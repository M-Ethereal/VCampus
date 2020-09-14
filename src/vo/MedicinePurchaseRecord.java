package vo;

import java.io.Serializable;

public class MedicinePurchaseRecord implements Serializable {
    private static final long serialVersionUID = 50000;
    private Integer purchaseID;
    private String purchaseStuId;
    private String purchaseMedId;
    private String purchaseDate;
    private String purchaseTime;
    private double purchaseCost;
    private Integer purchaseNum;

    public MedicinePurchaseRecord() {
        this.setPurchaseID(0);
        this.setPurchaseStuId("");
        this.setPurchaseMedId("");
        this.setPurchaseDate("");
        this.setPurchaseTime("");
        this.setPurchaseCost(0.0);
        this.setPurchaseNum(0);
    }

    public Integer getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(Integer purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getPurchaseStuId() {
        return purchaseStuId;
    }

    public void setPurchaseStuId(String purchaseStuId) {
        this.purchaseStuId = purchaseStuId;
    }

    public String getPurchaseMedId() {
        return purchaseMedId;
    }

    public void setPurchaseMedId(String purchaseMedId) {
        this.purchaseMedId = purchaseMedId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }
}
