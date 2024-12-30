package com.berkaytell.service.user;

import com.berkaytell.dto.authentication.AuthorityDto;
import com.berkaytell.dto.authentication.ChangePasswordRequest;
import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.model.User;
import com.berkaytell.result.Result;

import java.util.List;

public interface UserService {
    boolean existsByUserName(String userName);

    User saveUser(SignUpUserDto signUpUserDto);

    User findByUserName(String userName);

    Result delete(Long id);

    Result changePassword(ChangePasswordRequest changePasswordRequest);

}
