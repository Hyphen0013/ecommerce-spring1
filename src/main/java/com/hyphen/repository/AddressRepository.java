package com.hyphen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hyphen.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
