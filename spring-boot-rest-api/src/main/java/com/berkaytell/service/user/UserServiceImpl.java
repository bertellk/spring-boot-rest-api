package com.berkaytell.service.user;

import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.model.User;
import com.berkaytell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public User saveUser(SignUpUserDto signUpUserDto) {
        return userRepository.save(createInstance(signUpUserDto));
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    private User createInstance(SignUpUserDto signUpUserDto) {
        return User.builder()
                .userName(signUpUserDto.getUserName())
                .password(passwordEncoder.encode(signUpUserDto.getPassword()))
                .roles(new HashSet<>())
                .build();
    }
}
