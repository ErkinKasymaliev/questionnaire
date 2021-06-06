package kg.kasymaliev.questionnaire.service;

import kg.kasymaliev.questionnaire.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByLogin(String login);
    List<User> getAllUsers();
    boolean deleteUser(Long userId);
}
