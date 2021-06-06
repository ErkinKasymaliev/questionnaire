package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.User;
import kg.kasymaliev.questionnaire.repository.UserRepository;
import kg.kasymaliev.questionnaire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLoginIs(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null)
            return false;
        userRepository.delete(user);
        return true;
    }
}
