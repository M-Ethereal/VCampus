package vo;

import javafx.scene.image.Image;

public class tempShop{
	private String gName,gPrice,gTag,gIntroduction,gStore,gsID;
	private int gAddNum;
	private Image gPic;

	public void setgName(String gName) {
		this.gName=gName;
		System.out.println("/client/Image/" + gName + ".jpg");
		gPic = new Image("/client/Image/" + gName + ".jpg", 180, 220, false, true);
	}
	public void setgName_pure(String gName) {
		this.gName=gName;
	}
	public String getgName() {
		return gName;
	}
	public void setgPrice(String gPrice) {
		this.gPrice=gPrice;
	}
	public String getgPrice() {
		return gPrice;
	}
	public void setgTag(String gTag) {
		this.gTag=gTag;
	}
	public String getgTag() {
		return gTag;
	}
	public void setgIntroduction(String gIntroduction) {
		this.gIntroduction=gIntroduction;
	}
	public String getgIntroduction() {
		return gIntroduction;
	}
	public void setgStore(String gStore) {
		this.gStore=gStore;
	}
	public String getgStore() {
		return gStore;
	}
	public void setgsID(String gsID) {
		this.gsID=gsID;
	}
	public String getgsID() {
		return gsID;
	}
	public void setgAddNum(int gAddNum) {
		this.gAddNum=gAddNum;
	}
	public int getgAddNum() {
		return gAddNum;
	}
	public Image getgPic() { return gPic; }
}
