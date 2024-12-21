package com.berkaytell.service.token;

import com.berkaytell.model.User;

public interface TokenService {
    void saveToken(User user, String jwtToken);
}
