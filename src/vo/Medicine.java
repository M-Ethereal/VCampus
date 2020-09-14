package vo;

import java.io.Serializable;

public class Medicine implements Serializable {
    private static final long serialVersionUID = 50000;
    private Integer mID;
    private String mName;
    private double mPrice;
    private String mTag;
    private String mSpecification;
    private Integer mStore;
    private String mIntroduction;

    public Medicine(){
        this.setmID(0);
        this.setmName("");
        this.setmPrice(0.0);
        this.setmTag("");
        this.setmSpecification("");
        this.setmStore(0);
        this.setmIntroduction("");
    }

    public Integer getmID() {
        return mID;
    }

    public void setmID(Integer mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public String getmSpecification() {
        return mSpecification;
    }

    public void setmSpecification(String mSpecification) {
        this.mSpecification = mSpecification;
    }

    public Integer getmStore() {
        return mStore;
    }

    public void setmStore(Integer mStore) {
        this.mStore = mStore;
    }

    public String getmIntroduction() {
        return mIntroduction;
    }

    public void setmIntroduction(String mIntroduction) {
        this.mIntroduction = mIntroduction;
    }
}
