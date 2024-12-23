package com.berkaytell.service.token;

import com.berkaytell.model.User;
import com.berkaytell.result.Result;

public interface TokenService {
    void save(User user, String jwtToken);

    Result delete(String token);
}
