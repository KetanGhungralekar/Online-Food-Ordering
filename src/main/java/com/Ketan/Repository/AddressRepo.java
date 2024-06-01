package com.Ketan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ketan.model.Address;


public interface AddressRepo extends JpaRepository<Address, Long>{
    
}
