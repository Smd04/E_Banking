package com.projet.project_e_banking.Service.Mapper.AdministrationMapper;

import com.projet.project_e_banking.Dto.AdministrationDto.AdministratorDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import com.projet.project_e_banking.Model.EspaceBanque.Agent;

public class AdministratorMapper {

    // ENTITY -> DTO
    public static AdministratorDto toDto(Administrator admin) {
        if (admin == null) return null;

        AdministratorDto dto = new AdministratorDto();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setName(admin.getName());
        dto.setEmail(admin.getEmail());
        dto.setPassword(admin.getPassword());
        dto.setPhone(admin.getPhone());
        dto.setAddress(admin.getAddress());
        dto.setCity(admin.getCity());
        dto.setStatus(admin.getStatus());
        dto.setRole(admin.getRole());
        dto.setEmployeeId(admin.getEmployeeId());
        dto.setDepartement(admin.getDepartement());
        dto.setBankId(admin.getBank() != null ? admin.getBank().getId() : null);
        dto.setAgentId(admin.getAgent() != null ? admin.getAgent().getId() : null);

        return dto;
    }
}
