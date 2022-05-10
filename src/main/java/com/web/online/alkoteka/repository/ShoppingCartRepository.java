package com.web.online.alkoteka.repository;

import com.web.online.alkoteka.model.ShoppingCart;
import com.web.online.alkoteka.model.User;
import com.web.online.alkoteka.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}
