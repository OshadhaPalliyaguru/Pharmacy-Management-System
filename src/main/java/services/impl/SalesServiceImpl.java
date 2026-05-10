package services.impl;

import model.dto.Sales;
import repository.impl.SaleRepositoryImpl;
import services.SaleService;

import java.util.List;

public class SalesServiceImpl implements SaleService {

  private  final SaleRepositoryImpl saleRepository =new SaleRepositoryImpl();

    @Override
    public List<Sales> getAllSales() {
        return saleRepository.getAllSales();
    }
    @Override
    public int addSales(Sales sales){
        return saleRepository.addSales(sales);
    }
}

