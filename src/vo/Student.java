package vo;

import java.io.Serializable;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 50000;
    private String number;//学号
    private String name;
    private Integer age;
    private Integer sex;
    private Integer state;//学籍状态
    private Integer majorId;
    private double GPA;
    private double STRP;
    private double credit;//学分
    private String phoneNmuber;
    private String register;
    private String dormNum;
    private Integer lendBooksNum;
    private double cardBalance;
    private Integer renown;//商家信誉等级
    private Integer campusPosition;
    private Integer punishment;//处分类型，0-没有，1-警告，2-通报批评，3-留校察看，4-劝退，5-开除

    public Student(){
        this.setId("---------");
        this.setpwd("");
        this.setUserType(0);
        this.setNumber("--------");
        this.setName("");
        this.setAge(0);
        this.setSex(0);
        this.setState(0);
        this.setMajorId(9);
        this.setGPA(0.0);
        this.setSTRP(0.0);
        this.setCredit(0.0);
        this.setPhoneNmuber("-----------");
        this.setRegister("----/--/--");
        this.setDormNum("------");
        this.setLendBooksNum(0);
        this.setCardBalance(0.0);
        this.setRenown(100);
        this.setCampusPosition(0);
        this.setPunishment(0);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getPhoneNmuber() {
        return phoneNmuber;
    }

    public void setPhoneNmuber(String phoneNmuber) {
        this.phoneNmuber = phoneNmuber;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public Integer getPunishment() {
        return punishment;
    }

    public void setPunishment(Integer punishment) {
        this.punishment = punishment;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public double getSTRP() {
        return STRP;
    }

    public void setSTRP(double STRP) {
        this.STRP = STRP;
    }

    public String getDormNum() {
        return dormNum;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public Integer getLendBooksNum() {
        return lendBooksNum;
    }

    public void setLendBooksNum(Integer lendBooksNum) {
        this.lendBooksNum = lendBooksNum;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public Integer getRenown() {
        return renown;
    }

    public void setRenown(Integer renown) {
        this.renown = renown;
    }

    public Integer getCampusPosition() {
        return campusPosition;
    }

    public void setCampusPosition(Integer campusPosition) {
        this.campusPosition = campusPosition;
    }
}
