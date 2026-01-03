package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long>{

}
