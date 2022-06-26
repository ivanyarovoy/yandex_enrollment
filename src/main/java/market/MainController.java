package market;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class MainController {
	@Autowired
	private Repo repo;

	@PostMapping(path = "/imports")
	@ResponseBody
	public ResponseEntity addNewMarketItem(@RequestBody JsonInput input)
			throws JsonMappingException, JsonProcessingException {
		ArrayList<Item> items = input.getItems();
		String updateDate = input.getUpdateDate();
		if (input.checkValidityItems() == false || (input.checkValidityDate() == false)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.getMessage400());
		}
		for (Item item : items) {
			if (item.checkValidityItem(repo) == false) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.getMessage400());
			}
			MarketItem marketItem = new MarketItem(item, repo, updateDate);
			repo.save(marketItem);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Вставка или обновление прошли успешно.");
	}

	@GetMapping(path = "/nodes/{id}")
	public ResponseEntity getAllDetails(@PathVariable("id") String id)
			throws JsonMappingException, JsonProcessingException {
		if (repo.existsById(id)) {
			MarketItem outputComputed = repo.findById(id).get();
			recurseCompute(outputComputed);
			return ResponseEntity.status(HttpStatus.OK).body(outputComputed);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Messages.getMessage404());
		}
	}

	public void recurseCompute(MarketItem marketItem) {
		Set<MarketItem> children = marketItem.getChildren();
		Integer descSum = 0;
		Integer descCount = 0;
		for (MarketItem child : children) {
			if (child.getType().equals("OFFER")) {
				descSum += child.getPrice();
				descCount++;
			}
			if (child.getType().equals("CATEGORY")) {
				try {
					recurseCompute(child);
					descSum += child.getDescSum();
					descCount += child.getDescCount();
				} catch (NullPointerException e) {
					marketItem.setDescCount(0);
					marketItem.setDescSum(0);
				}
			}
		}
		marketItem.setDescCount(descCount);
		marketItem.setDescSum(descSum);
		try {
			marketItem.setPrice(descSum / descCount);
		} catch (ArithmeticException e) {
			marketItem.setPrice(null);
		}
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity deleteCascade(@PathVariable("id") String id)
			throws JsonMappingException, JsonProcessingException {
		if (id == null || id.equals("")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.getMessage400());
		} else if (repo.existsById(id) == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Messages.getMessage404());
		} else {
			repo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Удаление прошло успешно.");
		}
	}
}
