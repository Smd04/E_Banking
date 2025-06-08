package com.projet.project_e_banking.Service.Dao.AdministartionDao;

import com.projet.project_e_banking.Dto.AdministrationDto.SupportMessageDto;
import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;

import java.util.List;

public interface SupportMessageDao {
    public List<SupportMessage> getallRequestmessages();
    public SupportMessage deletemessage(Long id);
    public SupportMessage addResponsemessage(SupportMessageDto response);
}
