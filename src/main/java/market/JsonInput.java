package market;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JsonInput {
	private ArrayList<Item> items;
	private String updateDate;

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public JsonInput(ArrayList<Item> items, String updateDate) {
		this.items = items;
		this.updateDate = updateDate;
	}

	public JsonInput() {
	}

	public boolean checkValidityItems() {
		if (this.items == null) {
			return false;
		} else {
			Set<String> noDuplicates = new HashSet<>();
			for (Item item : this.items) {
				boolean isNotDuplicate = noDuplicates.add(item.getId());
				if (isNotDuplicate == false) {
					return false;
				}
			}
			return true;
		}

	}

	public boolean checkValidityDate() {
		try {
			java.time.format.DateTimeFormatter.ISO_DATE_TIME.parse(this.updateDate);
			return true;
		} catch (java.time.format.DateTimeParseException e) {
			return false;
		}

	}

}
