package repository;

import model.dto.SaleItem;

import java.util.List;

public interface SalesItemRepository {

 void addSaleItem(SaleItem newSaleItem);
  List<SaleItem>getAllSaleItems();

    }
