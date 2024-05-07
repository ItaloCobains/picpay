package picpay.desafio.picpay.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import picpay.desafio.picpay.domain.user.User;
import picpay.desafio.picpay.domain.user.UserType;
import picpay.desafio.picpay.dtos.UserDTO;
import picpay.desafio.picpay.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Merchant cannot make transactions");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(Long id) throws Exception {
        return userRepository.findUserById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public User findUserByDocument(String document) throws Exception {
        return userRepository.findUserByDocument(document).orElseThrow(() -> new Exception("User not found"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);

        saveUser(newUser);

        return newUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }    
}
