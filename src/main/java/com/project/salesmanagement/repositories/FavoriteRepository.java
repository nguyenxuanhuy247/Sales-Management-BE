package com.project.salesmanagement.repositories;

import com.project.salesmanagement.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    Favorite findByUserIdAndProductId(Long userId, Long productId);
}


