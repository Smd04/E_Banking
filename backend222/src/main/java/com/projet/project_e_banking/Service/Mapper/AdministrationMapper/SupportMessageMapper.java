package com.projet.project_e_banking.Service.Mapper.AdministrationMapper;


import com.projet.project_e_banking.Dto.AdministrationDto.SupportMessageDto;
import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;

public class SupportMessageMapper {

    public static SupportMessageDto mapToDto(SupportMessage message) {
        return new SupportMessageDto(
                message.getId(),
                message.getMessage(),
                message.getType(),
                message.getTimestamp(),
                message.getUser().getId(),
                message.getAdmin().getId()
        );
    }
}
