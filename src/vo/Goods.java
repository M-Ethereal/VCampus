package vo;

import java.io.Serializable;

public class Goods implements Serializable {
	private static final long serialVersionUID = 50000;
	private String gID,gName,gPrice,gTag,gIntroduction,gSID,gBID;
	private boolean gDelivery;

	public Goods(String gID, String gName, String gPrice, String gTag, String gIntroduction, boolean gDelivery, String gSID, String gBID) {
		this.gID=gID;
		this.gName=gName;
		this.gPrice=gPrice;
		this.gTag=gTag;
		this.gIntroduction=gIntroduction;
		this.gDelivery=gDelivery;
		this.gSID=gSID;
		this.gBID=gBID;
	}

	public void setgID() {
		
	}

	public void getgID() {
		
	}
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/
}
