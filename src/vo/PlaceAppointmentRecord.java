package vo;

import java.io.Serializable;

public class PlaceAppointmentRecord implements Serializable {
    private static final long serialVersionUID = 50000;
    private Integer apID;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String placeId;
    private String placeName;
    private String stuId;
    private String stuName;

    public PlaceAppointmentRecord() {
        this.setApID(0);
        this.setStartDate("");
        this.setStartTime("");
        this.setEndDate("");
        this.setEndTime("");
        this.setPlaceId("");
        this.setPlaceName("");
        this.setStuId("");
        this.setStuName("");
    }

    public Integer getApID() {
        return apID;
    }

    public void setApID(Integer apID) {
        this.apID = apID;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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
}
