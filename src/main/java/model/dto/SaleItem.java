package model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaleItem {
    private Integer sale_id;
    private Integer medicine_id;
    private Integer qty;
    private Double unit_price;
    private LocalDateTime created_at;
}
