package com.Ketan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ketan.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    
}
