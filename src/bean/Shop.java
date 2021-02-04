package app.shopmanagement.bean;

public class Shop {

	private String shopId;
	private String shopName;
	private String status;
	
	public Shop(String id, String name) {
		this.shopId = id;
		this.shopName = name;
		this.status = "OPEN";
	}

	public String getShopId() {
		return shopId;
	}
	public void setAdminId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}