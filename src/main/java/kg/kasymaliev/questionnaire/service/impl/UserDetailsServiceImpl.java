package kg.kasymaliev.questionnaire.service.impl;

import kg.kasymaliev.questionnaire.entity.User;
import kg.kasymaliev.questionnaire.model.UserDetailsImpl;
import kg.kasymaliev.questionnaire.model.UserType;
import kg.kasymaliev.questionnaire.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.*;

@RequiredArgsConstructor
@Service("questionnaireUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepository.findByLoginIs(s).orElseThrow(() ->
//                new UsernameNotFoundException("User with login " + s + " is not found"));
//
//        return new UserDetailsImpl(user);
//    }
    @Autowired
    private UserRepository repo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByLoginIs(username).orElse(null);
        Set<UserType> set = new HashSet<>(Arrays.asList(UserType.ADMIN, UserType.USER));

        if (user != null) {
            return new UserDetailsImpl(
                    user.getPassword(),
                    user.getLogin(),
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<UserType> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserType role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }
}
