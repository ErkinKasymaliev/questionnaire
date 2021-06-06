package kg.kasymaliev.questionnaire.util;

import kg.kasymaliev.questionnaire.model.UserDetailsImpl;
import kg.kasymaliev.questionnaire.model.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class SecurityContextHelperUtil {
    public static String getUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(it -> (UserDetailsImpl) it)
                .map(UserDetailsImpl::getUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find username in security context."));
    }

    public static UserType getUserType() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .flatMap(it -> it.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(UserType::valueOf)
                        .findFirst()
                ).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find user type in security context."));
    }
}
