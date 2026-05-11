package repository;

import model.dto.Users;

import java.util.List;

public interface UserRepository 
{
    boolean save(Users user);

    List<Users> findAll();

    Users findByEmail(String email);

    Users login(String username, String password);
}
