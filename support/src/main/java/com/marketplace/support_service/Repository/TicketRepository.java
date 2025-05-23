package com.marketplace.support_service.Repository;

import com.marketplace.support_service.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

        List<Ticket> findByUserId(String userId);
}


