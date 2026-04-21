package model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Medicine {
    private Integer id;
    private String name;
    private String brand;
    private String batch_no;
    private LocalDate expiry_date;
    private Integer quantity;
    private Double unit_price;
    private Integer reorder_level;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    }

