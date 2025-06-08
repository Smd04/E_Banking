package com.projet.project_e_banking.Repository;

import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long> {

    @Query("SELECT s FROM SystemSettings s WHERE s.user.id = :userId AND s.user.role = com.projet.project_e_banking.Utilis.Role.USER")
    SystemSettings findByUserIdIfRoleIsUser(@Param("userId") Long userId);


    @Query("SELECT s FROM SystemSettings s WHERE s.user.role = com.projet.project_e_banking.Utilis.Role.USER")
    List<SystemSettings> findAllSettingsForUsersOnly();

    Optional<SystemSettings> findByUserId(Long userId);
}
