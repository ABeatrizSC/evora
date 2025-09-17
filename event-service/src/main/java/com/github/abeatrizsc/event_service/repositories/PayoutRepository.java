package com.github.abeatrizsc.event_service.repositories;

import com.github.abeatrizsc.event_service.domain.Payout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoutRepository extends JpaRepository<Payout, String> {
}
