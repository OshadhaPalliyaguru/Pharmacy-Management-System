package services;

import model.dto.Purchase_items;

import java.util.List;

public interface PurchaseItemService {
    boolean addPurchaseItem(Purchase_items purchaseItems);
    boolean updatePurchaseItem(Purchase_items purchaseItems);
    Purchase_items findPurchaseItemByNo(String batchNo);
    List<Purchase_items>getAllPurchaseItems();
    boolean deletePurchaseItems(String batchNo);



}
