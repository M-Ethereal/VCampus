package vo;

import java.io.Serializable;

public class Place implements Serializable {
    private static final long serialVersionUID = 50000;
    private Integer placeID;
    private String placeName;
    private String placePosition;
    private String pCampus;
    private Integer placeType; //0-图书馆 1-体育馆 2-教室 3-浴室
    private Integer pMaximumAvailable;//最大人数上限

    public Place() {
        this.setPlaceID(0);
        this.setPlaceName("");
        this.setPlacePosition("");
        this.setpCampus("");
        this.setPlaceType(0);
        this.setpMaximumAvailable(1);
    }

    public Integer getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Integer placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlacePosition() {
        return placePosition;
    }

    public void setPlacePosition(String placePosition) {
        this.placePosition = placePosition;
    }

    public String getpCampus() {
        return pCampus;
    }

    public void setpCampus(String pCampus) {
        this.pCampus = pCampus;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public Integer getpMaximumAvailable() {
        return pMaximumAvailable;
    }

    public void setpMaximumAvailable(Integer pMaximumAvailable) {
        this.pMaximumAvailable = pMaximumAvailable;
    }
}
