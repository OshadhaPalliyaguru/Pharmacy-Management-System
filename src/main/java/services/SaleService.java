package services;

import model.dto.Sales;

import java.util.List;

public interface SaleService {
 List<Sales>getAllSales();
 int addSales(Sales sales);
}
