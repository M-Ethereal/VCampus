package vo;

import java.io.Serializable;

public class RegistrationRecord implements Serializable { //挂号记录
    private static final long serialVersionUID = 50000;
    private Integer apID;
    private String docId;
    private String docName;
    private String stuId;
    private String stuName;
    private String apDate;
    private String apTime;
    private double apCost;
    private String apDept;
    private String apType;

    public RegistrationRecord() {
        this.setApID(0);
        this.setDocId("");
        this.setDocName("");
        this.setStuId("");
        this.setStuName("");
        this.setApDate("");
        this.setApTime("");
        this.setApCost(0.0);
        this.setApDept("");
        this.setApType("");
    }

    public Integer getApID() {
        return apID;
    }

    public void setApID(Integer apID) {
        this.apID = apID;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getApDate() {
        return apDate;
    }

    public void setApDate(String apDate) {
        this.apDate = apDate;
    }

    public String getApTime() {
        return apTime;
    }

    public void setApTime(String apTime) {
        this.apTime = apTime;
    }

    public double getApCost() {
        return apCost;
    }

    public void setApCost(double apCost) {
        this.apCost = apCost;
    }

    public String getApDept() {
        return apDept;
    }

    public void setApDept(String apDept) {
        this.apDept = apDept;
    }

    public String getApType() {
        return apType;
    }

    public void setApType(String apType) {
        this.apType = apType;
    }
}
