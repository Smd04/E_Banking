package com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl;

import com.projet.project_e_banking.Dto.AdministrationDto.SupportMessageDto;
import com.projet.project_e_banking.Exception.resourcenotfoundexception;
import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.AdministarationRepository.AdminRepository;
import com.projet.project_e_banking.Repository.AdministarationRepository.SupportMessagesRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import com.projet.project_e_banking.Service.Dao.AdministartionDao.SupportMessageDao;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SupportMessageDaoImpl implements SupportMessageDao {

    private final SupportMessagesRepository supportmessagerepository ;
    private final UserRepository userrepository ;
    private final AdminRepository adminrepository;

    public SupportMessageDaoImpl(SupportMessagesRepository supportmessagerepository, UserRepository userrepository, AdminRepository adminrepository) {
        this.supportmessagerepository = supportmessagerepository;
        this.userrepository = userrepository;
        this.adminrepository = adminrepository;
    }

    @Override
    public List<SupportMessage> getallRequestmessages() {
        return supportmessagerepository.findByType("request");
    }

    @Override
    public SupportMessage deletemessage(Long id) {
        SupportMessage message=supportmessagerepository.findById(id)
                .orElseThrow(()->new resourcenotfoundexception("message not found"));
         supportmessagerepository.delete(message);
         return message ;
    }

    @Override
    public SupportMessage addResponsemessage(SupportMessageDto responsedto) {
        User user=userrepository.findById(responsedto.getUserid())
                .orElseThrow(()->new resourcenotfoundexception("usernotfound"));
        Administrator admin =adminrepository.findById(responsedto.getAdminid())
                .orElseThrow(()->new resourcenotfoundexception("usernotfound"));

        SupportMessage response=new SupportMessage();
        response.setMessage(responsedto.getMessage());
        response.setType("response");
        response.setTimestamp(new Date());
        response.setUser(user);
        response.setAdmin(admin);
        return supportmessagerepository.save(response);
    }
}
