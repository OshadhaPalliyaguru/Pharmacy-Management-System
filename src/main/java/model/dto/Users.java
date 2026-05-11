package model.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
}
