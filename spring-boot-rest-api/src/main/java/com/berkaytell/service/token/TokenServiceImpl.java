package com.berkaytell.service.token;

import com.berkaytell.model.Token;
import com.berkaytell.model.User;
import com.berkaytell.repository.TokenRepository;
import com.berkaytell.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void save(User user, String jwtToken) {
        tokenRepository.save(createInstance(user, jwtToken));
    }

    @Override
    public Result delete(String token) {
        Token tokenToDelete = tokenRepository.findByToken(token).orElse(null);

        if (tokenToDelete == null)
            return Result.of(false, "Token Not Found.");

        tokenRepository.delete(tokenToDelete);
        return Result.of(true);
    }

    @Override
    public void deleteAllTokensAssociatedWithUser(Long userId) {
        List<Token> tokens = tokenRepository.findAllValidTokenByUser(userId);

        tokenRepository.deleteAll(tokens);
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
