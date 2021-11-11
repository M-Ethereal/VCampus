package vo;

import java.io.Serializable;

public class MedicalAdvice implements Serializable {
    private static final long serialVersionUID = 50000;
    private Integer qID;
    private String queryDept;
    private String docId;
    private String docName;
    private String stuId;
    private String stuName;
    private String qQuestion;
    private String qAnswer;
    private String queryDate;
    private String queryTime;
    private String ansDate;
    private String ansTime;
    private Boolean isRank;

    public MedicalAdvice() {
        this.setqID(0);
        this.setQueryDept("");
        this.setDocId("");
        this.setDocName("");
        this.setStuId("");
        this.setStuName("");
        this.setqQuestion("");
        this.setqAnswer("");
        this.setQueryDate("");
        this.setQueryTime("");
        this.setAnsDate("");
        this.setAnsTime("");
        this.setRank(false);
    }

    public Integer getqID() {
        return qID;
    }

    public void setqID(Integer qID) {
        this.qID = qID;
    }

    public String getQueryDept() {
        return queryDept;
    }

    public void setQueryDept(String queryDept) {
        this.queryDept = queryDept;
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

    public String getqQuestion() {
        return qQuestion;
    }

    public void setqQuestion(String qQuestion) {
        this.qQuestion = qQuestion;
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getAnsDate() {
        return ansDate;
    }

    public void setAnsDate(String ansDate) {
        this.ansDate = ansDate;
    }

    public String getAnsTime() {
        return ansTime;
    }

    public void setAnsTime(String ansTime) {
        this.ansTime = ansTime;
    }

    public Boolean getRank() {
        return isRank;
    }

    public void setRank(Boolean rank) {
        isRank = rank;
    }
}
