package com.github.abeatrizsc.event_service.repositories;

import com.github.abeatrizsc.event_service.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByPostalCode(String postalCode);
}
