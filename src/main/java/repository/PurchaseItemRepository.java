package repository;

import model.dto.Purchase_items;

import java.util.List;

public interface PurchaseItemRepository {
     boolean addPurchaseItem(Purchase_items purchaseItems);
    boolean updatePurchaseItems(Purchase_items purchaseItems);
    Purchase_items findBybatchNo(String batchNo);
    List<Purchase_items> getAllPurchaseItems();
    boolean deletePurchaseItems(String batchNo);
}
