package vo;

import java.io.Serializable;
//与Client、GUI进行交互的过程中需要用到Message对象进行信息的传递
public class Message implements Serializable {
    private static final long serialVersionUID = 50000;
    private int userType;
    private int userId;
    private Object data;
    private String messageType;
    private String errorMessage;
    private boolean lastOperState;

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public boolean isLastOperState() {
        return lastOperState;
    }
    public void setLastOperState(boolean lastOperState) {
        this.lastOperState = lastOperState;
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public int getUserType() {
        return userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }
}
