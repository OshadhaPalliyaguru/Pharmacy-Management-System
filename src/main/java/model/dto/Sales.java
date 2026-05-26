package model.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sales {
    private Integer id;
    private String invoice_no;
    private LocalDateTime sale_date;
    private String cashier;
    private Double total_amount;
    private LocalDateTime created_at;
}
