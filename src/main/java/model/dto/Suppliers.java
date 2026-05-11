package model.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Suppliers {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate createdAt;
}
