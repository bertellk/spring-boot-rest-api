package com.berkaytell.service.token;

import com.berkaytell.model.Token;
import com.berkaytell.model.User;
import com.berkaytell.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(User user, String jwtToken) {
        tokenRepository.save(createInstance(user, jwtToken));
    }

    private Token createInstance(User user, String jwtToken) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
    }

}
