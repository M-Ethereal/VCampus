package vo;
import java.io.Serializable;
import java.sql.*;
public class LendRecord implements Serializable
{
    private static final long serialVersionUID=50000;
    private String RecordID;
    private String RecordStudentID;
    private String RecordBookID;
    private String RecordStartDate;
    private String RecordEndDate;
    private boolean IsReturn;
    private String RecordBookName;
    public void setRecordBookName(String name)
    {
        RecordBookName=name;
    }
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
    public String getRecordBookName()
    {
        return RecordBookName;
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
        this.RecordBookName=" ";
        this.IsReturn=false;
    }
    public LendRecord(LendRecord lend)
    {
        this.RecordID=lend.RecordID;
        this.RecordStudentID=lend.RecordStudentID;
        this.RecordBookID=lend.RecordBookID;
        this.RecordStartDate=lend.RecordStartDate;
        this.RecordEndDate=lend.RecordEndDate;
        this.IsReturn=lend.IsReturn;
        this.RecordBookName=lend.RecordBookName;
    }
}

