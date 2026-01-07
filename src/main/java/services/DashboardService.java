package services;

import model.dto.PurchaseTableModel;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, Integer> getMedicineStockData();
    Map<String, Map<String, Integer>> getMedicineStockTrend();
    int getTotalMedicineStock();
    double getTotalSales();
    List<PurchaseTableModel> getRecentPurchases();
    int getLowStockCount();
}