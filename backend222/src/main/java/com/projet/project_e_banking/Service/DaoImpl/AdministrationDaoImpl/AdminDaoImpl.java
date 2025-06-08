package com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl;

import com.projet.project_e_banking.Dto.AdministrationDto.AdministratorDto;
import com.projet.project_e_banking.Exception.DuplicateFieldException;
import com.projet.project_e_banking.Exception.resourcenotfoundexception;
import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Model.EspaceBanque.Agent;
import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import com.projet.project_e_banking.Repository.AdministarationRepository.AdminRepository;
import com.projet.project_e_banking.Repository.BanqueRepository.AgentRepository;
import com.projet.project_e_banking.Repository.BanqueRepository.BankRepository;
import com.projet.project_e_banking.Service.Dao.AdministartionDao.AdminDao;
import com.projet.project_e_banking.Utilis.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminDaoImpl implements AdminDao {
    private final AdminRepository adminrepository ;
    private final BankRepository bankrepository ;
    private final PasswordEncoder passwordEncoder ;
    private final AgentRepository agentrepository;

    public AdminDaoImpl(AdminRepository adminrepository, BankRepository bankrepository, PasswordEncoder passwordEncoder, AgentRepository agentrepository) {
        this.adminrepository = adminrepository;
        this.bankrepository = bankrepository;
        this.passwordEncoder = passwordEncoder;
        this.agentrepository = agentrepository;
    }

    @Override
    public List<Administrator> getAlladmins() {
       return adminrepository.findAll();
    }

    @Override
    public Administrator addadmin(AdministratorDto admindto) {
        if(adminrepository.existsByUsername(admindto.getUsername())) {
            throw new DuplicateFieldException("username already used");
        }
        if(adminrepository.existsByEmail(admindto.getEmail())) {
            throw new DuplicateFieldException("email already used");
        }
        if(adminrepository.existsByPhone(admindto.getPhone())) {
            throw new DuplicateFieldException("number phone already used");
        }
        Bank bank =bankrepository.findById(admindto.getBankId())
                .orElseThrow(()->new resourcenotfoundexception("bank not found "));
        Agent agent = agentrepository.findById(admindto.getAgentId())
                .orElseThrow(()->new resourcenotfoundexception("agent not found"));
        Administrator admin =new Administrator();
        admin.setBank(bank);
        admin.setEmployeeId(admindto.getEmployeeId());
        admin.setDepartement(admindto.getDepartement());
        admin.setAddress(admindto.getAddress());
        admin.setEmail(admindto.getEmail());
//        admin.setCity(admindto.getCity());
        admin.setName(admindto.getName());
        admin.setUsername(admindto.getUsername());
        admin.setPhone(admindto.getPhone());
        admin.setStatus("ACTIVE");
        admin.setRole(Role.ADMIN);
        String encodedPassword = passwordEncoder.encode(admindto.getPassword());
        admin.setPassword(encodedPassword);
        admin.setAgent(agent);
        return adminrepository.save(admin);
    }

    @Override
    public Administrator updateadmin(AdministratorDto admindto) {
        Administrator admin=adminrepository.findById(admindto.getId())
                .orElseThrow(()->new resourcenotfoundexception("admin not found"));
        if(adminrepository.existsByUsername(admindto.getUsername()) && !Objects.equals(admin.getUsername(), admindto.getUsername()))
            throw new DuplicateFieldException("username already used");
        if(adminrepository.existsByEmail(admindto.getEmail()) && !Objects.equals(admin.getEmail(), admindto.getEmail()))
            throw new DuplicateFieldException("email already used");
        if(adminrepository.existsByPhone(admindto.getPhone()) && !Objects.equals(admin.getPhone(), admindto.getPhone()))
            throw new DuplicateFieldException("number phone already used");
        Bank bank =bankrepository.findById(admindto.getBankId())
                .orElseThrow(()->new resourcenotfoundexception("bank not found "));
        Agent agent = agentrepository.findById(admindto.getAgentId())
                .orElseThrow(()->new resourcenotfoundexception("agent not found"));
        admin.setBank(bank);
        admin.setEmployeeId(admindto.getEmployeeId());
        admin.setDepartement(admindto.getDepartement());
        admin.setAddress(admindto.getAddress());
        admin.setEmail(admindto.getEmail());
//        admin.setCity(admindto.getCity());
        admin.setName(admindto.getName());
        admin.setPhone(admindto.getPhone());
        admin.setStatus(admindto.getStatus());
        admin.setAgent(agent);
        return adminrepository.save(admin);
    }

    @Override
    public Administrator deleteadmin(AdministratorDto admindto) {
        Administrator admin =adminrepository.findById(admindto.getId())
                .orElseThrow(()->new resourcenotfoundexception("admin not found"));
        adminrepository.delete(admin);
        return admin;
    }


}
