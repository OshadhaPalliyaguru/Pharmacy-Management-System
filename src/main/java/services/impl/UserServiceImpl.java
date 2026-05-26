package services.impl;

import model.dto.Users;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public boolean registerUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users login(String username, String password) {
       return userRepository.login(username,password);
    }

}
