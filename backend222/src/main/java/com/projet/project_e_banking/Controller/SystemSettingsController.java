package com.projet.project_e_banking.Controller;

import com.projet.project_e_banking.Dto.SystemSettingsDto;
import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import com.projet.project_e_banking.Service.DaoImpl.AdminService.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class SystemSettingsController {

    @Autowired
    private SystemSettingsService settingsService;

    @GetMapping("/user/{userId}")
    public SystemSettingsDto getSettingsForUser(@PathVariable("userId") Long userId) {
        return settingsService.getSettingsForUser(userId);
    }

    @PutMapping("/user/{userId}")
    public SystemSettingsDto updateUserSettings(@PathVariable("userId") Long userId, @RequestBody SystemSettingsDto newSettings) {
        return settingsService.updateUserSettings(userId, newSettings);
    }

    @GetMapping("/users/settings")
    public List<SystemSettingsDto> getAllUserSettings() {
        List<SystemSettings> settingsList = settingsService.getAllSettingsForUsersOnly();
        return settingsList.stream()
                .map(s -> {
                    SystemSettingsDto dto = new SystemSettingsDto();
                    dto.setId(s.getId());
                    dto.setCommissionRate(s.getCommissionRate());
                    dto.setMaxTransactionAmount(s.getMaxTransactionAmount());
                    dto.setTransferLimit(s.getTransferLimit());
                    dto.setCryptoEnabled(s.isCryptoEnabled());
                    dto.setReferralEnabled(s.isReferralEnabled());
                    dto.setUserId(s.getUser().getId());
                    return dto;
                })
                .toList();
    }

}