package com.projet.project_e_banking.Service.Mapper.AdministrationMapper;

import com.projet.project_e_banking.Dto.AdministrationDto.DeviseDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Devise;

public class DeviseMapper {

    public static DeviseDto mapdevisetodto(Devise devise ){
        return new DeviseDto(
                devise.getId(),
                devise.getCode(),
                devise.getName(),
                devise.getBuyRate(),
                devise.getSellRate(),
                devise.isEnabled(),
                devise.getSymbol(),
                devise.getCountry(),
                devise.getAmount()

        );
    }
}
