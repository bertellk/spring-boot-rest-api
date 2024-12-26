package com.berkaytell.controller;

import com.berkaytell.dto.authentication.AuthorityDto;
import com.berkaytell.dto.authentication.ChangePasswordRequest;
import com.berkaytell.dto.authentication.LogInResponse;
import com.berkaytell.dto.authentication.LogOutRequest;
import com.berkaytell.dto.user.LogInUserDto;
import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;
import com.berkaytell.service.authentication.AuthenticationService;
import com.berkaytell.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@CrossOrigin you may need
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<Result> signUp(@RequestBody SignUpUserDto signUpUserDto) {
        return ResponseEntity.ok(authenticationService.signUp(signUpUserDto));
    }

    @PostMapping("/log-in")
    public ResponseEntity<DataResult<LogInResponse>> logIn(@RequestBody LogInUserDto logInUserDto) {
        return ResponseEntity.ok(authenticationService.logIn(logInUserDto));
    }

    @PostMapping("/log-out")
    public ResponseEntity<Result> logOut(@RequestBody LogOutRequest logOutRequest) {
        return ResponseEntity.ok(authenticationService.logOut(logOutRequest));
    }

}
