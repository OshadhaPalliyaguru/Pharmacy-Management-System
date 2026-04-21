package services.impl;

import model.dto.PurchaseTableModel;
import repository.DashboardRepository;
import repository.impl.DashboardRepositoryImpl;
import services.DashboardService;

import java.util.List;
import java.util.Map;

public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository = new DashboardRepositoryImpl();

    @Override
    public Map<String, Integer> getMedicineStockData() {
        return dashboardRepository.getMedicineStockData();
    }

    @Override
    public Map<String, Map<String, Integer>> getMedicineStockTrend() {
        return dashboardRepository.getMedicineStockTrend();
    }

    @Override
    public int getTotalMedicineStock() {
        return dashboardRepository.getTotalMedicineStock();
    }

    @Override
    public double getTotalSales() {
        return dashboardRepository.getTotalSales();
    }

    @Override
    public List<PurchaseTableModel> getRecentPurchases() {
        return dashboardRepository.getRecentPurchases();
    }

    @Override
    public int getLowStockCount() {
        return dashboardRepository.getLowStockCount();
    }
}