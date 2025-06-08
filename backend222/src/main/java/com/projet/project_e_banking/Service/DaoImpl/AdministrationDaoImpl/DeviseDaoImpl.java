package com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl;

import com.projet.project_e_banking.Dto.AdministrationDto.DeviseDto;
import com.projet.project_e_banking.Exception.DuplicateFieldException;
import com.projet.project_e_banking.Exception.resourcenotfoundexception;
import com.projet.project_e_banking.Model.EspaceAdministration.Devise;
import com.projet.project_e_banking.Repository.AdministarationRepository.DeviseRepository;
import com.projet.project_e_banking.Service.Dao.AdministartionDao.DeviseDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DeviseDaoImpl implements DeviseDao {
    private final DeviseRepository deviserepository ;

    public DeviseDaoImpl(DeviseRepository deviserepository) {
        this.deviserepository = deviserepository;
    }

    @Override
    public Devise adddevise(DeviseDto devisedto) {
        Devise devise =new Devise ();
        if(deviserepository.existsByCode(devisedto.getCode()) && !Objects.equals(devisedto.getCode(),devise.getCode())) throw new DuplicateFieldException("code already used");
        if(deviserepository.existsByName(devisedto.getName()) && !Objects.equals(devisedto.getName(),devise.getName())) throw new DuplicateFieldException("name already used");
        devise.setCode(devisedto.getCode());
        devise.setCountry(devisedto.getCountry());
        devise.setBuyRate(devisedto.getBuyRate());
        devise.setSymbol(devisedto.getSymbol());
        devise.setSellRate(devisedto.getSellRate());
        devise.setName(devisedto.getName());
        devise.setEnabled(true);
        devise.setAmount(0L);
        return deviserepository.save(devise);
    }

    @Override
    public List<Devise> getalldevises() {
        return deviserepository.findAll();
    }

    @Override
    public Devise updateDevisestatus(Long id) {
//        System.out.println("Updating Devise ID " + id + " with enabled=" + status);
        Devise devise=deviserepository.findById(id)
                .orElseThrow(()-> new resourcenotfoundexception("User not found"));
        boolean bool=devise.isEnabled();
        devise.setEnabled(!bool);
        return deviserepository.save(devise);
    }

    @Override
    public Devise updatedevise(DeviseDto devisedto) {
        Devise devise=deviserepository.findById(devisedto.getId())
                .orElseThrow(()->new resourcenotfoundexception("user not found"));
        if(deviserepository.existsByCode(devisedto.getCode()) && !Objects.equals(devisedto.getCode(),devise.getCode())) throw new DuplicateFieldException("code already used");
        if(deviserepository.existsByName(devisedto.getName()) && !Objects.equals(devisedto.getName(),devise.getName())) throw new DuplicateFieldException("name already used");
        devise.setBuyRate(devisedto.getBuyRate());
        devise.setSellRate(devisedto.getSellRate());
        devise.setName(devisedto.getName());
        devise.setCode(devisedto.getCode());
        devise.setCountry(devisedto.getCountry());
        devise.setSymbol(devisedto.getSymbol());
        devise.setAmount(devisedto.getAmount());
        return deviserepository.save(devise);
    }

}
