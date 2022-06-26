package market;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Entity
public class MarketItem {
	@Id
	private String id;
	private String name;
	private String type;
	private String updateDate;
	private Integer descSum;
	private Integer descCount;
	private Integer price;

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	private MarketItem parent;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = { CascadeType.REMOVE })
	private Set<MarketItem> children;

	@JsonIgnore
	public Integer getDescSum() {
		return descSum;
	}

	public void setDescSum(Integer descSum) {
		this.descSum = descSum;
	}

	@JsonIgnore
	public Integer getDescCount() {
		return descCount;
	}

	public void setDescCount(Integer descCount) {
		this.descCount = descCount;
	}

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
	@JsonProperty("date")
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	public MarketItem getParent() {
		return parent;
	}

	public void setParent(MarketItem parent) {
		this.parent = parent;
	}

	public Set<MarketItem> getChildren() {
		if (this.type.equals("CATEGORY")) {
			return children;
		} else {
			return null;
		}
	}

	public void setChildren(Set<MarketItem> children) {
		this.children = children;
	}

	public String getParentId() {
		try {
			return this.parent.getId();
		} catch (NullPointerException e) {
			return null;
		}
	}

	MarketItem() {

	}

	MarketItem(Item item, Repo repo, String updateDate) {
		this.id = item.getId();
		this.name = item.getName();
		this.price = item.getPrice();
		this.updateDate = updateDate;
		this.type = item.getType();
		if (item.getParentId() == null) {
			this.parent = null;
		} else {
			this.parent = repo.findById(item.getParentId()).get();
		}
	}

}
