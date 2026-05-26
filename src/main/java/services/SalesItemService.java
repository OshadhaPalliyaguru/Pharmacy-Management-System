package services;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.SaleItem;
import model.dto.Sales;

import java.util.List;

public interface SalesItemService {
    List<SaleItem>getAllSaleItems();
    void addSaleItem(Sales newSaleItem, ObservableList<CartItem>cartItems);

}
