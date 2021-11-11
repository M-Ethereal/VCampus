package vo;

import java.io.Serializable;

public class Goods implements Serializable {
	private static final long serialVersionUID = 50000;
	private String gName,gPrice,gTag,gIntroduction,gStore,gsID;

	public void setgName(String gName) {
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
}
