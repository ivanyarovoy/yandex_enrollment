package market;

import org.springframework.data.jpa.repository.JpaRepository;

import market.MarketItem;
public interface Repo extends JpaRepository<MarketItem, String> {

}
