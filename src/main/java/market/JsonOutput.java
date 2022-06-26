package market;

import java.util.HashSet;
import java.util.Set;

public class JsonOutput {

	private String id;
	private String name;
	private String type;
	private String updateDate;
	private Integer price;
	private String parentId;
	private Set<JsonOutput> children;

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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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

	public Set<JsonOutput> getChildren() {
		return children;
	}

	public void setChildren(Set<JsonOutput> children) {
		this.children = children;
	}

	JsonOutput() {

	}

	JsonOutput(MarketItem marketItem) {
		correctFieldsSetter(marketItem);
	}

	private void correctFieldsSetter(MarketItem marketItem) {
		this.id = marketItem.getId();
		this.name = marketItem.getName();
		this.type = marketItem.getType();
		this.updateDate = marketItem.getUpdateDate();
		try {
			this.parentId = marketItem.getParent().getId();
		} catch (NullPointerException e) {
			this.parentId = null;
		}
		this.price = marketItem.getPrice();
		if (marketItem.getType().equals("OFFER")) {
			this.children = null;
		}
		Set<MarketItem> oldChildren = marketItem.getChildren();
		Set<JsonOutput> newChildren = new HashSet<JsonOutput>();
		if (oldChildren != null) {
			for (MarketItem oldChild : oldChildren) {
				JsonOutput newChild = new JsonOutput();
				newChild.correctFieldsSetter(oldChild);
				newChildren.add(newChild);
			}
			this.children = newChildren;
		} else {
			this.children = null;
		}
	}
}
