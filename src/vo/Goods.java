package vo;

import java.io.Serializable;

public class Goods implements Serializable {
	private static final long serialVersionUID = 50000;
	public String gID,gName,gPrice,gTag,gIntroduction,gStore;

	public void setgID(String gID) {
		this.gID=gID;
	}
	public String getgID() {
		return gID;
	}
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
}
