package com.berkaytell.service.authentication;

import com.berkaytell.dto.authentication.LogInResponse;
import com.berkaytell.dto.user.LogInUserDto;
import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;

public interface AuthenticationService {
    Result signUp(SignUpUserDto signUpUserDto);

    DataResult<LogInResponse> logIn(LogInUserDto logInUserDto);
}
