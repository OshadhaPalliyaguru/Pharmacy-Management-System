package repository;

import model.dto.PurchaseTableModel;

import java.util.List;
import java.util.Map;

public interface DashboardRepository {
    Map<String, Integer> getMedicineStockData();
    Map<String, Map<String, Integer>> getMedicineStockTrend(); // For the complex line chart
    int getTotalMedicineStock();
    double getTotalSales();
    List<PurchaseTableModel> getRecentPurchases();
    int getLowStockCount();
}