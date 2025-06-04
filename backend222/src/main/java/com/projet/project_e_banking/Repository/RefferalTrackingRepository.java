package com.projet.project_e_banking.Repository;

import com.projet.project_e_banking.Model.ModelSystemParainage.RefferalTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RefferalTrackingRepository extends JpaRepository<RefferalTracking, Long> {
    List<RefferalTracking> findByReferringUserId(Long referringUserId);  // Updated method name
}