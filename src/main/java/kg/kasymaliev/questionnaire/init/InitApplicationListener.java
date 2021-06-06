package kg.kasymaliev.questionnaire.init;

import kg.kasymaliev.questionnaire.entity.User;
import kg.kasymaliev.questionnaire.model.UserType;
import kg.kasymaliev.questionnaire.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        init();
    }

    private void init() {
        if(userRepository.findAll().isEmpty()) {
            User admin = User.builder().createdDt(LocalDateTime.now())
                    .firstname("admin")
                    .login("admin")
                    .password(passwordEncoder.encode("admin"))
                    .surname("admin")
                    .type(UserType.ADMIN)
                    .build();
            userRepository.save(admin);
            User testUser = User.builder().createdDt(LocalDateTime.now())
                    .firstname("testName")
                    .login("userTest")
                    .password(passwordEncoder.encode("secret"))
                    .type(UserType.USER)
                    .build();
            userRepository.save(testUser);

        }
    }

}
