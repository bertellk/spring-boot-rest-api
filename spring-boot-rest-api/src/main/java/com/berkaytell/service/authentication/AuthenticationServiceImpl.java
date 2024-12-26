package com.berkaytell.service.authentication;

import com.berkaytell.dto.authentication.LogInResponse;
import com.berkaytell.dto.authentication.LogOutRequest;
import com.berkaytell.dto.user.LogInUserDto;
import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.exception.custom_exceptions.BadCredentialsException;
import com.berkaytell.model.auth.Role;
import com.berkaytell.model.User;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;
import com.berkaytell.security.JwtService;
import com.berkaytell.service.token.TokenService;
import com.berkaytell.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Result signUp(SignUpUserDto signUpUserDto) {
        if (userService.existsByUserName(signUpUserDto.getUserName()))
            return Result.of(false, "Kullanıcı Zaten Kayıtlı.");

        User savedUser = userService.saveUser(signUpUserDto);
        final String accessToken = jwtService.generateAccessToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveToken(savedUser, accessToken, refreshToken);

        return Result.of(true, "Kayıt Başarılı.");
    }

    @Override
    public DataResult<LogInResponse> logIn(LogInUserDto logInUserDto) {
        authenticate(logInUserDto.getUserName(), logInUserDto.getPassword());
        User user = userService.findByUserName(logInUserDto.getUserName());

        if (user == null)
            return DataResult.of(null, false, "Kullanıcı Bulunamadı.");

        final String accessToken = jwtService.generateAccessToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        saveToken(user, accessToken, refreshToken);

        //        return DataResult.of(createLogInResponseInstance(accessToken, refreshToken, user.getRoles().stream().map(Role::getName).toList()), true, "Giriş Yapıldı.");
        return DataResult.of(createLogInResponseInstance(accessToken, refreshToken), true, "Giriş Yapıldı.");
    }

    @Override
    public Result logOut(LogOutRequest logOutRequest) {
        if (logOutRequest == null || logOutRequest.getToken() == null)
            return Result.of(false, "Invalid Token");

        return tokenService.delete(logOutRequest.getToken());
    }

    private void saveToken(User user, String accessToken, String refreshToken) {
        tokenService.save(user, accessToken);
        tokenService.save(user, refreshToken);
    }

    private LogInResponse createLogInResponseInstance(String accessToken, String refreshToken) {
        return LogInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new BadCredentialsException();
        }
    }
}
