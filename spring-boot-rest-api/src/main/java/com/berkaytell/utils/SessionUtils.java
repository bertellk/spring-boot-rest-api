package com.berkaytell.utils;

import com.berkaytell.dto.user.UserDto;
import com.berkaytell.mapper.UserMapper;
import com.berkaytell.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SessionUtils {
    private static User getUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication).map(auth -> (User) auth.getPrincipal()).orElse(null);
    }

    public static UserDto getUser() {
        User user = getUserEntity();
        return UserMapper.INSTANCE.toDto(user);
    }

    public static String getUserName() {
        return getUserEntity().getUsername();
    }
}
