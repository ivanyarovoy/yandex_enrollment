package market;

public class Item {

	private String id;

	private String name;

	private String type;

	private Integer price;

	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Item(String id, String name, String type, Integer price, String parentId) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.parentId = parentId;
	}

	public Item() {
	}

	public boolean checkValidityItem(Repo repo) {
		try {
			if (this.id == null || this.id.equals("")) {
				return false;
			}
			if (this.name == null || this.name.equals("")) {
				return false;
			}
			if (!(this.type.equals("CATEGORY") || this.type.equals("OFFER"))) {
				return false;
			}
			if ((this.price == null || this.price < 0) && this.type.equals("OFFER")) {
				return false;
			} else if (this.price != null && this.type.equals("CATEGORY")) {
				return false;
			}
			if (this.parentId != null && (repo.existsById(this.parentId) == false)) {
				return false;
			} else if ((this.parentId != null)
					&& (repo.findById(this.parentId).get().getType().equals("CATEGORY") == false)) {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}
