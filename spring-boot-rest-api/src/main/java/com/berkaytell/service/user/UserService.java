package com.berkaytell.service.user;

import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.model.User;

public interface UserService {
    boolean existsByUserName(String userName);

    User saveUser(SignUpUserDto signUpUserDto);

    User findByUserName(String userName);
}
