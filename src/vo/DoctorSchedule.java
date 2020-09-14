package vo;

import java.io.Serializable;

public class DoctorSchedule implements Serializable {
    private static final long serialVersionUID = 50000;

    private Integer sID;
    private String docId;
    private String scheduleDate;
    private String scheduleInWeek;
    private String scheduleInDay;



    public Integer getsID() {
        return sID;
    }

    public void setsID(Integer sID) {
        this.sID = sID;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleInWeek() {
        return scheduleInWeek;
    }

    public void setScheduleInWeek(String scheduleInWeek) {
        this.scheduleInWeek = scheduleInWeek;
    }

    public String getScheduleInDay() {
        return scheduleInDay;
    }

    public void setScheduleInDay(String scheduleInDay) {
        this.scheduleInDay = scheduleInDay;
    }
}
