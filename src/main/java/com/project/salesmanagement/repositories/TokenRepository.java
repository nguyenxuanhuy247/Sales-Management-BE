package com.project.salesmanagement.repositories;


import com.project.salesmanagement.models.Token;
import com.project.salesmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}

