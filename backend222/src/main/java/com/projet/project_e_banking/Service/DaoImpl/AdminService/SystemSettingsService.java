package com.projet.project_e_banking.Service.DaoImpl.AdminService;

import com.projet.project_e_banking.Dto.SystemSettingsDto;
import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.SystemSettingsRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import com.projet.project_e_banking.Utilis.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SystemSettingsService {

    @Autowired
    private SystemSettingsRepository settingsRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to initialize default settings for all users with role USER
    public void initializeDefaultSettingsForAllUsers() {
        List<User> users = userRepository.findByRole(Role.USER);

        for (User user : users) {
            boolean exists = settingsRepository.existsByUserId(user.getId());
            if (!exists) {
                SystemSettings defaultSettings = new SystemSettings();
                defaultSettings.setUser(user);
                defaultSettings.setCommissionRate(0.0);
                defaultSettings.setMaxTransactionAmount(0.0);
                defaultSettings.setTransferLimit(0.0);
                defaultSettings.setCryptoEnabled(false);
                defaultSettings.setReferralEnabled(false);

                settingsRepository.save(defaultSettings);
            }
        }
    }

    // This method runs once after the bean is constructed and dependencies injected
    @PostConstruct
    public void init() {
        initializeDefaultSettingsForAllUsers();
    }

    public SystemSettingsDto getSettingsForUser(Long userId) {
        SystemSettings settings = settingsRepository.findByUserIdIfRoleIsUser(userId);
        if (settings == null) {
            return null;
        }
        return convertToDto(settings);
    }

    public SystemSettingsDto updateUserSettings(Long userId, SystemSettingsDto newSettingsDto) {
        SystemSettings existing = settingsRepository.findByUserIdIfRoleIsUser(userId);

        if (existing == null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found."));

            if (!user.getRole().equals(Role.USER)) {
                throw new RuntimeException("User is not allowed.");
            }

            existing = new SystemSettings();
            existing.setUser(user);
        }

        existing.setCommissionRate(newSettingsDto.getCommissionRate());
        existing.setMaxTransactionAmount(newSettingsDto.getMaxTransactionAmount());
        existing.setTransferLimit(newSettingsDto.getTransferLimit());
        existing.setCryptoEnabled(newSettingsDto.isCryptoEnabled());
        existing.setReferralEnabled(newSettingsDto.isReferralEnabled());

        SystemSettings saved = settingsRepository.save(existing);
        return convertToDto(saved);
    }

    private SystemSettingsDto convertToDto(SystemSettings settings) {
        SystemSettingsDto dto = new SystemSettingsDto();
        dto.setId(settings.getId());
        dto.setCommissionRate(settings.getCommissionRate());
        dto.setMaxTransactionAmount(settings.getMaxTransactionAmount());
        dto.setTransferLimit(settings.getTransferLimit());
        dto.setCryptoEnabled(settings.isCryptoEnabled());
        dto.setReferralEnabled(settings.isReferralEnabled());
        dto.setUserId(settings.getUser().getId());
        return dto;
    }

    public List<SystemSettingsDto> getAllSettingsForUsersOnly() {
        List<User> users = userRepository.findByRole(Role.USER);
        List<SystemSettingsDto> dtos = new ArrayList<>();

        for (User user : users) {
            SystemSettings settings = settingsRepository.findByUserId(user.getId()).orElse(null);

            if (settings == null) {
                // Create default settings DTO when none found
                SystemSettingsDto dto = new SystemSettingsDto();
                dto.setUserId(user.getId());
                dto.setCommissionRate(0.0);
                dto.setMaxTransactionAmount(0.0);
                dto.setTransferLimit(0.0);
                dto.setCryptoEnabled(false);
                dto.setReferralEnabled(false);
                dtos.add(dto);
            } else {
                dtos.add(convertToDto(settings));
            }
        }
        return dtos;
    }
}
