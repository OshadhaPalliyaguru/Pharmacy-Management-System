package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.dto.PurchaseTableModel;
import model.dto.Purchases;
import services.DashboardService;
import services.impl.DashboardServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {

    // ✅ Professional: Use Service, not Database directly
    private final DashboardService dashboardService = new DashboardServiceImpl();

    @FXML
    private AreaChart<String, Number> AreaChart;

    @FXML
    private TableColumn<Purchases, String> ColDate;
    @FXML
    private TableColumn<Purchases, String> ColMount;
    @FXML
    private TableColumn<Purchases, String> ColTransAction;
    @FXML
    private TableColumn<Purchases, String> ColType;

    @FXML
    private LineChart<String, Number> LineChart;

    @FXML
    private TableView<PurchaseTableModel> tblRecentActivity;

    @FXML
    private Text txtPurchase; // Seems unused in logic, but kept for FXML binding
    @FXML
    private Text txtStock;
    @FXML
    private Text txtTotalSale;
    @FXML
    private Label txtLowMedicine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMedicineStockAreaChart();
        loadAllMedicineStockTrend();
        loadTotalStock();
        loadTotalSalesAmount();
        loadRecentActivity();
        loadLowMedicineCount();
    }

    private void loadMedicineStockAreaChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Medicine Stock");

        // Ask Service for data map
        Map<String, Integer> stockData = dashboardService.getMedicineStockData();

        for (Map.Entry<String, Integer> entry : stockData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        AreaChart.getData().add(series);
    }

    private void loadAllMedicineStockTrend() {
        // Ask Service for complex trend data
        Map<String, Map<String, Integer>> trendData = dashboardService.getMedicineStockTrend();

        for (Map.Entry<String, Map<String, Integer>> medicineEntry : trendData.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(medicineEntry.getKey());

            for (Map.Entry<String, Integer> dateEntry : medicineEntry.getValue().entrySet()) {
                series.getData().add(new XYChart.Data<>(dateEntry.getKey(), dateEntry.getValue()));
            }
            LineChart.getData().add(series);
        }
    }

    private void loadTotalStock() {
        int totalStock = dashboardService.getTotalMedicineStock();
        txtStock.setText(String.valueOf(totalStock));
    }

    private void loadTotalSalesAmount() {
        double totalSales = dashboardService.getTotalSales();
        txtTotalSale.setText(String.format("%.2f", totalSales));
    }

    private void loadRecentActivity() {
        List<PurchaseTableModel> recentList = dashboardService.getRecentPurchases();

        ColTransAction.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColType.setCellValueFactory(new PropertyValueFactory<>("type"));
        ColMount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tblRecentActivity.setItems(FXCollections.observableArrayList(recentList));
    }

    private void loadLowMedicineCount() {
        int lowCount = dashboardService.getLowStockCount();
        txtLowMedicine.setText(String.valueOf(lowCount));
    }
}