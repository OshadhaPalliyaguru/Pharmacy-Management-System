package model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
    private String medicineId;
    private String customerName;
    private String medicineName;
    private double unitPrice;
    private int quantity;
    private double total;
    private String cashier;


}
