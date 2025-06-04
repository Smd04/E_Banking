package com.projet.project_e_banking.Service.DaoImpl.AdminService;

import com.projet.project_e_banking.Dto.SystemSettingsDto;
import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.SystemSettingsRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemSettingsService {

    @Autowired
    private SystemSettingsRepository settingsRepository;

    @Autowired
    private UserRepository userRepository;

    public SystemSettingsDto getSettingsForUser(Long userId) {
        SystemSettings settings = settingsRepository.findByUserIdIfRoleIsUser(userId);
        if (settings == null) {
            return null;
        }
        return convertToDto(settings);
    }

    public SystemSettingsDto updateUserSettings(Long userId, SystemSettingsDto newSettingsDto) {
        // Try to find existing settings first
        SystemSettings existing = settingsRepository.findByUserIdIfRoleIsUser(userId);

        if (existing == null) {
            // Only create new settings if they don't exist
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found."));

            if (!user.getRole().equals(com.projet.project_e_banking.Utilis.Role.USER)) {
                throw new RuntimeException("User is not allowed.");
            }

            existing = new SystemSettings();
            existing.setUser(user);
        }

        // Update the fields whether it's new or existing
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

    public List<SystemSettings> getAllSettingsForUsersOnly() {
        return settingsRepository.findAllSettingsForUsersOnly();
    }

}