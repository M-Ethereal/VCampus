package vo;

import java.io.Serializable;

public class LendRecord implements Serializable {
    private static final long serialVersionUID = 50000;
    private String RecordID;
    private String RecordStudentID;
    private String RecordBookID;
    private String RecordStartDate;
    private String RecordEndDate;
    private boolean IsReturn;
    public void setRecordID(String ID)
    {
        RecordID=ID;
    }
    public void setRecordStudentID(String ID)
    {
        RecordStudentID=ID;
    }
    public void setRecordBookID(String ID)
    {
        RecordBookID=ID;
    }
    public void setRecordStartDate(String date)
    {
        RecordStartDate=date;
    }
    public void setRecordEndDate(String date)
    {
        RecordEndDate=date;
    }
    public void setIsReturn(boolean isReturn)
    {
        IsReturn=isReturn;
    }
    public String getRecordID()
    {
        return RecordID;
    }
    public String getRecordStudentID()
    {
        return RecordStudentID;
    }
    public String getRecordBookID()
    {
        return RecordBookID;
    }
    public String getRecordStartDate()
    {
        return RecordStartDate;
    }
    public String getRecordEndDate()
    {
        return RecordEndDate;
    }
    public boolean getIsReturn()
    {
        return IsReturn;
    }

    public LendRecord()//无参构造函数
    {
        this.RecordID=" ";
        this.RecordStudentID=" ";
        this.RecordBookID=" ";
        this.RecordStartDate=" ";
        this.RecordEndDate=" ";
        this.IsReturn=false;
    }
    public LendRecord(String RecordID,String RecordStudentID,String RecordBookID,//有参构造函
                      String RecordStartDate,String RecordEndDate,boolean IsReturn)
    {
        this.RecordID=RecordID;
        this.RecordStudentID=RecordStudentID;
        this.RecordBookID=RecordBookID;
        this.RecordStartDate=RecordStartDate;
        this.RecordEndDate=RecordEndDate;
        this.IsReturn=IsReturn;
    }
}
