package com.berkaytell.service.user;

import com.berkaytell.configuration.ConstantErrorMessages;
import com.berkaytell.configuration.ConstantMessages;
import com.berkaytell.dto.authentication.ChangePasswordRequest;
import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.model.User;
import com.berkaytell.repository.UserRepository;
import com.berkaytell.result.Result;
import com.berkaytell.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.findByUserNameAndIsDeletedFalse(userName).isPresent();
    }

    @Override
    public User saveUser(SignUpUserDto signUpUserDto) {
        return userRepository.save(createInstance(signUpUserDto));
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserNameAndIsDeletedFalse(userName).orElse(null);
    }

    @Override
    public Result delete(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null)
            return Result.of(false, "Kullanıcı Bulunamadı");

        userRepository.delete(user);
        return Result.of(true);
    }

    @Override
    public Result changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByUserNameAndIsDeletedFalse(changePasswordRequest.getUserName()).orElse(null);

        if (user == null)
            return Result.of(false, ConstantMessages.USER_NOT_FOUND);

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword()))
            return Result.of(false, ConstantErrorMessages.BAD_CREDENTIALS);

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        tokenService.deleteAllTokensAssociatedWithUser(user.getId());
        userRepository.save(user);

        return Result.of(true);
    }

    private User createInstance(SignUpUserDto signUpUserDto) {
        return User.builder()
                .userName(signUpUserDto.getUserName())
                .password(passwordEncoder.encode(signUpUserDto.getPassword()))
                .isActive(true)
                .roles(new HashSet<>())
                .build();
    }

}
