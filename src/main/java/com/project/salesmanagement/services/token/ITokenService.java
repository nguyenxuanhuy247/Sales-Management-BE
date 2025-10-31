package com.project.salesmanagement.services.token;

import com.project.salesmanagement.models.Token;
import com.project.salesmanagement.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
}
