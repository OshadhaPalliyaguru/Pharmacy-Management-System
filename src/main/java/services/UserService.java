package services;

import model.dto.Users;

import java.util.List;

public interface UserService {
    boolean registerUser(Users user);
    List<Users> getAllUsers();
    Users findByEmail(String email);
    Users login(String username,String password);
}
