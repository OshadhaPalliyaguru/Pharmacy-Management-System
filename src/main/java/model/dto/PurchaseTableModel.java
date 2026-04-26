package model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseTableModel {
    private Integer id;
    private String date;
    private String type;
    private Double amount;
}