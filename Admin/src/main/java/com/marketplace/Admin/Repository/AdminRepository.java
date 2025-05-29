package com.marketplace.Admin.Repository;

import com.marketplace.Admin.Model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findByEmail(String email);
}


