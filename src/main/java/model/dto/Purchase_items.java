package model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchase_items {

    private Integer id;
    private Integer purchase_id;
    private Integer medicine_id;
    private String batch_no;
    private LocalDate expiry_date;
    private Integer qty;
    private Double unit_price;
    private LocalDate created_at;
}