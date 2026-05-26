package model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchases {
    private Integer id;
    private Integer supplier_id;
    private String invoice_no;
    private LocalDate purchase_date;
    private Double total_amount;
    private LocalDate created_at;


}
